package cn.devicelinks.service.device;

import cn.devicelinks.api.model.dto.AttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddAttributeRequest;
import cn.devicelinks.api.model.request.AttributeInfoRequest;
import cn.devicelinks.api.model.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.AttributeDataType;
import cn.devicelinks.common.AttributeValueSource;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceAttributeRepository;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.product.FunctionModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceAttribute.DEVICE_ATTRIBUTE;


/**
 * 设备属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeServiceImpl extends BaseServiceImpl<DeviceAttribute, String, DeviceAttributeRepository>
        implements DeviceAttributeService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    public DeviceAttributeServiceImpl(DeviceAttributeRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public List<DeviceAttributeLatestDTO> getLatestAttribute(String deviceId, String moduleId, String attributeName, String attributeIdentifier) {
        return this.repository.getLatestAttribute(deviceId, moduleId, attributeName, attributeIdentifier);
    }

    @Override
    public Attribute extractUnknownAttribute(String deviceAttributeId, ExtractUnknownDeviceAttributeRequest request, UserAuthorizedAddition authorizedAddition) {
        DeviceAttribute deviceAttribute = this.selectById(deviceAttributeId);
        if (deviceAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, deviceAttributeId);
        }
        if (!ObjectUtils.isEmpty(deviceAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_UNKNOWN, deviceAttribute.getIdentifier());
        }
        FunctionModule functionModule = this.functionModuleService.selectById(deviceAttribute.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, deviceAttribute.getModuleId());
        }
        // @formatter:off
        AddAttributeRequest addAttributeRequest = new AddAttributeRequest()
                .setProductId(functionModule.getProductId())
                .setModuleId(functionModule.getId())
                .setInfo(new AttributeInfoRequest()
                        .setName(request.getAttributeName())
                        .setIdentifier(deviceAttribute.getIdentifier())
                        .setDataType(request.getDataType())
                        .setAddition(request.getAddition())
                        .setWritable(request.isWritable()));

        AttributeDTO attributeDTO = this.attributeService.addAttribute(addAttributeRequest, authorizedAddition);

        // After the unknown attribute is extracted as a known attribute,
        // set the attribute_id of the expected attribute to the known attribute id
        deviceAttribute.setAttributeId(attributeDTO.getId());

        this.repository.update(
                List.of(DEVICE_ATTRIBUTE.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE.ID.eq(deviceAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }

    @Override
    public DeviceAttribute checkAttributeIdChartField(String deviceId, String deviceAttributeId) {
        DeviceAttribute deviceAttribute = this.repository.selectOne(
                DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE.ID.eq(deviceAttributeId));
        if (deviceAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, deviceAttributeId);
        }
        // Is it a known attribute
        if (ObjectUtils.isEmpty(deviceAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_KNOWN, deviceAttribute.getIdentifier());
        }
        // Whether the attribute data type supports adding to the chart
        Attribute attribute = this.attributeService.selectById(deviceAttribute.getAttributeId());
        if (AttributeDataType.INTEGER != attribute.getDataType() && AttributeDataType.DOUBLE != attribute.getDataType()) {
            throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART, deviceAttribute.getIdentifier());
        }
        return deviceAttribute;
    }

    @Override
    public void saveOrUpdateDeviceAttribute(List<DeviceAttribute> deviceAttributeList) {
        for (DeviceAttribute deviceAttribute : deviceAttributeList) {
            // Insert
            if (ObjectUtils.isEmpty(deviceAttribute.getId())) {
                this.insert(deviceAttribute);
            }
            // Update
            else {
                this.update(deviceAttribute);
            }
        }
    }

    @Override
    public DeviceAttribute selectByIdentifier(String deviceId, String moduleId, AttributeValueSource valueSource, String identifier) {
        // @formatter:off
        return this.repository.selectOne(DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId),
                DEVICE_ATTRIBUTE.MODULE_ID.eq(moduleId),
                DEVICE_ATTRIBUTE.VALUE_SOURCE.eq(valueSource),
                DEVICE_ATTRIBUTE.IDENTIFIER.eq(identifier));
        // @formatter:on
    }
}

package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.query.PaginationQuery;
import cn.devicelinks.api.support.search.SearchFieldConditionBuilder;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.api.support.request.AddAttributeRequest;
import cn.devicelinks.api.support.request.AttributeInfoRequest;
import cn.devicelinks.api.support.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeRepository;
import cn.devicelinks.service.attribute.AttributeService;
import cn.devicelinks.service.product.FunctionModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttribute.DEVICE_ATTRIBUTE;

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
        return this.repository.getByPageable(searchFieldConditionList, paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
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
}

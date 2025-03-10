package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.console.service.DeviceAttributeLatestService;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddAttributeRequest;
import cn.devicelinks.console.web.request.AttributeInfoRequest;
import cn.devicelinks.console.web.request.ExtractUnknownLatestAttributeRequest;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeLatestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeLatest.DEVICE_ATTRIBUTE_LATEST;

/**
 * 设备属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeLatestServiceImpl extends BaseServiceImpl<DeviceAttributeLatest, String, DeviceAttributeLatestRepository>
        implements DeviceAttributeLatestService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    public DeviceAttributeLatestServiceImpl(DeviceAttributeLatestRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeLatestDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownLatestAttributeRequest request) {
        DeviceAttributeLatest reportedAttribute = this.selectById(reportAttributeId);
        if (reportedAttribute == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_REPORTED_ATTRIBUTE_NOT_FOUND, reportAttributeId);
        }
        if (!ObjectUtils.isEmpty(reportedAttribute.getAttributeId())) {
            throw new ApiException(StatusCodeConstants.DEVICE_REPORTED_ATTRIBUTE_NOT_UNKNOWN, reportedAttribute.getIdentifier());
        }
        FunctionModule functionModule = this.functionModuleService.selectById(reportedAttribute.getModuleId());
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, reportedAttribute.getModuleId());
        }
        // @formatter:off
        AddAttributeRequest addAttributeRequest = new AddAttributeRequest()
                .setProductId(functionModule.getProductId())
                .setModuleId(functionModule.getId())
                .setInfo(new AttributeInfoRequest()
                        .setName(request.getAttributeName())
                        .setIdentifier(reportedAttribute.getIdentifier())
                        .setDataType(request.getDataType())
                        .setAddition(request.getAddition())
                        .setWritable(request.isWritable()));

        AttributeDTO attributeDTO = this.attributeService.addAttribute(addAttributeRequest);

        // After the unknown attribute is extracted as a known attribute,
        // set the attribute_id of the expected attribute to the known attribute id
        reportedAttribute.setAttributeId(attributeDTO.getId());

        this.repository.update(
                List.of(DEVICE_ATTRIBUTE_LATEST.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE_LATEST.ID.eq(reportedAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }
}

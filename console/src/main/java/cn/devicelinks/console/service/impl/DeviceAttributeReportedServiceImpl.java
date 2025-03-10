package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.console.service.DeviceAttributeReportedService;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddAttributeRequest;
import cn.devicelinks.console.web.request.AttributeInfoRequest;
import cn.devicelinks.console.web.request.ExtractUnknownReportedAttributeRequest;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeReportedDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeReportedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeReported.DEVICE_ATTRIBUTE_REPORTED;

/**
 * 设备上报属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeReportedServiceImpl extends BaseServiceImpl<DeviceAttributeLatest, String, DeviceAttributeReportedRepository>
        implements DeviceAttributeReportedService {

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private AttributeService attributeService;

    public DeviceAttributeReportedServiceImpl(DeviceAttributeReportedRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeReportedDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownReportedAttributeRequest request) {
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
                List.of(DEVICE_ATTRIBUTE_REPORTED.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE_REPORTED.ID.eq(reportedAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }
}

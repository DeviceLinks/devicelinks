package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.console.service.ChartDataConfigService;
import cn.devicelinks.console.service.DeviceAttributeLatestService;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddAttributeRequest;
import cn.devicelinks.console.web.request.AddDeviceAttributeChartRequest;
import cn.devicelinks.console.web.request.AttributeInfoRequest;
import cn.devicelinks.console.web.request.ExtractUnknownLatestAttributeRequest;
import cn.devicelinks.framework.common.ChartDataFieldType;
import cn.devicelinks.framework.common.ChartDataTargetLocation;
import cn.devicelinks.framework.common.ChartType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.*;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.ChartDataDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeLatestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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

    @Autowired
    private ChartDataConfigService chartDataConfigService;

    public DeviceAttributeLatestServiceImpl(DeviceAttributeLatestRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeLatestDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public Attribute extractUnknownAttribute(String deviceAttributeId, ExtractUnknownLatestAttributeRequest request) {
        DeviceAttributeLatest deviceAttribute = this.selectById(deviceAttributeId);
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

        AttributeDTO attributeDTO = this.attributeService.addAttribute(addAttributeRequest);

        // After the unknown attribute is extracted as a known attribute,
        // set the attribute_id of the expected attribute to the known attribute id
        deviceAttribute.setAttributeId(attributeDTO.getId());

        this.repository.update(
                List.of(DEVICE_ATTRIBUTE_LATEST.ATTRIBUTE_ID.set(attributeDTO.getId())),
                DEVICE_ATTRIBUTE_LATEST.ID.eq(deviceAttribute.getId()));

        // @formatter:on
        return attributeDTO;
    }

    @Override
    public String addDeviceAttributeChart(String deviceId, AddDeviceAttributeChartRequest request) {
        // @formatter:off
        ChartDataConfig chartDataConfig = new ChartDataConfig()
                .setName(request.getChartName())
                .setChartType(ChartType.valueOf(request.getChartType()))
                .setTargetLocation(ChartDataTargetLocation.DeviceStatus)
                .setTargetId(deviceId)
                .setCreateBy(UserDetailsContext.getUserId());
        List<ChartDataFields> chartDataFields = new ArrayList<>();
        for (AddDeviceAttributeChartRequest.ChartField field : request.getFields()) {
            DeviceAttributeLatest deviceAttribute = this.repository.selectOne(
                    DEVICE_ATTRIBUTE_LATEST.DEVICE_ID.eq(deviceId),
                    DEVICE_ATTRIBUTE_LATEST.ID.eq(field.getDeviceAttributeId()));
            if (deviceAttribute == null) {
                throw new ApiException(StatusCodeConstants.DEVICE_ATTRIBUTE_NOT_FOUND, field.getDeviceAttributeId());
            }
            ChartDataFields chartField = new ChartDataFields()
                    .setFieldType(ChartDataFieldType.Attribute)
                    .setFieldId(deviceAttribute.getId())
                    .setFieldIdentifier(deviceAttribute.getIdentifier())
                    .setFieldLabel(field.getFieldLabel());
            chartDataFields.add(chartField);
        }
        // @formatter:on
        ChartDataDTO chartDataDTO = this.chartDataConfigService.addChartData(chartDataConfig, chartDataFields);
        return chartDataDTO.getId();
    }
}

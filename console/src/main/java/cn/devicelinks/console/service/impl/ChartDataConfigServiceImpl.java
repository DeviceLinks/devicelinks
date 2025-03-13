package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.*;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.converter.ChartDataConverter;
import cn.devicelinks.console.web.request.AddDataChartRequest;
import cn.devicelinks.framework.common.ChartDataFieldType;
import cn.devicelinks.framework.common.ChartDataTargetLocation;
import cn.devicelinks.framework.common.ChartType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.model.dto.ChartDataDTO;
import cn.devicelinks.framework.jdbc.repositorys.ChartDataConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static cn.devicelinks.framework.jdbc.tables.TChartDataConfig.CHART_DATA_CONFIG;

/**
 * 数据图表配置业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class ChartDataConfigServiceImpl extends BaseServiceImpl<ChartDataConfig, String, ChartDataConfigRepository> implements ChartDataConfigService {

    @Autowired
    private ChartDataFieldsService chartDataFieldsService;

    private final Map<ChartDataFieldType, BiFunction<String, String, String>> FIELD_IDENTIFIER_STRATEGIES;

    public ChartDataConfigServiceImpl(ChartDataConfigRepository repository,
                                      DeviceAttributeService deviceAttributeService,
                                      DeviceTelemetryService deviceTelemetryService) {
        super(repository);

        this.FIELD_IDENTIFIER_STRATEGIES = Map.of(
                // DeviceAttribute#identifier
                ChartDataFieldType.Attribute, (targetId, fieldId) -> {
                    DeviceAttribute deviceAttribute = deviceAttributeService.checkAttributeIdChartField(targetId, fieldId);
                    return deviceAttribute.getIdentifier();
                },
                // DeviceTelemetry#metricKey
                ChartDataFieldType.Telemetry, (targetId, fieldId) -> {
                    DeviceTelemetry deviceTelemetry = deviceTelemetryService.checkTelemetryIdChartField(targetId, fieldId);
                    return deviceTelemetry.getMetricKey();
                }
                //...
        );
    }

    @Override
    public ChartDataDTO addChart(ChartDataConfig chartDataConfig, List<ChartDataFields> fields) {
        Assert.notNull(chartDataConfig, "数据图表对象实例为空，无法添加.");
        Assert.notEmpty(fields, "数据图表需要至少包含一个字段.");
        this.repository.insert(chartDataConfig);
        for (ChartDataFields field : fields) {
            field.setConfigId(chartDataConfig.getId());
            this.chartDataFieldsService.insert(field);
        }
        ChartDataDTO chartDataDTO = ChartDataConverter.INSTANCE.fromChartDataConfig(chartDataConfig);
        chartDataDTO.setFields(fields);
        return chartDataDTO;
    }

    @Override
    public ChartDataDTO addChart(AddDataChartRequest request) {
        ChartDataTargetLocation chartTargetLocation = ChartDataTargetLocation.valueOf(request.getTargetLocation());
        ChartDataConfig chart = this.repository.selectOne(
                CHART_DATA_CONFIG.TARGET_ID.eq(request.getTargetId()),
                CHART_DATA_CONFIG.TARGET_LOCATION.eq(chartTargetLocation),
                CHART_DATA_CONFIG.NAME.eq(request.getChartName())
        );
        if (chart != null) {
            throw new ApiException(StatusCodeConstants.DATA_CHART_ALREADY_EXISTS, request.getChartName());
        }
        chart = new ChartDataConfig()
                .setName(request.getChartName())
                .setChartType(ChartType.valueOf(request.getChartType()))
                .setTargetLocation(chartTargetLocation)
                .setTargetId(request.getTargetId())
                .setCreateBy(UserDetailsContext.getUserId());

        List<ChartDataFields> chartFields = new ArrayList<>();
        for (AddDataChartRequest.ChartField field : request.getFields()) {
            chartFields.add(this.toChartField(request.getTargetId(), field));
        }
        return this.addChart(chart, chartFields);
    }

    @Override
    public ChartDataConfig deleteChart(String chartId) {
        ChartDataConfig dataChart = this.selectById(chartId);
        if (dataChart == null || dataChart.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DATA_CHART_NOT_EXISTS, chartId);
        }
        dataChart.setDeleted(Boolean.TRUE);
        this.update(dataChart);
        return dataChart;
    }

    private ChartDataFields toChartField(String targetId, AddDataChartRequest.ChartField field) {
        ChartDataFieldType chartFieldType = ChartDataFieldType.valueOf(field.getFieldType());
        BiFunction<String, String, String> identifierStrategy = FIELD_IDENTIFIER_STRATEGIES.get(chartFieldType);
        if (identifierStrategy == null) {
            throw new IllegalArgumentException("Unsupported chart field type: " + chartFieldType);
        }

        String fieldIdentifier = identifierStrategy.apply(targetId, field.getFieldId());

        return new ChartDataFields()
                .setFieldId(field.getFieldId())
                .setFieldIdentifier(fieldIdentifier)
                .setFieldLabel(field.getFieldLabel())
                .setFieldType(chartFieldType);
    }
}

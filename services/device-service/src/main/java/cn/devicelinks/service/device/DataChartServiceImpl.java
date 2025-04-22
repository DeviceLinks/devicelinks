package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.converter.DataChartConverter;
import cn.devicelinks.api.model.request.AddDataChartRequest;
import cn.devicelinks.common.DataChartFieldType;
import cn.devicelinks.common.DataChartTargetLocation;
import cn.devicelinks.common.DataChartType;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.DataChart;
import cn.devicelinks.entity.DataChartField;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.entity.DeviceTelemetry;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.api.model.dto.DataChartDTO;
import cn.devicelinks.jdbc.repository.DataChartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static cn.devicelinks.jdbc.tables.TDataChart.DATA_CHART;

/**
 * 数据图表业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DataChartServiceImpl extends BaseServiceImpl<DataChart, String, DataChartRepository> implements DataChartService {

    @Autowired
    private DataChartFieldService chartDataFieldsService;

    private final Map<DataChartFieldType, BiFunction<String, String, String>> FIELD_IDENTIFIER_STRATEGIES;

    public DataChartServiceImpl(DataChartRepository repository,
                                DeviceAttributeService deviceAttributeService,
                                DeviceTelemetryService deviceTelemetryService) {
        super(repository);

        this.FIELD_IDENTIFIER_STRATEGIES = Map.of(
                // DeviceAttribute#identifier
                DataChartFieldType.Attribute, (targetId, fieldId) -> {
                    DeviceAttribute deviceAttribute = deviceAttributeService.checkAttributeIdChartField(targetId, fieldId);
                    return deviceAttribute.getIdentifier();
                },
                // DeviceTelemetry#metricKey
                DataChartFieldType.Telemetry, (targetId, fieldId) -> {
                    DeviceTelemetry deviceTelemetry = deviceTelemetryService.checkTelemetryIdChartField(targetId, fieldId);
                    return deviceTelemetry.getMetricKey();
                }
                //...
        );
    }

    @Override
    public List<DataChartDTO> getDataChartList(List<SearchFieldCondition> searchFieldConditionList) {
        List<DataChart> dataChartList = this.repository.getDataChartList(searchFieldConditionList);
        if (ObjectUtils.isEmpty(dataChartList)) {
            return null;
        }
        return dataChartList.stream()
                .map(dataChart -> {
                    DataChartDTO dataChartDTO = DataChartConverter.INSTANCE.fromDataChart(dataChart);
                    dataChartDTO.setFields(this.chartDataFieldsService.getFieldListByChartId(dataChart.getId()));
                    return dataChartDTO;
                }).toList();
    }

    @Override
    public DataChartDTO addChart(DataChart dataChart, List<DataChartField> fields) {
        Assert.notNull(dataChart, "数据图表对象实例为空，无法添加.");
        Assert.notEmpty(fields, "数据图表需要至少包含一个字段.");
        this.repository.insert(dataChart);
        for (DataChartField field : fields) {
            field.setChartId(dataChart.getId());
            this.chartDataFieldsService.insert(field);
        }
        DataChartDTO dataChartDTO = DataChartConverter.INSTANCE.fromDataChart(dataChart);
        dataChartDTO.setFields(fields);
        return dataChartDTO;
    }

    @Override
    public DataChartDTO addChart(AddDataChartRequest request, UserAuthorizedAddition authorizedAddition) {
        DataChartTargetLocation chartTargetLocation = DataChartTargetLocation.valueOf(request.getTargetLocation());
        DataChart chart = this.repository.selectOne(
                DATA_CHART.TARGET_ID.eq(request.getTargetId()),
                DATA_CHART.TARGET_LOCATION.eq(chartTargetLocation),
                DATA_CHART.NAME.eq(request.getChartName())
        );
        if (chart != null) {
            throw new ApiException(StatusCodeConstants.DATA_CHART_ALREADY_EXISTS, request.getChartName());
        }
        chart = new DataChart()
                .setName(request.getChartName())
                .setChartType(DataChartType.valueOf(request.getChartType()))
                .setTargetLocation(chartTargetLocation)
                .setTargetId(request.getTargetId())
                .setCreateBy(authorizedAddition.getUserId());

        List<DataChartField> chartFields = new ArrayList<>();
        for (AddDataChartRequest.ChartField field : request.getFields()) {
            chartFields.add(this.toChartField(request.getTargetId(), field));
        }
        return this.addChart(chart, chartFields);
    }

    @Override
    public DataChart deleteChart(String chartId) {
        DataChart dataChart = this.selectById(chartId);
        if (dataChart == null || dataChart.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DATA_CHART_NOT_EXISTS, chartId);
        }
        dataChart.setDeleted(Boolean.TRUE);
        this.update(dataChart);
        return dataChart;
    }

    private DataChartField toChartField(String targetId, AddDataChartRequest.ChartField field) {
        DataChartFieldType chartFieldType = DataChartFieldType.valueOf(field.getFieldType());
        BiFunction<String, String, String> identifierStrategy = FIELD_IDENTIFIER_STRATEGIES.get(chartFieldType);
        if (identifierStrategy == null) {
            throw new IllegalArgumentException("Unsupported chart field type: " + chartFieldType);
        }

        String fieldIdentifier = identifierStrategy.apply(targetId, field.getFieldId());

        return new DataChartField()
                .setFieldId(field.getFieldId())
                .setFieldIdentifier(fieldIdentifier)
                .setFieldLabel(field.getFieldLabel())
                .setFieldType(chartFieldType);
    }
}

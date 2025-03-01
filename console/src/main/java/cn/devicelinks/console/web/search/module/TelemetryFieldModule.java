package cn.devicelinks.console.web.search.module;

import cn.devicelinks.framework.common.TelemetryMetricType;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 遥测数据检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class TelemetryFieldModule implements SearchFieldModule {

    SearchField ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_ID = SearchField.of(SearchFieldVariable.DEVICE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField METRIC_TYPE = SearchField.of(SearchFieldVariable.TELEMETRY_METRIC_TYPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(TelemetryMetricType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField METRIC_KEY = SearchField.of(SearchFieldVariable.TELEMETRY_METRIC_KEY)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField LAST_UPDATE_TIMESTAMP = SearchField.of(SearchFieldVariable.TELEMETRY_LAST_UPDATE_TIMESTAMP)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GreaterThan,
                    SearchFieldOperator.GreaterThanOrEqualTo,
                    SearchFieldOperator.LessThan,
                    SearchFieldOperator.LessThanOrEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Telemetry;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                ID,
                DEVICE_ID,
                METRIC_TYPE,
                METRIC_KEY,
                LAST_UPDATE_TIMESTAMP
        );
    }
}

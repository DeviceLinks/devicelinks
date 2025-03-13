package cn.devicelinks.console.web.search.module;

import cn.devicelinks.framework.common.DataChartTargetLocation;
import cn.devicelinks.framework.common.DataChartType;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据图表检索字段模版
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DataChartSearchFieldModule implements SearchFieldModule {

    SearchField CHART_NAME = SearchField.of(SearchFieldVariable.DATA_CHART_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField CHART_TYPE = SearchField.of(SearchFieldVariable.DATA_CHART_TYPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DataChartType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField CHART_TARGET_LOCATION = SearchField.of(SearchFieldVariable.DATA_CHART_TARGET_LOCATION)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DataChartTargetLocation.class)
            .setRequired(Boolean.TRUE)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField CHART_TARGET_ID = SearchField.of(SearchFieldVariable.DATA_CHART_TARGET_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setRequired(Boolean.TRUE)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DataChart;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(CHART_NAME, CHART_TYPE, CHART_TARGET_LOCATION, CHART_TARGET_ID);
    }
}

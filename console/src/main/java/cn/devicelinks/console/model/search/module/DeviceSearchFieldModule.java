package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.DeviceType;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData DEVICE_TYPE_DIRECT = SearchFieldOptionData.of().setLabel("直接连接").setValue(DeviceType.Direct);
    SearchFieldOptionData DEVICE_TYPE_GATEWAY = SearchFieldOptionData.of().setLabel("网关设备").setValue(DeviceType.Gateway);
    SearchFieldOptionData DEVICE_TYPE_GATEWAY_SUB = SearchFieldOptionData.of().setLabel("网关子设备").setValue(DeviceType.GatewaySub);

    SearchField DEVICE_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_CODE = SearchField.of(SearchFieldVariable.DEVICE_CODE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_NAME = SearchField.of(SearchFieldVariable.DEVICE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_TYPE = SearchField.of(SearchFieldVariable.DEVICE_TYPE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    DEVICE_TYPE_DIRECT,
                    DEVICE_TYPE_GATEWAY,
                    DEVICE_TYPE_GATEWAY_SUB
            )).setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField DEVICE_ACTIVATION_TIME = SearchField.of(SearchFieldVariable.DEVICE_ACTIVATION_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GreaterThan,
                    SearchFieldOperator.GreaterThanOrEqualTo,
                    SearchFieldOperator.LessThan,
                    SearchFieldOperator.LessThanOrEqualTo
            ));

    SearchField DEVICE_LAST_ONLINE_TIME = SearchField.of(SearchFieldVariable.DEVICE_LAST_ONLINE_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GreaterThan,
                    SearchFieldOperator.GreaterThanOrEqualTo,
                    SearchFieldOperator.LessThan,
                    SearchFieldOperator.LessThanOrEqualTo
            ));

    SearchField DEVICE_LAST_REPORT_TIME = SearchField.of(SearchFieldVariable.DEVICE_LAST_REPORT_TIME)
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
        return SearchFieldModuleIdentifier.Device;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                DEVICE_ID,
                DEVICE_CODE,
                DEVICE_NAME,
                DEVICE_TYPE,
                DEVICE_ACTIVATION_TIME,
                DEVICE_LAST_ONLINE_TIME,
                DEVICE_LAST_REPORT_TIME
        );
    }
}

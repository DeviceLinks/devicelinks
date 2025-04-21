package cn.devicelinks.api.support.search.module;

import cn.devicelinks.framework.common.web.search.*;
import cn.devicelinks.framework.common.DeviceStatus;
import cn.devicelinks.framework.common.DeviceType;
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

    SearchFieldOptionData ENABLED_BOOLEAN_TRUE = SearchFieldOptionData.of().setLabel("启用").setValue(Boolean.TRUE);
    SearchFieldOptionData ENABLED_BOOLEAN_FALSE = SearchFieldOptionData.of().setLabel("禁用").setValue(Boolean.FALSE);
    SearchFieldOptionData DELETED_BOOLEAN_TRUE = SearchFieldOptionData.of().setLabel("已删除").setValue(Boolean.TRUE);
    SearchFieldOptionData DELETED_BOOLEAN_FALSE = SearchFieldOptionData.of().setLabel("未删除").setValue(Boolean.FALSE);


    SearchField DEVICE_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_PRODUCT_ID = SearchField.of(SearchFieldVariable.PRODUCT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
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

    SearchField DEVICE_NOTE_NAME = SearchField.of(SearchFieldVariable.DEVICE_NOTE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DEVICE_TYPE = SearchField.of(SearchFieldVariable.DEVICE_TYPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DeviceType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField DEVICE_STATUS = SearchField.of(SearchFieldVariable.DEVICE_STATUS)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DeviceStatus.class)
            .setOperators(List.of(
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

    SearchField DEVICE_ENABLED = SearchField.of(SearchFieldVariable.ENABLED)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionStaticData(List.of(
                    ENABLED_BOOLEAN_TRUE,
                    ENABLED_BOOLEAN_FALSE
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    SearchField DEVICE_DELETED = SearchField.of(SearchFieldVariable.DELETED)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionStaticData(List.of(
                    DELETED_BOOLEAN_TRUE,
                    DELETED_BOOLEAN_FALSE
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Device;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                DEVICE_ID,
                DEVICE_PRODUCT_ID,
                DEVICE_CODE,
                DEVICE_NAME,
                DEVICE_NOTE_NAME,
                DEVICE_TYPE,
                DEVICE_STATUS,
                DEVICE_ACTIVATION_TIME,
                DEVICE_LAST_ONLINE_TIME,
                DEVICE_LAST_REPORT_TIME,
                DEVICE_ENABLED,
                DEVICE_DELETED
        );
    }
}

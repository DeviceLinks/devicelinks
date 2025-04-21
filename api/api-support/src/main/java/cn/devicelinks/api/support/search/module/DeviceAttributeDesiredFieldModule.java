package cn.devicelinks.api.support.search.module;

import cn.devicelinks.framework.common.web.search.*;
import cn.devicelinks.framework.common.DesiredAttributeStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备期望属性检索字段定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceAttributeDesiredFieldModule implements SearchFieldModule {

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
            .setRequired(true)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField MODULE_ID = SearchField.of(SearchFieldVariable.FUNCTION_MODULE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField ATTRIBUTE_ID = SearchField.of(SearchFieldVariable.ATTRIBUTE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField IDENTIFIER = SearchField.of(SearchFieldVariable.ATTRIBUTE_IDENTIFIER)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField STATUS = SearchField.of(SearchFieldVariable.DEVICE_ATTRIBUTE_DESIRED_STATUS)
            .setValueType(SearchFieldValueType.ENUM)
            .setEnumClass(DesiredAttributeStatus.class)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField LAST_UPDATE_TIME = SearchField.of(SearchFieldVariable.DEVICE_ATTRIBUTE_LAST_UPDATE_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GreaterThan,
                    SearchFieldOperator.LessThan,
                    SearchFieldOperator.GreaterThanOrEqualTo,
                    SearchFieldOperator.LessThanOrEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DeviceDesiredAttribute;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, IDENTIFIER, STATUS, LAST_UPDATE_TIME);
    }
}

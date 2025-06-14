package cn.devicelinks.api.support.search.module;

import cn.devicelinks.common.AttributeScope;
import cn.devicelinks.component.web.search.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备属性检索字段定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceAttributeFieldModule implements SearchFieldModule {

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

    SearchField LAST_REPORT_TIME = SearchField.of(SearchFieldVariable.DEVICE_ATTRIBUTE_REPORT_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GreaterThan,
                    SearchFieldOperator.LessThan,
                    SearchFieldOperator.GreaterThanOrEqualTo,
                    SearchFieldOperator.LessThanOrEqualTo
            ));

    SearchField SCOPE = SearchField.of(SearchFieldVariable.DEVICE_ATTRIBUTE_SCOPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(AttributeScope.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DeviceAttribute;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(ID, DEVICE_ID, MODULE_ID, ATTRIBUTE_ID, IDENTIFIER, LAST_REPORT_TIME, SCOPE);
    }
}

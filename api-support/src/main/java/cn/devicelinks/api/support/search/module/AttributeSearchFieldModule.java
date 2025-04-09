package cn.devicelinks.api.support.search.module;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.web.*;
import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldModule;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.common.web.SearchFieldVariable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 属性检索字段模板定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class AttributeSearchFieldModule implements SearchFieldModule {

    SearchField ATTRIBUTE_PRODUCT_ID = SearchField.of(SearchFieldVariable.PRODUCT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setRequired(true)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_MODULE_ID = SearchField.of(SearchFieldVariable.FUNCTION_MODULE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setRequired(true)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_PARENT_ID = SearchField.of(SearchFieldVariable.ATTRIBUTE_PARENT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField ATTRIBUTE_IDENTIFIER = SearchField.of(SearchFieldVariable.ATTRIBUTE_IDENTIFIER)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_DATA_TYPE = SearchField.of(SearchFieldVariable.ATTRIBUTE_DATA_TYPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(AttributeDataType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField ATTRIBUTE_NAME = SearchField.of(SearchFieldVariable.ATTRIBUTE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField WRITABLE = SearchField.of(SearchFieldVariable.ATTRIBUTE_WRITABLE)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Attribute;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                ATTRIBUTE_PRODUCT_ID,
                ATTRIBUTE_MODULE_ID,
                ATTRIBUTE_PARENT_ID,
                ATTRIBUTE_IDENTIFIER,
                ATTRIBUTE_DATA_TYPE,
                ATTRIBUTE_NAME,
                WRITABLE
        );
    }
}

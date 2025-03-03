package cn.devicelinks.console.web.search.module;

import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能模块检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class FunctionModuleSearchFieldModule implements SearchFieldModule {

    SearchField FUNCTION_MODULE_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField FUNCTION_MODULE_PRODUCT_ID = SearchField.of(SearchFieldVariable.PRODUCT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField FUNCTION_MODULE_NAME = SearchField.of(SearchFieldVariable.FUNCTION_MODULE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField FUNCTION_MODULE_IDENTIFIER = SearchField.of(SearchFieldVariable.FUNCTION_MODULE_IDENTIFIER)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.FunctionModule;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                FUNCTION_MODULE_ID,
                FUNCTION_MODULE_PRODUCT_ID,
                FUNCTION_MODULE_NAME,
                FUNCTION_MODULE_IDENTIFIER
        );
    }
}

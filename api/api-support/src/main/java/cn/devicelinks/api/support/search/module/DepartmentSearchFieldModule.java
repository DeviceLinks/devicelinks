package cn.devicelinks.api.support.search.module;

import cn.devicelinks.component.web.search.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 部门检索字段定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DepartmentSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData DELETED_BOOLEAN_TRUE = SearchFieldOptionData.of().setLabel("已删除").setValue(Boolean.TRUE);

    SearchFieldOptionData DELETED_BOOLEAN_FALSE = SearchFieldOptionData.of().setLabel("未删除").setValue(Boolean.FALSE);

    SearchField ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField NAME = SearchField.of(SearchFieldVariable.DEPARTMENT_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField IDENTIFIER = SearchField.of(SearchFieldVariable.DEPARTMENT_IDENTIFIER)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DELETED = SearchField.of(SearchFieldVariable.DELETED)
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
        return SearchFieldModuleIdentifier.Department;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(ID, NAME, IDENTIFIER, DELETED);
    }
}

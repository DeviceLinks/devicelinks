package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class LogSearchFieldModule implements SearchFieldModule {
    
    SearchFieldOptionData LOG_SUCCESS_TRUE = SearchFieldOptionData.of().setLabel("成功").setValue(true);
    SearchFieldOptionData LOG_SUCCESS_FALSE = SearchFieldOptionData.of().setLabel("失败").setValue(false);

    SearchField LOG_USER_ID = SearchField.of(SearchFieldVariable.LOG_USER_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn)
            );

    SearchField LOG_ACTION = SearchField.of(SearchFieldVariable.LOG_ACTION)
            .setValueType(SearchFieldValueType.ENUM)
            .setEnumClass(LogAction.class)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField LOG_OBJECT_TYPE = SearchField.of(SearchFieldVariable.LOG_OBJECT_TYPE)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(LogObjectType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField LOG_OBJECT_ID = SearchField.of(SearchFieldVariable.LOG_OBJECT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField LOG_MSG = SearchField.of(SearchFieldVariable.LOG_MSG)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField LOG_SUCCESS = SearchField.of(SearchFieldVariable.LOG_SUCCESS)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    LOG_SUCCESS_TRUE,
                    LOG_SUCCESS_FALSE
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Log;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                LOG_USER_ID,
                LOG_ACTION,
                LOG_OBJECT_TYPE,
                LOG_OBJECT_ID,
                LOG_MSG,
                LOG_SUCCESS
        );
    }
}

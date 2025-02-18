package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class UserSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData USER_ACTIVATE_METHOD_SEND_URL_EMAIL = SearchFieldOptionData.of().setLabel("向邮箱发送激活邮件").setValue(UserActivateMethod.SendUrlToEmail);
    SearchFieldOptionData USER_ACTIVATE_METHOD_SHOW_URL = SearchFieldOptionData.of().setLabel("显示激活链接").setValue(UserActivateMethod.ShowUrl);

    SearchField USER_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField USER_NAME = SearchField.of(SearchFieldVariable.USER_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField USER_ACCOUNT = SearchField.of(SearchFieldVariable.USER_ACCOUNT)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField USER_EMAIL = SearchField.of(SearchFieldVariable.USER_EMAIL)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField USER_PHONE = SearchField.of(SearchFieldVariable.USER_PHONE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField USER_ACTIVATE_METHOD = SearchField.of(SearchFieldVariable.USER_ACTIVATE_METHOD)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    USER_ACTIVATE_METHOD_SEND_URL_EMAIL,
                    USER_ACTIVATE_METHOD_SHOW_URL
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.User;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                USER_ID,
                USER_NAME,
                USER_ACCOUNT,
                USER_EMAIL,
                USER_PHONE,
                USER_ACTIVATE_METHOD
        );
    }
}

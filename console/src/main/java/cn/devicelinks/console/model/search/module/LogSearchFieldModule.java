package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.web.*;

import java.util.List;

/**
 * 日志检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class LogSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData LOG_OBJECT_TYPE_USER = SearchFieldOptionData.of().setLabel("用户").setValue(LogObjectType.User);
    SearchFieldOptionData LOG_OBJECT_TYPE_DEPARTMENT = SearchFieldOptionData.of().setLabel("部门").setValue(LogObjectType.Department);
    SearchFieldOptionData LOG_OBJECT_TYPE_DEVICE = SearchFieldOptionData.of().setLabel("设备").setValue(LogObjectType.Device);
    SearchFieldOptionData LOG_OBJECT_TYPE_PRODUCT = SearchFieldOptionData.of().setLabel("产品").setValue(LogObjectType.Product);
    SearchFieldOptionData LOG_OBJECT_TYPE_OTA = SearchFieldOptionData.of().setLabel("OTA升级包").setValue(LogObjectType.Ota);
    SearchFieldOptionData LOG_OBJECT_TYPE_FUNCTION_MODULE = SearchFieldOptionData.of().setLabel("功能模块").setValue(LogObjectType.FunctionModule);
    SearchFieldOptionData LOG_OBJECT_TYPE_ATTRIBUTE = SearchFieldOptionData.of().setLabel("属性").setValue(LogObjectType.Attribute);

    SearchFieldOptionData LOG_ACTION_LOGIN = SearchFieldOptionData.of().setLabel("登录").setValue(LogAction.Login);
    SearchFieldOptionData LOG_ACTION_LOGOUT = SearchFieldOptionData.of().setLabel("登出").setValue(LogAction.Logout);
    SearchFieldOptionData LOG_ACTION_ADD = SearchFieldOptionData.of().setLabel("新增").setValue(LogAction.Add);
    SearchFieldOptionData LOG_ACTION_UPDATE = SearchFieldOptionData.of().setLabel("修改").setValue(LogAction.Update);
    SearchFieldOptionData LOG_ACTION_DELETE = SearchFieldOptionData.of().setLabel("删除").setValue(LogAction.Delete);
    SearchFieldOptionData LOG_ACTION_UPDATE_STATUS = SearchFieldOptionData.of().setLabel("修改状态").setValue(LogAction.UpdateStatus);
    SearchFieldOptionData LOG_ACTION_UPDATE_ATTRIBUTE = SearchFieldOptionData.of().setLabel("修改属性").setValue(LogAction.UpdateAttribute);
    SearchFieldOptionData LOG_ACTION_CHANGE_PWD = SearchFieldOptionData.of().setLabel("修改密码").setValue(LogAction.ChangePwd);
    SearchFieldOptionData LOG_ACTION_BIND = SearchFieldOptionData.of().setLabel("绑定").setValue(LogAction.Bind);
    SearchFieldOptionData LOG_ACTION_UNBIND = SearchFieldOptionData.of().setLabel("解绑").setValue(LogAction.Unbind);


    SearchFieldOptionData LOG_SUCCESS_TRUE = SearchFieldOptionData.of().setLabel("成功").setValue(true);
    SearchFieldOptionData LOG_SUCCESS_FALSE = SearchFieldOptionData.of().setLabel("失败").setValue(false);

    SearchField LOG_ACTION = SearchField.of(SearchFieldVariable.LOG_ACTION)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    LOG_ACTION_LOGIN,
                    LOG_ACTION_LOGOUT,
                    LOG_ACTION_ADD,
                    LOG_ACTION_UPDATE,
                    LOG_ACTION_DELETE,
                    LOG_ACTION_UPDATE_STATUS,
                    LOG_ACTION_UPDATE_ATTRIBUTE,
                    LOG_ACTION_CHANGE_PWD,
                    LOG_ACTION_BIND,
                    LOG_ACTION_UNBIND
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField LOG_OBJECT_TYPE = SearchField.of(SearchFieldVariable.LOG_OBJECT_TYPE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    LOG_OBJECT_TYPE_USER,
                    LOG_OBJECT_TYPE_DEVICE,
                    LOG_OBJECT_TYPE_DEPARTMENT,
                    LOG_OBJECT_TYPE_PRODUCT,
                    LOG_OBJECT_TYPE_OTA,
                    LOG_OBJECT_TYPE_FUNCTION_MODULE,
                    LOG_OBJECT_TYPE_ATTRIBUTE
            ))
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
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
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
                LOG_ACTION,
                LOG_OBJECT_TYPE,
                LOG_OBJECT_ID,
                LOG_MSG,
                LOG_SUCCESS
        );
    }
}

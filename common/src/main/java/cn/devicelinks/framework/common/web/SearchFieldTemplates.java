/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.common.web;

import cn.devicelinks.framework.common.DeviceType;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.UserActivateMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检索字段模板组
 * <p>
 * 定义不同功能模块的检索字段模板
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SearchFieldTemplates {

    // SearchFieldOptionData List
    SearchFieldOptionData USER_ACTIVATE_METHOD_SEND_URL_EMAIL = SearchFieldOptionData.of().setLabel("向邮箱发送激活邮件").setValue(UserActivateMethod.SendUrlToEmail);
    SearchFieldOptionData USER_ACTIVATE_METHOD_SHOW_URL = SearchFieldOptionData.of().setLabel("显示激活链接").setValue(UserActivateMethod.ShowUrl);
    SearchFieldOptionData DEVICE_TYPE_DIRECT = SearchFieldOptionData.of().setLabel("直接连接").setValue(DeviceType.Direct);
    SearchFieldOptionData DEVICE_TYPE_GATEWAY = SearchFieldOptionData.of().setLabel("网关设备").setValue(DeviceType.Gateway);
    SearchFieldOptionData DEVICE_TYPE_GATEWAY_SUB = SearchFieldOptionData.of().setLabel("网关子设备").setValue(DeviceType.GatewaySub);

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

    // SearchFieldTemplate List
    SearchFieldTemplate ID = SearchFieldTemplate.of(SearchField.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate USER_NAME = SearchFieldTemplate.of(SearchField.USER_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate USER_ACCOUNT = SearchFieldTemplate.of(SearchField.USER_ACCOUNT)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate USER_EMAIL = SearchFieldTemplate.of(SearchField.USER_EMAIL)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate USER_PHONE = SearchFieldTemplate.of(SearchField.USER_PHONE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate USER_ACTIVATE_METHOD = SearchFieldTemplate.of(SearchField.USER_ACTIVATE_METHOD)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    USER_ACTIVATE_METHOD_SEND_URL_EMAIL,
                    USER_ACTIVATE_METHOD_SHOW_URL
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL
            ));

    SearchFieldTemplate DEVICE_CODE = SearchFieldTemplate.of(SearchField.DEVICE_CODE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate DEVICE_NAME = SearchFieldTemplate.of(SearchField.DEVICE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate DEVICE_TYPE = SearchFieldTemplate.of(SearchField.DEVICE_TYPE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    DEVICE_TYPE_DIRECT,
                    DEVICE_TYPE_GATEWAY,
                    DEVICE_TYPE_GATEWAY_SUB
            )).setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.IN,
                    SearchFieldOperator.NOT_IN
            ));

    SearchFieldTemplate DEVICE_ACTIVATION_TIME = SearchFieldTemplate.of(SearchField.DEVICE_ACTIVATION_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GREATER_THAN,
                    SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                    SearchFieldOperator.LESS_THAN,
                    SearchFieldOperator.LESS_THAN_OR_EQUAL
            ));

    SearchFieldTemplate DEVICE_LAST_ONLINE_TIME = SearchFieldTemplate.of(SearchField.DEVICE_LAST_ONLINE_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GREATER_THAN,
                    SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                    SearchFieldOperator.LESS_THAN,
                    SearchFieldOperator.LESS_THAN_OR_EQUAL
            ));

    SearchFieldTemplate DEVICE_LAST_REPORT_TIME = SearchFieldTemplate.of(SearchField.DEVICE_LAST_REPORT_TIME)
            .setValueType(SearchFieldValueType.DATE_TIME)
            .setComponentType(SearchFieldComponentType.DATE_TIME)
            .setOperators(List.of(
                    SearchFieldOperator.GREATER_THAN,
                    SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                    SearchFieldOperator.LESS_THAN,
                    SearchFieldOperator.LESS_THAN_OR_EQUAL
            ));

    SearchFieldTemplate LOG_ACTION = SearchFieldTemplate.of(SearchField.LOG_ACTION)
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
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.IN,
                    SearchFieldOperator.NOT_IN
            ));

    SearchFieldTemplate LOG_OBJECT_TYPE = SearchFieldTemplate.of(SearchField.LOG_OBJECT_TYPE)
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
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.IN,
                    SearchFieldOperator.NOT_IN
            ));

    SearchFieldTemplate LOG_OBJECT_ID = SearchFieldTemplate.of(SearchField.LOG_OBJECT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate LOG_MSG = SearchFieldTemplate.of(SearchField.LOG_MSG)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));

    SearchFieldTemplate LOG_SUCCESS = SearchFieldTemplate.of(SearchField.LOG_SUCCESS)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    LOG_SUCCESS_TRUE,
                    LOG_SUCCESS_FALSE
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL
            ));

    // List of search field templates for each module
    Map<SearchFieldModule, List<SearchFieldTemplate>> MODULE_SEARCH_FIELD_TEMPLATE_MAP = new HashMap<>() {{
        put(SearchFieldModule.User, List.of(
                ID,
                USER_NAME,
                USER_ACCOUNT,
                USER_EMAIL,
                USER_PHONE,
                USER_ACTIVATE_METHOD
        ));
        put(SearchFieldModule.Device, List.of(
                ID,
                DEVICE_CODE,
                DEVICE_NAME,
                DEVICE_TYPE,
                DEVICE_ACTIVATION_TIME,
                DEVICE_LAST_ONLINE_TIME,
                DEVICE_LAST_REPORT_TIME
        ));
        put(SearchFieldModule.Log, List.of(
                LOG_ACTION,
                LOG_OBJECT_TYPE,
                LOG_OBJECT_ID,
                LOG_MSG,
                LOG_SUCCESS
        ));
    }};

}

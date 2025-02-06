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
public interface SearchFieldTemplateGroup {

    // @formatter:off
    SearchFieldTemplate ID = SearchFieldTemplate.of()
            .setField("id")
            .setFieldText("ID")
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EQUAL,
                    SearchFieldOperator.NOT_EQUAL,
                    SearchFieldOperator.LIKE,
                    SearchFieldOperator.NOT_LIKE
            ));
    // @formatter:on

    Map<SearchFieldModule, List<SearchFieldTemplate>> MODULE_SEARCH_FIELD_TEMPLATE_MAP = new HashMap<>() {{
        // SearchFieldModule#User
        put(SearchFieldModule.User, List.of(
                ID,
                SearchFieldTemplate.of()
                        .setField("name")
                        .setFieldText("用户名称")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("account")
                        .setFieldText("账号")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("email")
                        .setFieldText("邮箱地址")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("phone")
                        .setFieldText("手机号")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("activateMethod")
                        .setFieldText("激活方式")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.SELECT)
                        .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                        .setOptionStaticData(List.of(
                                SearchFieldOptionData.of().setLabel("向邮箱发送激活邮件").setValue(UserActivateMethod.SendUrlToEmail.toString()),
                                SearchFieldOptionData.of().setLabel("显示激活链接").setValue(UserActivateMethod.ShowUrl.toString())
                        ))
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL
                        ))
        ));
        // SearchFieldModule#Device
        put(SearchFieldModule.Device, List.of(
                ID,
                SearchFieldTemplate.of()
                        .setField("deviceCode")
                        .setFieldText("设备号")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("name")
                        .setFieldText("设备名称")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("deviceType")
                        .setFieldText("设备类型")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.SELECT)
                        .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                        .setOptionStaticData(List.of(
                                SearchFieldOptionData.of().setLabel("直连设备").setValue(DeviceType.Direct.toString()),
                                SearchFieldOptionData.of().setLabel("网关设备").setValue(DeviceType.Gateway.toString()),
                                SearchFieldOptionData.of().setLabel("网关子设备").setValue(DeviceType.GatewaySub.toString())
                        )).setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.IN,
                                SearchFieldOperator.NOT_IN
                        )),
                SearchFieldTemplate.of()
                        .setField("activationTime")
                        .setFieldText("激活时间")
                        .setValueType(SearchFieldValueType.DATE_TIME)
                        .setComponentType(SearchFieldComponentType.DATE_TIME)
                        .setOperators(List.of(
                                SearchFieldOperator.GREATER_THAN,
                                SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                                SearchFieldOperator.LESS_THAN,
                                SearchFieldOperator.LESS_THAN_OR_EQUAL
                        )),
                SearchFieldTemplate.of()
                        .setField("lastOnlineTime")
                        .setFieldText("最后上线时间")
                        .setValueType(SearchFieldValueType.DATE_TIME)
                        .setComponentType(SearchFieldComponentType.DATE_TIME)
                        .setOperators(List.of(
                                SearchFieldOperator.GREATER_THAN,
                                SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                                SearchFieldOperator.LESS_THAN,
                                SearchFieldOperator.LESS_THAN_OR_EQUAL
                        )),
                SearchFieldTemplate.of()
                        .setField("lastReportTime")
                        .setFieldText("最后上报时间")
                        .setValueType(SearchFieldValueType.DATE_TIME)
                        .setComponentType(SearchFieldComponentType.DATE_TIME)
                        .setOperators(List.of(
                                SearchFieldOperator.GREATER_THAN,
                                SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                                SearchFieldOperator.LESS_THAN,
                                SearchFieldOperator.LESS_THAN_OR_EQUAL
                        ))
        ));
        // SearchFieldModule#Log
        put(SearchFieldModule.Log, List.of(
                SearchFieldTemplate.of()
                        .setField("action")
                        .setFieldText("操作动作")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.SELECT)
                        .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                        .setOptionStaticData(List.of(
                                SearchFieldOptionData.of().setLabel("登录").setValue(LogAction.Login.toString()),
                                SearchFieldOptionData.of().setLabel("登出").setValue(LogAction.Logout.toString()),
                                SearchFieldOptionData.of().setLabel("新增").setValue(LogAction.Add.toString()),
                                SearchFieldOptionData.of().setLabel("修改").setValue(LogAction.Update.toString()),
                                SearchFieldOptionData.of().setLabel("删除").setValue(LogAction.Delete.toString()),
                                SearchFieldOptionData.of().setLabel("修改状态").setValue(LogAction.UpdateStatus.toString()),
                                SearchFieldOptionData.of().setLabel("修改属性").setValue(LogAction.UpdateAttribute.toString()),
                                SearchFieldOptionData.of().setLabel("修改密码").setValue(LogAction.ChangePwd.toString()),
                                SearchFieldOptionData.of().setLabel("绑定").setValue(LogAction.Bind.toString()),
                                SearchFieldOptionData.of().setLabel("解绑").setValue(LogAction.Unbind.toString())
                        ))
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.IN,
                                SearchFieldOperator.NOT_IN
                        )),
                SearchFieldTemplate.of()
                        .setField("objectType")
                        .setFieldText("对象类型")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.SELECT)
                        .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                        .setOptionStaticData(List.of(
                                SearchFieldOptionData.of().setLabel("用户").setValue(LogObjectType.User.toString()),
                                SearchFieldOptionData.of().setLabel("设备").setValue(LogObjectType.Device.toString()),
                                SearchFieldOptionData.of().setLabel("部门").setValue(LogObjectType.Department.toString()),
                                SearchFieldOptionData.of().setLabel("产品").setValue(LogObjectType.Product.toString()),
                                SearchFieldOptionData.of().setLabel("OTA升级包").setValue(LogObjectType.Ota.toString()),
                                SearchFieldOptionData.of().setLabel("功能模块").setValue(LogObjectType.FunctionModule.toString()),
                                SearchFieldOptionData.of().setLabel("属性").setValue(LogObjectType.Attribute.toString())

                        ))
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.IN,
                                SearchFieldOperator.NOT_IN
                        )),
                SearchFieldTemplate.of()
                        .setField("objectId")
                        .setFieldText("对象ID")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("msg")
                        .setFieldText("消息内容")
                        .setValueType(SearchFieldValueType.STRING)
                        .setComponentType(SearchFieldComponentType.INPUT)
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL,
                                SearchFieldOperator.LIKE,
                                SearchFieldOperator.NOT_LIKE
                        )),
                SearchFieldTemplate.of()
                        .setField("success")
                        .setFieldText("是否成功")
                        .setValueType(SearchFieldValueType.BOOLEAN)
                        .setComponentType(SearchFieldComponentType.SELECT)
                        .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                        .setOptionStaticData(List.of(
                                SearchFieldOptionData.of().setLabel("成功").setValue("1"),
                                SearchFieldOptionData.of().setLabel("失败").setValue("0")
                        ))
                        .setOperators(List.of(
                                SearchFieldOperator.EQUAL,
                                SearchFieldOperator.NOT_EQUAL
                        ))
        ));
    }};
}

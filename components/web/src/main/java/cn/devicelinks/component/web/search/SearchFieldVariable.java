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

package cn.devicelinks.component.web.search;

import lombok.Getter;

/**
 * 检索字段
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum SearchFieldVariable {
    ID("id", "ID"),
    DELETED("deleted", "是否删除"),
    ENABLED("enabled", "是否启用"),
    CREATE_BY("createBy", "创建人ID"),
    CREATE_TIME("createTime", "创建时间"),

    USER_NAME("name", "用户名称"),
    USER_ACCOUNT("account", "用户账号"),
    USER_PHONE("phone", "手机号"),
    USER_EMAIL("email", "邮箱地址"),
    USER_ACTIVATE_METHOD("activateMethod", "激活方式"),

    DEPARTMENT_ID("departmentId", "部门ID"),
    DEPARTMENT_NAME("name", "部门名称"),
    DEPARTMENT_IDENTIFIER("identifier", "标识符"),

    DEVICE_ID("deviceId", "设备ID"),
    DEVICE_CODE("deviceCode", "设备号"),
    DEVICE_NAME("deviceName", "设备名称"),
    DEVICE_NOTE_NAME("noteName", "备注名称"),
    DEVICE_TYPE("deviceType", "设备类型"),
    DEVICE_STATUS("status", "设备状态"),
    DEVICE_ACTIVATION_TIME("activationTime", "激活时间"),
    DEVICE_LAST_ONLINE_TIME("lastOnlineTime", "最后上线时间"),
    DEVICE_LAST_REPORT_TIME("lastReportTime", "最后上报时间"),

    LOG_USER_ID("userId", "用户ID"),
    LOG_ACTION("action", "动作"),
    LOG_OBJECT_TYPE("objectType", "对象类型"),
    LOG_OBJECT_ID("objectId", "对象ID"),
    LOG_MSG("msg", "日志内容"),
    LOG_SUCCESS("success", "是否成功"),


    FUNCTION_MODULE_ID("moduleId", "功能模块ID"),
    FUNCTION_MODULE_NAME("name", "模块名称"),
    FUNCTION_MODULE_IDENTIFIER("identifier", "模块标识符"),

    ATTRIBUTE_ID("attributeId", "属性ID"),
    ATTRIBUTE_NAME("name", "属性名称"),
    ATTRIBUTE_PARENT_ID("pid", "属性上级ID"),
    ATTRIBUTE_DATA_TYPE("dataType", "数据类型"),
    ATTRIBUTE_IDENTIFIER("identifier", "属性标识符"),
    ATTRIBUTE_WRITABLE("writable", "是否可写"),

    PRODUCT_ID("productId", "产品ID"),
    PRODUCT_NAME("name", "产品名称"),
    PRODUCT_DEVICE_TYPE("deviceType", "设备类型"),
    PRODUCT_NETWORKING_AWAY("networkingAway", "联网方式"),
    PRODUCT_ACCESS_GATEWAY_PROTOCOL("accessGatewayProtocol", "接入网关协议"),
    PRODUCT_DATA_FORMAT("dataFormat", "数据格式"),
    PRODUCT_DYNAMIC_REGISTRATION("dynamicRegistration", "是否动态注册"),
    PRODUCT_STATUS("status", "状态"),


    TELEMETRY_METRIC_TYPE("metricType", "遥测数据类型"),
    TELEMETRY_METRIC_KEY("key", "遥测数据Key"),
    TELEMETRY_LAST_UPDATE_TIMESTAMP("lastUpdateTime", "最后更新时间"),

    DEVICE_ATTRIBUTE_REPORT_TIME("lastReportTime", "设备属性上报时间"),
    DEVICE_ATTRIBUTE_LAST_UPDATE_TIME("lastUpdateTime", "设备属性最后更新时间"),
    DEVICE_ATTRIBUTE_DESIRED_STATUS("status", "设备期望属性状态"),

    DATA_CHART_NAME("name", "数据图表名称"),
    DATA_CHART_TYPE("chartType", "数据图表类型"),
    DATA_CHART_TARGET_LOCATION("targetLocation", "数据图表位置"),
    DATA_CHART_TARGET_ID("targetId", "数据图表业务目标ID"),


    DEVICE_PROFILE_ID("profileId", "设备配置文件ID"),
    DEVICE_PROFILE_NAME("name", "设备配置文件名称"),
    DEVICE_PROFILE_FIRMWARE_ID("firmwareId", "固件ID"),
    DEVICE_PROFILE_SOFTWARE_ID("softwareId", "软件ID"),
    DEVICE_PROFILE_PROVISION_REGISTRATION_STRATEGY("provisionRegistrationStrategy", "预注册策略"),

    DEVICE_TAG_NAME("name", "设备标签名称"),
    ;
    private final String field;
    private final String text;

    SearchFieldVariable(String field, String text) {
        this.field = field;
        this.text = text;
    }
}

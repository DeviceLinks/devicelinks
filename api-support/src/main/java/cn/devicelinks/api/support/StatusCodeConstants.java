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

package cn.devicelinks.api.support;

import cn.devicelinks.framework.common.api.StatusCode;

/**
 * The {@link StatusCode} Constants
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface StatusCodeConstants {

    StatusCode SEARCH_FIELD_ENUM_VALUE_ILLEGAL = StatusCode.build("SEARCH_FIELD_ENUM_VALUE_ILLEGAL", "检索字段：[%s]，枚举值：[%s]，并未定义，请检查是否有效.");
    StatusCode SEARCH_FIELD_REQUIRED_NOT_PRESENT = StatusCode.build("SEARCH_FIELD_REQUIRED_NOT_PRESENT", "检索字段：[%s]，必须全部传递.");

    StatusCode SYS_SETTING_NOT_FOUND = StatusCode.build("SYS_SETTING_NOT_FOUND", "全局设置：%s，不存在.");
    StatusCode SYS_SETTING_VALUE_TYPE_MISMATCH = StatusCode.build("SYS_SETTING_VALUE_TYPE_MISMATCH", "全局设置[%s]，数据类型不匹配，请检查数据格式是否正确.");
    StatusCode SYS_SETTING_VALUE_TYPE_NOT_SUPPORT = StatusCode.build("SYS_SETTING_VALUE_TYPE_NOT_SUPPORT", "全局设置[%s]，数据类型：[%s]，不在支持的数据");

    /**
     * User
     */
    StatusCode USER_NOT_FOUND = StatusCode.build("USER_NOT_FOUND", "用户：%s，不存在.");
    StatusCode USER_ALREADY_EXISTS = StatusCode.build("USER_ALREADY_EXISTS", "用户已经存在，请检查账号、邮箱、手机号是否重复.");
    StatusCode USER_EMAIL_CANNOT_EMPTY = StatusCode.build("USER_EMAIL_CANNOT_EMPTY", "用户邮箱地址不允许为空.");
    StatusCode USER_ACTIVE_METHOD_CANNOT_EMPTY = StatusCode.build("USER_ACTIVE_METHOD_CANNOT_EMPTY", "用户激活方式不可以为空.");

    /**
     * Department
     */
    StatusCode DEPARTMENT_NOT_FOUND = StatusCode.build("DEPARTMENT_NOT_FOUND", "部门：%s，不存在.");
    StatusCode DEPARTMENT_ALREADY_EXISTS = StatusCode.build("DEPARTMENT_ALREADY_EXISTS", "部门已经存在，请检查名称、标识符是否重复.");
    StatusCode DEPARTMENT_PARENT_NOT_EXISTS = StatusCode.build("DEPARTMENT_PARENT_NOT_EXISTS", "上级部门不存在.");

    /**
     * Function Module
     */
    StatusCode FUNCTION_MODULE_NOT_FOUND = StatusCode.build("FUNCTION_MODULE_NOT_FOUND", "功能模块：%s，不存在.");
    StatusCode FUNCTION_MODULE_ALREADY_EXISTS = StatusCode.build("FUNCTION_MODULE_ALREADY_EXISTS", "功能模块已经存在，请检查名称、标识符是否重复.");
    StatusCode FUNCTION_MODULE_NOT_BELONG_PRODUCT = StatusCode.build("FUNCTION_MODULE_NOT_BELONG_PRODUCT", "功能模块：%s，不属于产品：%s.");
    /**
     * Product
     */
    StatusCode PRODUCT_NOT_EXISTS = StatusCode.build("PRODUCT_NOT_EXISTS", "产品：%s，不存在.");
    StatusCode PRODUCT_ALREADY_EXISTS = StatusCode.build("PRODUCT_ALREADY_EXISTS", "产品：%s，已经存在，请检查名称、ProductKey是否重复.");
    StatusCode PRODUCT_NOT_IN_DEVELOPMENT_STATUS = StatusCode.build("PRODUCT_NOT_IN_DEVELOPMENT_STATUS", "产品：%s，不在开发状态，无法操作.");
    StatusCode PRODUCT_PUBLISHED = StatusCode.build("PRODUCT_PUBLISHED", "产品：%s，已发布，无法操作.");
    StatusCode PRODUCT_HAS_RELATED_DEVICES = StatusCode.build("PRODUCT_HAS_RELATED_DEVICES", "产品：%s，存在关联的设备，无法删除.");

    StatusCode ATTRIBUTE_NOT_FOUND = StatusCode.build("ATTRIBUTE_NOT_FOUND", "属性：%s，不存在.");
    StatusCode ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE = StatusCode.build("ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE", "属性：%s，不属于功能模块：%s.");
    StatusCode ATTRIBUTE_DATA_TYPE_NOT_MATCH = StatusCode.build("ATTRIBUTE_DATA_TYPE_NOT_MATCH", "属性：%s，数据类型不匹配.");
    StatusCode ATTRIBUTE_NOT_BELONG_PRODUCT = StatusCode.build("ATTRIBUTE_NOT_BELONG_PRODUCT", "属性：%s，不属于产品：%s.");
    StatusCode ATTRIBUTE_NOT_WRITEABLE = StatusCode.build("ATTRIBUTE_NOT_WRITEABLE", "属性：%s，不允许设置值.");
    StatusCode ATTRIBUTE_ID_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_ID_CANNOT_BLANK", "属性ID不允许为空.");
    StatusCode ATTRIBUTE_IDENTIFIER_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_IDENTIFIER_CANNOT_BLANK", "属性标识符不允许为空.");
    StatusCode ATTRIBUTE_DATA_TYPE_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_DATA_TYPE_CANNOT_BLANK", "属性数据类型不允许为空.");
    StatusCode ATTRIBUTE_NOT_COMMON_NOT_ALLOW_SET_DESIRED = StatusCode.build("ATTRIBUTE_NOT_COMMON_NOT_ALLOW_SET_DESIRED", "属性：%s，不是公共属性，不支持设置期望值.");

    StatusCode DEVICE_ALREADY_EXISTS = StatusCode.build("DEVICE_ALREADY_EXISTS", "设备：%s，已经存在，请检查产品下设备唯一码是否重复.");
    StatusCode DEVICE_NOT_EXISTS = StatusCode.build("DEVICE_NOT_EXISTS", "设备：%s，不存在.");
    StatusCode INVALID_DEVICE_STATIC_TOKEN = StatusCode.build("INVALID_DEVICE_STATIC_TOKEN", "无效的设备请求令牌.");
    StatusCode INVALID_DEVICE_DYNAMIC_TOKEN_SECRET = StatusCode.build("INVALID_DEVICE_DYNAMIC_TOKEN_SECRET", "无效的动态令牌DeviceSecret.");
    StatusCode DEVICE_STATIC_TOKEN_ALREADY_EXISTS = StatusCode.build("DEVICE_STATIC_TOKEN_ALREADY_EXISTS", "设备请求令牌已经存在.");
    StatusCode DEVICE_IS_ENABLE_NOT_ALLOWED_DELETE = StatusCode.build("DEVICE_IS_ENABLE_NOT_ALLOWED_DELETE", "设备：%s，目前处于启用状态，不允许删除.");
    StatusCode INVALID_DEVICE_MQTT_BASIC_AUTH = StatusCode.build("INVALID_DEVICE_MQTT_BASIC_AUTH", "无效的MQTT Basic Auth.");
    StatusCode DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS = StatusCode.build("DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS", "MQTT客户端ID已经存在.");
    StatusCode INVALID_DEVICE_X509_PEM = StatusCode.build("INVALID_DEVICE_X509_PEM", "无效的X509 Pem证书.");
    StatusCode DEVICE_AUTHENTICATION_NOT_EXISTS = StatusCode.build("DEVICE_AUTHENTICATION_NOT_EXISTS", "设备：%s，认证信息不存在.");

    StatusCode DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID", "无效的期望属性值，请检查与数据类型是否匹配.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_DATA_TYPE_NOT_MATCH = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_DATA_TYPE_NOT_MATCH", "期望属性：%s，与现有的数据类型不匹配.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND", "设备期望属性：%s，不存在.");
    StatusCode DEVICE_ATTRIBUTE_NOT_FOUND = StatusCode.build("DEVICE_ATTRIBUTE_NOT_FOUND", "设备上报属性：%s，不存在.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_NOT_UNKNOWN = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_NOT_UNKNOWN", "设备期望属性：%s，不是未知属性，无法提取.");
    StatusCode DEVICE_ATTRIBUTE_NOT_UNKNOWN = StatusCode.build("DEVICE_ATTRIBUTE_NOT_UNKNOWN", "设备属性：%s，不是未知属性.");
    StatusCode DEVICE_ATTRIBUTE_NOT_KNOWN = StatusCode.build("DEVICE_ATTRIBUTE_NOT_KNOWN", "设备属性：%s，不是已知属性.");
    StatusCode DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART = StatusCode.build("DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART", "设备属性：%s，的数据类型不支持添加到图表.");


    StatusCode TELEMETRY_NOT_EXISTS = StatusCode.build("TELEMETRY_NOT_EXISTS", "设备遥测数据：%s，不存在.");
    StatusCode TELEMETRY_DATA_TYPE_CANNOT_ADD_CHART = StatusCode.build("TELEMETRY_DATA_TYPE_CANNOT_ADD_CHART", "遥测数据：%s，的数据类型不支持添加到图表.");

    StatusCode DATA_CHART_ALREADY_EXISTS = StatusCode.build("DATA_CHART_ALREADY_EXISTS", "同一目标位置下数据图表：%s，已经存在.");
    StatusCode DATA_CHART_NOT_EXISTS = StatusCode.build("DATA_CHART_NOT_EXISTS", "数据图表：%s，不存在.");

    StatusCode DEVICE_PROFILE_NOT_EXISTS = StatusCode.build("DEVICE_PROFILE_NOT_EXISTS", "设备配置文件：%s，不存在.");
    StatusCode DEVICE_PROFILE_ALREADY_EXISTS = StatusCode.build("DEVICE_PROFILE_ALREADY_EXISTS", "设备配置文件：%s，已经存在.");
    StatusCode DEVICE_PROFILE_PROVISION_ADDITION_INVALID = StatusCode.build("DEVICE_PROFILE_PROVISION_ADDITION_INVALID", "设备配置文件预配置附加参数验证失败.");
    StatusCode DEVICE_PROFILE_PROVISION_VALID_SECOND_ERROR = StatusCode.build("DEVICE_PROFILE_PROVISION_VALID_SECOND_ERROR", "设备配置文件预配置动态令牌有效时长验证失败，有效时长范围：%d（包含） ~ %d（包含）.");
    StatusCode DEVICE_PROFILE_LOG_LEVELS_INVALID = StatusCode.build("DEVICE_PROFILE_LOG_LEVELS_INVALID", "设备配置日志等级附加信息验证失败，如果选择了产品请检查功能模块是否所属该产品，如果并未选择产品仅允许传递[default]功能模块的日志配置.");
    StatusCode DEVICE_PROFILE_DEFAULT_CANNOT_DELETE = StatusCode.build("DEVICE_PROFILE_DEFAULT_CANNOT_DELETE", "默认的设备配置文件不允许删除.");
    StatusCode DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID = StatusCode.build("DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID", "将设备配置文件批量设置给设备时，设置方式与参数验证失败，设置方式：%s.");


    StatusCode OTA_FIRMWARE_NOT_EXISTS = StatusCode.build("OTA_FIRMWARE_NOT_EXISTS", "固件OTA：%s，不存在.");
    StatusCode OTA_SOFTWARE_NOT_EXISTS = StatusCode.build("OTA_SOFTWARE_NOT_EXISTS", "软件OTA：%s，不存在.");

    StatusCode DEVICE_NOT_HAVE_SECRET = StatusCode.build("DEVICE_NOT_HAVE_SECRET", "设备不存在密钥.");
    StatusCode DEVICE_SECRET_DECRYPTION_ERROR = StatusCode.build("DEVICE_SECRET_DECRYPTION_ERROR", "设备密钥解密失败.");
    StatusCode DEVICE_SECRET_KEY_INVALID = StatusCode.build("DEVICE_SECRET_KEY_INVALID", "设备密钥Key无效.");
    StatusCode DEVICE_SECRET_NOT_HAVE_VERSION_kEY = StatusCode.build("DEVICE_SECRET_NOT_HAVE_VERSION_kEY", "不存在设备密钥Key版本对应的设备密钥.");
}

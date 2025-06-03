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

import cn.devicelinks.component.web.api.StatusCode;

/**
 * The {@link StatusCode} Constants
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface StatusCodeConstants {
    /**
     * 通用
     */
    StatusCode SYSTEM_EXCEPTION = StatusCode.build("SYSTEM_EXCEPTION", "系统异常.");
    StatusCode UNKNOWN_ERROR = StatusCode.build("UNKNOWN_ERROR", "遇到未知异常");
    StatusCode REQUEST_EXPIRED = StatusCode.build("REQUEST_EXPIRED", "请求已过期");
    StatusCode SIGN_VERIFICATION_FAILED = StatusCode.build("SIGN_VERIFICATION_FAILED", "签名校验失败.");
    StatusCode PASSWORD_VERIFICATION_FAILED = StatusCode.build("PASSWORD_VERIFICATION_FAILED", "密码校验失败.");
    StatusCode AES_DECRYPTION_ERROR = StatusCode.build("AES_DECRYPTION_ERROR", "AES算法解密失败.");
    StatusCode BUSINESS_EXCEPTION = StatusCode.build("BUSINESS_EXCEPTION", "遇到业务异常：[%s].");

    /**
     * Api相关
     */
    StatusCode SEARCH_FIELD_NOT_IN_MODULE = StatusCode.build("SEARCH_FIELD_NOT_IN_MODULE", "检索字段：[%s]，不在模块[%s]中.");
    StatusCode SEARCH_FIELD_OPERATOR_NOT_SUPPORT = StatusCode.build("SEARCH_FIELD_OPERATOR_NOT_SUPPORT", "检索字段：[%s]，不支持运算符：[%s].");
    StatusCode SEARCH_FIELD_ENUM_VALUE_ILLEGAL = StatusCode.build("SEARCH_FIELD_ENUM_VALUE_ILLEGAL", "检索字段：[%s]，枚举值：[%s]，并未定义，请检查是否有效.");
    StatusCode SEARCH_FIELD_REQUIRED_NOT_PRESENT = StatusCode.build("SEARCH_FIELD_REQUIRED_NOT_PRESENT", "检索字段：[%s]，必须全部传递.");
    StatusCode API_KEY_NOT_FOUND = StatusCode.build("API_KEY_NOT_FOUND", "ApiKey不存在，禁止访问Api.");
    StatusCode API_KEY_SIGN_ERROR = StatusCode.build("API_KEY_SIGN_ERROR", "ApiKey签名验证失败，禁止访问Api.");
    StatusCode API_REQUEST_IS_EXPIRED = StatusCode.build("API_REQUEST_IS_EXPIRED", "Api请求已过期.");

    /**
     * 用户与认证
     */
    StatusCode INVALID_TOKEN = StatusCode.build("INVALID_TOKEN", "令牌无效.");
    StatusCode TOKEN_JWT_PARSING_FAILED = StatusCode.build("TOKEN_JWT_PARSING_FAILED", "令牌Jwt解析失败.");
    StatusCode TOKEN_EXPIRED = StatusCode.build("TOKEN_EXPIRED", "令牌已过期.");
    StatusCode TOKEN_INVALID = StatusCode.build("TOKEN_INVALID", "无效的令牌.");
    StatusCode USER_NOT_FOUND = StatusCode.build("USER_NOT_FOUND", "用户：%s，不存在.");
    StatusCode USER_ALREADY_EXISTS = StatusCode.build("USER_ALREADY_EXISTS", "用户已经存在，请检查账号、邮箱、手机号是否重复.");
    StatusCode USER_EMAIL_CANNOT_EMPTY = StatusCode.build("USER_EMAIL_CANNOT_EMPTY", "用户邮箱地址不允许为空.");
    StatusCode USER_ACTIVE_METHOD_CANNOT_EMPTY = StatusCode.build("USER_ACTIVE_METHOD_CANNOT_EMPTY", "用户激活方式不可以为空.");
    StatusCode UPDATE_SESSION_LAST_ACTIVE_TIME_FAILED = StatusCode.build("UPDATE_SESSION_LAST_ACTIVE_TIME_FAILED", "更新会话最后活动时间失败.");

    /**
     * 产品与功能
     */
    StatusCode PRODUCT_NOT_EXISTS = StatusCode.build("PRODUCT_NOT_EXISTS", "产品：%s，不存在.");
    StatusCode PRODUCT_ALREADY_EXISTS = StatusCode.build("PRODUCT_ALREADY_EXISTS", "产品：%s，已经存在，请检查名称、ProductKey是否重复.");
    StatusCode PRODUCT_NOT_IN_DEVELOPMENT_STATUS = StatusCode.build("PRODUCT_NOT_IN_DEVELOPMENT_STATUS", "产品：%s，不在开发状态，无法操作.");
    StatusCode PRODUCT_PUBLISHED = StatusCode.build("PRODUCT_PUBLISHED", "产品：%s，已发布，无法操作.");
    StatusCode PRODUCT_HAS_RELATED_DEVICES = StatusCode.build("PRODUCT_HAS_RELATED_DEVICES", "产品：%s，存在关联的设备，无法删除.");
    StatusCode FUNCTION_MODULE_NOT_FOUND = StatusCode.build("FUNCTION_MODULE_NOT_FOUND", "功能模块：%s，不存在.");
    StatusCode FUNCTION_MODULE_ALREADY_EXISTS = StatusCode.build("FUNCTION_MODULE_ALREADY_EXISTS", "功能模块已经存在，请检查名称、标识符是否重复.");
    StatusCode FUNCTION_MODULE_NOT_BELONG_PRODUCT = StatusCode.build("FUNCTION_MODULE_NOT_BELONG_PRODUCT", "功能模块：%s，不属于产品：%s.");
    StatusCode PRODUCT_NOT_SUPPORTED_DYNAMIC_REGISTER = StatusCode.build("PRODUCT_NOT_SUPPORTED_DYNAMIC_REGISTER", "产品：%s，不支持动态注册.");

    /**
     * 部门
     */
    StatusCode DEPARTMENT_NOT_FOUND = StatusCode.build("DEPARTMENT_NOT_FOUND", "部门：%s，不存在.");
    StatusCode DEPARTMENT_ALREADY_EXISTS = StatusCode.build("DEPARTMENT_ALREADY_EXISTS", "部门已经存在，请检查名称、标识符是否重复.");
    StatusCode DEPARTMENT_PARENT_NOT_EXISTS = StatusCode.build("DEPARTMENT_PARENT_NOT_EXISTS", "上级部门不存在.");

    /**
     * 设备
     */
    StatusCode UNKNOWN_DEVICE = StatusCode.build("UNKNOWN_DEVICE", "未知的设备.");
    StatusCode DEVICE_DISABLED = StatusCode.build("DEVICE_DISABLED", "设备已被禁用.");
    StatusCode DEVICE_ACTIVATE_FAIL = StatusCode.build("DEVICE_ACTIVATE_FAIL", "设备激活失败.");
    StatusCode DEVICE_NAME_NOT_MATCH = StatusCode.build("DEVICE_NAME_NOT_MATCH", "设备名称不匹配.");
    StatusCode DEVICE_ALREADY_EXISTS = StatusCode.build("DEVICE_ALREADY_EXISTS", "设备：%s，已经存在，请检查产品下设备唯一码是否重复.");
    StatusCode DEVICE_NOT_EXISTS = StatusCode.build("DEVICE_NOT_EXISTS", "设备：%s，不存在.");
    StatusCode INVALID_DEVICE_STATIC_TOKEN = StatusCode.build("INVALID_DEVICE_STATIC_TOKEN", "无效的设备请求令牌.");
    StatusCode DEVICE_STATIC_TOKEN_ALREADY_EXISTS = StatusCode.build("DEVICE_STATIC_TOKEN_ALREADY_EXISTS", "设备请求令牌已经存在.");
    StatusCode DEVICE_IS_ENABLE_NOT_ALLOWED_DELETE = StatusCode.build("DEVICE_IS_ENABLE_NOT_ALLOWED_DELETE", "设备：%s，目前处于启用状态，不允许删除.");
    StatusCode INVALID_DEVICE_MQTT_BASIC_AUTH = StatusCode.build("INVALID_DEVICE_MQTT_BASIC_AUTH", "无效的MQTT Basic Auth.");
    StatusCode DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS = StatusCode.build("DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS", "MQTT客户端ID已经存在.");
    StatusCode INVALID_DEVICE_X509_PEM = StatusCode.build("INVALID_DEVICE_X509_PEM", "无效的X509 Pem证书.");
    StatusCode DEVICE_CREDENTIALS_NO_VALID_EXISTS = StatusCode.build("DEVICE_CREDENTIALS_NOT_EXISTS", "设备：%s，不存在有效的凭证.");
    StatusCode DEVICE_DYNAMIC_REGISTRATION_METHOD_NOT_SUPPORT = StatusCode.build("DEVICE_DYNAMIC_REGISTRATION_METHOD_NOT_SUPPORT", "不支持的设备动态注册方式：[%s].");

    /**
     * 设备配置文件
     */
    StatusCode DEVICE_PROFILE_NOT_EXISTS = StatusCode.build("DEVICE_PROFILE_NOT_EXISTS", "设备配置文件：%s，不存在.");
    StatusCode DEVICE_PROFILE_ALREADY_EXISTS = StatusCode.build("DEVICE_PROFILE_ALREADY_EXISTS", "设备配置文件：%s，已经存在.");
    StatusCode DEVICE_PROFILE_PROVISION_ADDITION_INVALID = StatusCode.build("DEVICE_PROFILE_PROVISION_ADDITION_INVALID", "设备配置文件预配置附加参数验证失败.");
    StatusCode DEVICE_PROFILE_LOG_LEVELS_INVALID = StatusCode.build("DEVICE_PROFILE_LOG_LEVELS_INVALID", "设备配置日志等级附加信息验证失败，如果选择了产品请检查功能模块是否所属该产品，如果并未选择产品仅允许传递[default]功能模块的日志配置.");
    StatusCode DEVICE_PROFILE_DEFAULT_CANNOT_DELETE = StatusCode.build("DEVICE_PROFILE_DEFAULT_CANNOT_DELETE", "默认的设备配置文件不允许删除.");
    StatusCode DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID = StatusCode.build("DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID", "将设备配置文件批量设置给设备时，设置方式与参数验证失败，设置方式：%s.");
    StatusCode DEVICE_PROFILE_CREATION_NOT_ALLOWED = StatusCode.build("DEVICE_PROFILE_CREATION_NOT_ALLOWED", "设备配置文件：%s，预配置策略不允许动态创建设备.");
    StatusCode DEVICE_PROFILE_NOT_HAVE_PRODUCT_ID = StatusCode.build("DEVICE_PROFILE_NOT_HAVE_PRODUCT_ID", "设备配置文件：%s，不属于任何产品，无法动态创建设备.");

    /**
     * 设备密钥
     */
    StatusCode DEVICE_NOT_HAVE_SECRET = StatusCode.build("DEVICE_NOT_HAVE_SECRET", "设备不存在密钥.");
    StatusCode DEVICE_SECRET_DECRYPTION_ERROR = StatusCode.build("DEVICE_SECRET_DECRYPTION_ERROR", "设备密钥解密失败.");
    StatusCode DEVICE_SECRET_KEY_INVALID = StatusCode.build("DEVICE_SECRET_KEY_INVALID", "设备密钥Key无效.");
    StatusCode DEVICE_SECRET_NOT_HAVE_VERSION_kEY = StatusCode.build("DEVICE_SECRET_NOT_HAVE_VERSION_kEY", "不存在设备密钥Key版本对应的设备密钥.");

    /**
     * OTA
     */
    StatusCode OTA_FIRMWARE_NOT_EXISTS = StatusCode.build("OTA_FIRMWARE_NOT_EXISTS", "固件OTA：%s，不存在.");
    StatusCode OTA_SOFTWARE_NOT_EXISTS = StatusCode.build("OTA_SOFTWARE_NOT_EXISTS", "软件OTA：%s，不存在.");

    /**
     * 属性与遥测数据
     */
    StatusCode ATTRIBUTE_NOT_FOUND = StatusCode.build("ATTRIBUTE_NOT_FOUND", "属性：%s，不存在.");
    StatusCode ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE = StatusCode.build("ATTRIBUTE_NOT_BELONG_FUNCTION_MODULE", "属性：%s，不属于功能模块：%s.");
    StatusCode ATTRIBUTE_NOT_BELONG_PRODUCT = StatusCode.build("ATTRIBUTE_NOT_BELONG_PRODUCT", "属性：%s，不属于产品：%s.");
    StatusCode ATTRIBUTE_NOT_WRITEABLE = StatusCode.build("ATTRIBUTE_NOT_WRITEABLE", "属性：%s，不允许设置值.");
    StatusCode ATTRIBUTE_ID_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_ID_CANNOT_BLANK", "属性ID不允许为空.");
    StatusCode ATTRIBUTE_IDENTIFIER_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_IDENTIFIER_CANNOT_BLANK", "属性标识符不允许为空.");
    StatusCode ATTRIBUTE_DATA_TYPE_CANNOT_BLANK = StatusCode.build("ATTRIBUTE_DATA_TYPE_CANNOT_BLANK", "属性数据类型不允许为空.");
    StatusCode ATTRIBUTE_NOT_COMMON_NOT_ALLOW_SET_DESIRED = StatusCode.build("ATTRIBUTE_NOT_COMMON_NOT_ALLOW_SET_DESIRED", "属性：%s，不是公共属性，不支持设置期望值.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_VALUE_INVALID", "无效的期望属性值，请检查与数据类型是否匹配.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_NOT_FOUND", "设备期望属性：%s，不存在.");
    StatusCode DEVICE_ATTRIBUTE_NOT_FOUND = StatusCode.build("DEVICE_ATTRIBUTE_NOT_FOUND", "设备上报属性：%s，不存在.");
    StatusCode DEVICE_DESIRED_ATTRIBUTE_NOT_UNKNOWN = StatusCode.build("DEVICE_DESIRED_ATTRIBUTE_NOT_UNKNOWN", "设备期望属性：%s，不是未知属性，无法提取.");
    StatusCode DEVICE_ATTRIBUTE_NOT_UNKNOWN = StatusCode.build("DEVICE_ATTRIBUTE_NOT_UNKNOWN", "设备属性：%s，不是未知属性.");
    StatusCode DEVICE_ATTRIBUTE_NOT_KNOWN = StatusCode.build("DEVICE_ATTRIBUTE_NOT_KNOWN", "设备属性：%s，不是已知属性.");
    StatusCode DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART = StatusCode.build("DEVICE_ATTRIBUTE_DATA_TYPE_CANNOT_ADD_CHART", "设备属性：%s，的数据类型不支持添加到图表.");
    StatusCode TELEMETRY_NOT_EXISTS = StatusCode.build("TELEMETRY_NOT_EXISTS", "设备遥测数据：%s，不存在.");
    StatusCode TELEMETRY_DATA_TYPE_CANNOT_ADD_CHART = StatusCode.build("TELEMETRY_DATA_TYPE_CANNOT_ADD_CHART", "遥测数据：%s，的数据类型不支持添加到图表.");

    /**
     * 数据图表
     */
    StatusCode DATA_CHART_ALREADY_EXISTS = StatusCode.build("DATA_CHART_ALREADY_EXISTS", "同一目标位置下数据图表：%s，已经存在.");
    StatusCode DATA_CHART_NOT_EXISTS = StatusCode.build("DATA_CHART_NOT_EXISTS", "数据图表：%s，不存在.");

    /**
     * 全局参数
     */
    StatusCode SYS_SETTING_NOT_FOUND = StatusCode.build("SYS_SETTING_NOT_FOUND", "全局设置：%s，不存在.");
    StatusCode SYS_SETTING_VALUE_TYPE_MISMATCH = StatusCode.build("SYS_SETTING_VALUE_TYPE_MISMATCH", "全局设置[%s]，数据类型不匹配，请检查数据格式是否正确.");
    StatusCode SYS_SETTING_VALUE_TYPE_NOT_SUPPORT = StatusCode.build("SYS_SETTING_VALUE_TYPE_NOT_SUPPORT", "全局设置[%s]，数据类型：[%s]，不在支持的数据");
}

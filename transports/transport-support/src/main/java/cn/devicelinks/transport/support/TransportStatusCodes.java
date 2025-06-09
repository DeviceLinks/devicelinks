package cn.devicelinks.transport.support;

import cn.devicelinks.component.web.api.StatusCode;

/**
 * 传输服务{@link StatusCode}状态码定义列表
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface TransportStatusCodes {
    StatusCode SUCCESS_STATUS_CODE = StatusCode.build("0", "Success");
    StatusCode UNKNOWN_ERROR_STATUS_CODE = StatusCode.build("-1", "Unknown Error.");
    StatusCode SIGN_ALGORITHM_NOT_SUPPORT = StatusCode.build("SIGN_ALGORITHM_NOT_SUPPORT", "不支持的签名算法.");
    StatusCode REGISTRATION_METHOD_NOT_SUPPORT = StatusCode.build("REGISTRATION_METHOD_NOT_SUPPORT", "不支持的设备动态注册方式.");

    StatusCode REGISTRATION_METHOD_CANNOT_EMPTY = StatusCode.build("REGISTRATION_METHOD_CANNOT_EMPTY", "设备动态注册方式不可以为空.");
    StatusCode DEVICE_NAME_CANNOT_EMPTY = StatusCode.build("DEVICE_NAME_CANNOT_EMPTY", "设备名称不可以为空.");
    StatusCode DEVICE_ID_CANNOT_EMPTY = StatusCode.build("DEVICE_ID_CANNOT_EMPTY", "设备ID不可以为空.");
    StatusCode REQUEST_TIMESTAMP_CANNOT_EMPTY = StatusCode.build("REQUEST_TIMESTAMP_CANNOT_EMPTY", "请求时间戳不可以为空.");
    StatusCode SIGN_CANNOT_EMPTY = StatusCode.build("SIGN_CANNOT_EMPTY", "签名不可以为空.");
    StatusCode PROVISION_KEY_CANNOT_EMPTY = StatusCode.build("PROVISION_KEY_CANNOT_EMPTY", "预配置Key不可以为空.");
    StatusCode PRODUCT_KEY_CANNOT_EMPTY = StatusCode.build("PRODUCT_KEY_CANNOT_EMPTY", "产品Key不可以为空.");

    StatusCode REPORT_ATTRIBUTES_ERROR_STATUS_CODE = StatusCode.build("100001", "Report Attributes Error.");
}

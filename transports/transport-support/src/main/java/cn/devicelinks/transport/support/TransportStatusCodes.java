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

    StatusCode REPORT_ATTRIBUTES_ERROR_STATUS_CODE = StatusCode.build("100001", "Report Attributes Error.");
}

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
    StatusCode REPORT_ATTRIBUTES_ERROR_STATUS_CODE = StatusCode.build("100001", "Report Attributes Error.");
}

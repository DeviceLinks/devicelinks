package cn.devicelinks.framework.common.exception;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.api.StatusCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 接口异常
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    /**
     * 状态码
     */
    private final StatusCode statusCode;
    /**
     * 消息描述占位符变量列表
     */
    private Object[] messageVariables;

    public ApiException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public ApiException(StatusCode statusCode, Object... messageVariables) {
        super(statusCode.formatMessage(messageVariables));
        this.statusCode = statusCode;
        this.messageVariables = messageVariables;
    }
}

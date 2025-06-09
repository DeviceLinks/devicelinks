package cn.devicelinks.transport.support.model;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.component.web.api.StatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static cn.devicelinks.transport.support.TransportStatusCodes.SUCCESS_STATUS_CODE;

/**
 * 传输服务统一响应格式实体定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private int code;
    private String messageId;
    private String message;
    private T data;
    private long timestamp;

    private MessageResponse(int code, String messageId, String message, T data) {
        this.code = code;
        this.messageId = messageId;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> MessageResponse<T> success(String messageId, T data) {
        return success(SUCCESS_STATUS_CODE, messageId, data);
    }

    public static <T> MessageResponse<T> success(StatusCode statusCode, String messageId, T data, Object... messageVariables) {
        return new MessageResponse<>(Integer.parseInt(statusCode.getCode()), messageId, statusCode.formatMessage(messageVariables), data);
    }

    public static <T> MessageResponse<T> error(StatusCode statusCode, String messageId, Object... messageVariables) {
        return new MessageResponse<>(Integer.parseInt(statusCode.getCode()), messageId, statusCode.formatMessage(messageVariables), null);
    }
}

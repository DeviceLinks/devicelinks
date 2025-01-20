package cn.devicelinks.framework.common.api;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 接口统一响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    /**
     * 接口响应状态码
     */
    private String code;
    /**
     * 状态码对应的描述
     */
    private String message;
    /**
     * 接口响应的数据
     */
    private Object data;
    /**
     * 附加信息
     */
    private final Map<String, Object> additional = new HashMap<>();

    private ApiResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success() {
        return success(null);
    }

    public static <T> ApiResponse success(T data) {
        return success(StatusCode.SUCCESS, data);
    }

    public static <T> ApiResponse success(StatusCode statusCode, T data, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), data);
    }

    public static ApiResponse error(StatusCode statusCode) {
        return new ApiResponse(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public static ApiResponse error(StatusCode statusCode, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), null);
    }

    public ApiResponse putAdditional(String key, Object value) {
        this.additional.put(key, value);
        return this;
    }

    public ApiResponse putAdditional(Consumer<Map<String, Object>> consumer) {
        consumer.accept(this.additional);
        return this;
    }
}

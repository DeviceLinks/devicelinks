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

    /**
     * 封装success响应方法
     *
     * @param data 响应数据
     * @param <T>  响应数据的类型
     * @return {@link ApiResponse}
     */
    public static <T> ApiResponse success(T data) {
        return success(StatusCode.SUCCESS, data);
    }

    /**
     * 封装success响应方法，可传递响应消息{@link #message}占位符变量列表
     *
     * @param statusCode       状态码
     * @param data             响应数据
     * @param messageVariables 响应消息占位符变量列表
     * @param <T>              响应数据的类型
     * @return {@link ApiResponse}
     */
    public static <T> ApiResponse success(StatusCode statusCode, T data, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), data);
    }

    /**
     * 封装error响应方法
     *
     * @param statusCode 状态码
     * @return {@link ApiResponse}
     */
    public static ApiResponse error(StatusCode statusCode) {
        return new ApiResponse(statusCode.getCode(), statusCode.getMessage(), null);
    }

    /**
     * 封装error响应方法，可传递响应消息{@link #message}占位符变量列表
     *
     * @param statusCode       状态码
     * @param messageVariables 响应消息占位符变量列表
     * @return {@link ApiResponse}
     */
    public static ApiResponse error(StatusCode statusCode, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), null);
    }

    /**
     * 添加单个附加信息
     *
     * @param key   附加信息Key
     * @param value 附加信息Value
     * @return {@link ApiResponse} this object instance
     */
    public ApiResponse putAdditional(String key, Object value) {
        this.additional.put(key, value);
        return this;
    }

    /**
     * 批量添加附加信息
     *
     * @param consumer 附加信息集合消费者实例，将{@link #additional}作为参数传递
     * @return {@link ApiResponse} this object instance
     */
    public ApiResponse putAdditional(Consumer<Map<String, Object>> consumer) {
        consumer.accept(this.additional);
        return this;
    }
}

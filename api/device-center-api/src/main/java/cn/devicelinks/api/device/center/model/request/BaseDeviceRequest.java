package cn.devicelinks.api.device.center.model.request;

import cn.devicelinks.common.RequestSource;
import cn.devicelinks.common.utils.UUIDUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备请求基础实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDeviceRequest<T extends BaseDeviceRequest<T>> {
    /**
     * 请求来源
     */
    private RequestSource source;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 链路日志ID
     */
    private String traceId = UUIDUtils.generate();
    /**
     * 设备发起请求的时间戳
     */
    private long timestamp;

    @SuppressWarnings("unchecked")
    public T self() {
        return (T) this;
    }

    public T setSource(RequestSource source) {
        this.source = source;
        return self();
    }

    public T setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return self();
    }

    public T setMessageId(String messageId) {
        this.messageId = messageId;
        return self();
    }

    public T setTraceId(String traceId) {
        this.traceId = traceId;
        return self();
    }

    public T setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return self();
    }
}

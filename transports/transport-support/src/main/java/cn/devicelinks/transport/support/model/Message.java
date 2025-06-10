package cn.devicelinks.transport.support.model;

import cn.devicelinks.api.device.center.model.request.BaseDeviceRequest;
import cn.devicelinks.component.web.validator.TimestampValid;
import cn.devicelinks.transport.support.context.DeviceContext;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * 设备消息通用数据格式实体定义
 * <p>
 * 可继承该类自定义封装具体业务的消息实体，也可以直接通过泛型的方式直接定义使用
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Message {
    /**
     * 消息唯一ID，设备端生成，用于响应匹配和去重
     */
    @NotBlank(message = "消息ID不可以为空.")
    @Length(min = 20, max = 50, message = "消息ID长度请控制在20 ~ 50之间.")
    private String messageId;
    /**
     * 请求协议版本号，默认为"1.0"
     */
    @NotBlank(message = "协议版本号不可以为空.")
    private String version = "1.0";
    /**
     * 发送请求时的时间戳（单位：毫秒）
     */
    @TimestampValid(message = "请求时间戳值无效.")
    private long timestamp;

    /**
     * 转换为{@link BaseDeviceRequest}
     *
     * @param context 当前设备上下文 {@link DeviceContext}
     * @param request 具体请求对象实例，需要继承{@link BaseDeviceRequest}
     * @param <R>     具体请求对象类型
     * @return 具体请求对象实例
     */
    public <R extends BaseDeviceRequest<R>> R toRequest(DeviceContext context, R request) {
        return request
                .setDeviceId(context.getDeviceId())
                .setMessageId(this.messageId)
                .setSource(context.getRequestSource())
                .setTimestamp(this.timestamp);
    }
}

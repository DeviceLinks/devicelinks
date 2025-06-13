package cn.devicelinks.api.device.center.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 订阅设备属性更新请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SubscribeDeviceAttributeUpdateRequest extends BaseDeviceRequest<SubscribeDeviceAttributeUpdateRequest> {
    /**
     * 订阅时间
     */
    private LocalDateTime subscribeTime = LocalDateTime.now();
}

package cn.devicelinks.transport.support.context;

import cn.devicelinks.common.RequestSource;
import cn.devicelinks.common.context.Context;
import cn.devicelinks.entity.Device;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 设备上下文
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeviceContext implements Context {
    private Device device;
    private RequestSource requestSource;

    public static DeviceContext instance(Device device, RequestSource requestSource) {
        return new DeviceContext(device, requestSource);
    }

    public String getDeviceId() {
        return this.device.getId();
    }
}

package cn.devicelinks.component.openfeign;

import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import feign.jackson.JacksonEncoder;

/**
 * FeignClient Jackson编码器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class OpenFeignClientRequestEncoder extends JacksonEncoder {
    public OpenFeignClientRequestEncoder() {
        super(new DeviceLinksJsonMapper());
    }
}

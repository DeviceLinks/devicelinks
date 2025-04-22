package cn.devicelinks.api.support.feign;

import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import feign.jackson.JacksonEncoder;

/**
 * FeignClient Jackson编码器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class FeignClientRequestEncoder extends JacksonEncoder {
    public FeignClientRequestEncoder() {
        super(new DeviceLinksJsonMapper());
    }
}

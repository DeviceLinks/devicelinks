package cn.devicelinks.component.openfeign;

import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import feign.jackson.JacksonDecoder;

/**
 * FeignClient Jackson解码器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class OpenFeignClientResponseDecoder extends JacksonDecoder {

    public OpenFeignClientResponseDecoder() {
        super(new DeviceLinksJsonMapper());
    }
}

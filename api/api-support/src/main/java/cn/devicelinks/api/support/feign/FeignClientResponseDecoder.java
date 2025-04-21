package cn.devicelinks.api.support.feign;

import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import feign.jackson.JacksonDecoder;

/**
 * FeignClient Jackson解码器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class FeignClientResponseDecoder extends JacksonDecoder {

    public FeignClientResponseDecoder() {
        super(new DeviceLinksJsonMapper());
    }
}

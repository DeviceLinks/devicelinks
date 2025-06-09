package cn.devicelinks.common;

import cn.devicelinks.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 请求源
 * <p>
 * 一般用于内部服务Feign之间的方法调用时使用
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum RequestSource {
    Console("控制台"),
    TransportHttp("HTTP协议传输服务"),
    TransportCoap("CoAP协议传输服务"),
    TransportMqtt("MQTT协议传输服务"),
    TransportJt808("JT808协议传输服务"),
    ;
    private final String description;

    RequestSource(String description) {
        this.description = description;
    }
}

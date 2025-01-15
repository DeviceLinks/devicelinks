package cn.devicelinks.transport.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MQTT传输服务启动类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@SpringBootApplication
public class TransportMqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportMqttApplication.class, args);
    }
}

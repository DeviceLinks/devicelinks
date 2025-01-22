/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import cn.devicelinks.framework.common.utils.SSLUtils;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import lombok.SneakyThrows;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import javax.net.ssl.SSLSocketFactory;
import java.nio.charset.StandardCharsets;

/**
 * @author 恒宇少年
 * @since 1.0
 */
public class MqttTest {
    @SneakyThrows
    public static void main(String[] args) {
        /*twoWayTls();
        oneWayTls();*/
        subscribe();
        //webSocketTls();
    }

    /**
     * 订阅消息
     */
    static void subscribe() {
        String topic = "/test/#";
        String broker = "ssl://127.0.0.1:8883";
        String clientId = UUIDUtils.generateNoDelimiter();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(true);
            connOpts.setUserName("user");
            connOpts.setPassword("123456".getBytes(StandardCharsets.UTF_8));
            connOpts.setConnectionTimeout(60);
            connOpts.setKeepAliveInterval(60);
            System.out.println("Connecting to broker: " + broker);

            SSLSocketFactory sslSocketFactory = SSLUtils.getSocketFactory(
                    "/Users/yuqiyu/docker/mosquitto/ssl/ca.crt",
                    "/Users/yuqiyu/docker/mosquitto/ssl/client/user.crt",
                    "/Users/yuqiyu/docker/mosquitto/ssl/client/user_new.key",
                    "");
            connOpts.setSocketFactory(sslSocketFactory);

            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(topic, 2, (topic1, message) -> {
                System.out.println("messageId: " + message.getId());
                System.out.println("Retained: " + message.isRetained());
                System.out.println("topic: " + topic1);
                System.out.println("Qos: " + message.getQos());
                System.out.println("message content: " + new String(message.getPayload()));
            });

        } catch (Exception me) {
            if (me instanceof MqttException mqttException) {
                System.out.println("reason " + mqttException.getReasonCode());
                System.out.println("msg " + mqttException.getMessage());
                System.out.println("loc " + mqttException.getLocalizedMessage());
                System.out.println("cause " + mqttException.getCause());
                System.out.println("excep " + mqttException);
            }
            me.printStackTrace();
        }
    }

    /**
     * 双向TLS
     */
    static void mqttTwoWayTls() {
        String topic = "/test/two-way-tls";
        String content = "Message from MqttPublishSample";
        int qos = 2;
        String broker = "ssl://127.0.0.1:8883";
        String clientId = UUIDUtils.generateNoDelimiter();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(true);
            connOpts.setUserName("user");
            connOpts.setPassword("123456".getBytes(StandardCharsets.UTF_8));
            connOpts.setConnectionTimeout(60);
            connOpts.setKeepAliveInterval(60);
            System.out.println("Connecting to broker: " + broker);

            SSLSocketFactory sslSocketFactory = SSLUtils.getSocketFactory("/Users/yuqiyu/docker/mosquitto/ssl/ca.crt",
                    "/Users/yuqiyu/docker/mosquitto/ssl/client/user.crt", "/Users/yuqiyu/docker/mosquitto/ssl/client/user_new.key",
                    "");
            connOpts.setSocketFactory(sslSocketFactory);

            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // Published Message
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (Exception me) {
            if (me instanceof MqttException mqttException) {
                System.out.println("reason " + mqttException.getReasonCode());
                System.out.println("msg " + mqttException.getMessage());
                System.out.println("loc " + mqttException.getLocalizedMessage());
                System.out.println("cause " + mqttException.getCause());
                System.out.println("excep " + mqttException);
            }
            me.printStackTrace();
        }
    }

    /**
     * 单向TLS
     */
    static void mqttOneWayTls() {
        String topic = "/test/one-way-tls";
        String content = "Message from MqttPublishSample";
        int qos = 2;
        String broker = "ssl://127.0.0.1:8884";
        String clientId = UUIDUtils.generateNoDelimiter();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(true);
            connOpts.setUserName("user");
            connOpts.setPassword("123456".getBytes(StandardCharsets.UTF_8));
            System.out.println("Connecting to broker: " + broker);

            SSLSocketFactory sslSocketFactory = SSLUtils.getSingleSocketFactory("/Users/yuqiyu/docker/mosquitto/ssl/ca.crt");
            connOpts.setSocketFactory(sslSocketFactory);

            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // Published Message
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (Exception exception) {
            if (exception instanceof MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
            }
            exception.printStackTrace();
        }
    }

    /**
     * WebSocket TLS
     */
    static void webSocketTls() {
        String topic = "/mqtt";
        String content = "Message from WebSocket PublishSample";
        int qos = 2;
        String broker = "wss://127.0.0.1:9099";
        String clientId = UUIDUtils.generateNoDelimiter();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(true);
            connOpts.setUserName("user");
            connOpts.setPassword("123456".getBytes(StandardCharsets.UTF_8));
            System.out.println("Connecting to broker: " + broker);

            SSLSocketFactory sslSocketFactory = SSLUtils.getSingleSocketFactory("/Users/yuqiyu/docker/mosquitto/ssl/ca.crt");
            connOpts.setSocketFactory(sslSocketFactory);

            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // Published Message
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (Exception exception) {
            if (exception instanceof MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
            }
            exception.printStackTrace();
        }
    }
}

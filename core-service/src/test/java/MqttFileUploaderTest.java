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

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MQTT文件上传客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class MqttFileUploaderTest {
    /**
     * File segment size, default is 10KB, muse be less than `mqtt.max_packet_size` (default is 1024KB)
     */
    private final static int SEGMENT_SIZE = 1024 * 10;
    private final static String CLIENT_ID = "file-uploader-client";
    private final static int QOS = 1;
    private MqttClient client;
    private final String username;
    private final String password;


    public MqttFileUploaderTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void initClient(String brokerUrl, String clientId) {
        try {
            client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectionOptions options = new MqttConnectionOptions();
            options.setUserName(username);
            options.setPassword(password.getBytes(StandardCharsets.UTF_8));
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            System.out.println("Connecting to broker: " + brokerUrl);

            client.connect(options);
            if (!client.isConnected()) {
                log("Fail to connected " + brokerUrl + " with client ID " + client.getClientId());
                System.exit(1);
            }
            log("Connected " + brokerUrl + " with client ID " + client.getClientId());

        } catch (MqttException e) {
            e.printStackTrace();
            log(e.toString());
            System.exit(1);
        }

    }

    public void transferFile(String filePath, String host) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist : " + filePath);
            System.exit(1);
        }

        initClient(host, CLIENT_ID);

        try {
            // Use file checksum as file_id
            String fileChecksum = calculateChecksum(filePath);

            String fileId = fileChecksum;
            long fileSize = file.length();

            // The client device publishes init command topic.
            // The payload of the message contains the file metadata, including the file name, size, and checksum.
            pubInitCommand(fileId, file.getName(), fileSize, fileChecksum);

            //  The client sends consecutive segment commands
            //  Each segment command carries a chunk of the file data at the specified offset.
            pubSegmentCommands(file, fileId);

            // The client sends finish command
            pubFinishCommand(fileId, fileSize);

        } catch (Exception e) {
            System.out.println("msg " + e.getMessage());
            System.out.println("log " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
                client.close();
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void pubFinishCommand(String fileId, long fileSize) throws MqttException {
        String finishTopic = "$file/" + fileId + "/finish/" + fileSize;
        publishMessage(finishTopic, QOS, "".getBytes());
        log("File transfer finished.");
    }


    public void pubInitCommand(String fileId, String fileName, long fileSize, String fileChecksum) throws MqttException {
        String initTopic = "$file/" + fileId + "/init/" + fileSize;

        String initMsg = initMsg(fileName, fileSize, fileChecksum);
        System.out.println("Init Msg : " + initMsg);
        publishMessage(initTopic, QOS, initMsg.getBytes());
        log("File transfer session initialized.");
    }

    public void pubSegmentCommands(File file, String fileId) throws IOException, MqttException {
        log("Send file segment start =>");
        FileChannel fileChannel = FileChannel.open(file.toPath());
        // Read the file and publish segments
        int offset = 0;
        while (true) {
            // Read a segment from the file
            int capacity = (int) file.length() - offset;
            capacity = Math.min(SEGMENT_SIZE, capacity);
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            fileChannel.read(buffer);
            buffer.flip();

            // Publish the segment
            String segmentTopic = "$file/" + fileId + "/segment" + "/" + offset + "/" + (offset + buffer.array().length);

            offset += buffer.limit();
            publishMessage(segmentTopic, QOS, buffer.array());

            // Check if the end of the file has been reached
            if (buffer.limit() < SEGMENT_SIZE) {
                break;
            }
        }
        fileChannel.close();
        log("Send file segment end ");
    }


    private static String initMsg(String fileName, long fileSize, String fileChecksum) {
        return "{\"name\":\"" + fileName + "\",\"size\":" + fileSize + ",\"checksum\":\"" + fileChecksum + "\"}";
    }


    public static String calculateChecksum(String filePath) throws IOException, NoSuchAlgorithmException {
        // Get the MessageDigest instance for the checksum algorithm
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");

        // Get the file data
        File file = new File(filePath);

        byte[] fileData = Files.readAllBytes(Paths.get(file.getPath()));

        // Update the hash with the file data
        md5.update(fileData);

        // Convert the hash to a string
        return new BigInteger(1, md5.digest()).toString(16);
    }


    public void publishMessage(String topicName, int qos, byte[] payload) throws MqttException {
        if (!client.isConnected()) {
            System.out.println("client is unconnectd");
            System.exit(1);
        }

        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);
        client.publish(topicName, message);
        log("Published to topic \"" + topicName + "\" qos " + qos + " size:" + payload.length);
    }

    private static void log(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        String userName = "admin";
        String password = "123456";
        // broker.devicelinks.cn
        String broker = "tcp://127.0.0.1:1883";
        String filePath = "/Users/yuqiyu/Downloads/Cybertruck.jpeg";

        System.out.println("Args => broker:" + broker + " filePath:" + filePath + " userName=" + userName + " password:" + password);

        new MqttFileUploaderTest(userName, password).transferFile(filePath, broker);
    }

}

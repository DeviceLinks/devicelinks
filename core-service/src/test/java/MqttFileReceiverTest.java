import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * MQTT文件接收客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class MqttFileReceiverTest {
    private static final String CLIENT_ID = "file-receiver-client";
    private static final int QOS = 1;
    private final String username;
    private final String password;
    private final Map<String, FileOutputStream> fileStreams = new HashMap<>();

    public MqttFileReceiverTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void initClient(String brokerUrl) {
        try {
            MqttClient client = new MqttClient(brokerUrl, CLIENT_ID, new MemoryPersistence());
            MqttConnectionOptions options = new MqttConnectionOptions();
            options.setUserName(username);
            options.setPassword(password.getBytes(StandardCharsets.UTF_8));
            client.connect(options);
            if (!client.isConnected()) {
                log("Failed to connect to " + brokerUrl + " with client ID " + client.getClientId());
                System.exit(1);
            }
            log("Connected to " + brokerUrl + " with client ID " + client.getClientId());

            // Subscribe to the init, segment, and finish topics
            client.subscribe("$file/+/init/+", QOS, this::handleInitMessage);
            client.subscribe("$file/+/finish/+", QOS, this::handleFinishMessage);
            client.subscribe("$file/+/segment/+/+", QOS, this::handleSegmentMessage);

        } catch (Exception e) {
            e.printStackTrace();
            log(e.toString());
            System.exit(1);
        }
    }

    private void handleInitMessage(String topic, MqttMessage message) {
        String[] topicParts = topic.split("/");
        String fileId = topicParts[1];
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        log("Received init message for file ID " + fileId + ": " + payload);

        // Extract file name from the payload
        String fileName = extractFileName(payload);
        try {
            FileOutputStream fos = new FileOutputStream("upload/" + fileName);
            fileStreams.put(fileId, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSegmentMessage(String topic, MqttMessage message) {
        String[] topicParts = topic.split("/");
        String fileId = topicParts[1];
        int offset = Integer.parseInt(topicParts[3]);
        int end = Integer.parseInt(topicParts[4]);
        log("Received segment for file ID " + fileId + " at offset " + offset + " end at " + end);

        try {
            FileOutputStream fos = fileStreams.get(fileId);
            if (fos != null) {
                fos.write(message.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleFinishMessage(String topic, MqttMessage message) {
        String[] topicParts = topic.split("/");
        String fileId = topicParts[1];
        log("Received finish message for file ID " + fileId);

        try {
            FileOutputStream fos = fileStreams.get(fileId);
            if (fos != null) {
                fos.close();
                fileStreams.remove(fileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractFileName(String payload) {
        // Extract the file name from the JSON payload
        // This is a simple example, you may need to use a JSON library to parse the payload
        int nameIndex = payload.indexOf("\"name\":\"") + 8;
        int endIndex = payload.indexOf("\"", nameIndex);
        return payload.substring(nameIndex, endIndex);
    }

    private static void log(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        String userName = "admin";
        String password = "123456";
        String broker = "tcp://127.0.0.1:1883";

        new MqttFileReceiverTest(userName, password).initClient(broker);
    }
}
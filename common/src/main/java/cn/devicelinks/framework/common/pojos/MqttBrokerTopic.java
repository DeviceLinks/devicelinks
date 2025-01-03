package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.TopicCategory;
import cn.devicelinks.framework.common.TopicDevicePermission;
import cn.devicelinks.framework.common.TopicSubCategory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MQTT Broker Topic
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class MqttBrokerTopic implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String name;
    private String topic;
    private TopicCategory category;
    private TopicSubCategory subCategory;
    private TopicDevicePermission devicePermission;
    private boolean enabled;
    private String mark;
    private String createBy;
    private LocalDateTime createTime;
}

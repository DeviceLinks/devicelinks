package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.MqttBrokerTopic;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link MqttBrokerTopic} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TMqttBrokerTopic extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TMqttBrokerTopic MQTT_BROKER_TOPIC = new TMqttBrokerTopic("mqtt_broker_topic");

    private TMqttBrokerTopic(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column TOPIC = Column.withName("topic").build();
    public final Column CATEGORY = Column.withName("category").typeMapper(ColumnValueMappers.TOPIC_CATEGORY).build();
    public final Column SUB_CATEGORY = Column.withName("sub_category").typeMapper(ColumnValueMappers.TOPIC_SUB_CATEGORY).build();
    public final Column DEVICE_PERMISSION = Column.withName("device_permission").typeMapper(ColumnValueMappers.TOPIC_DEVICE_PERMISSION).build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().build();
    public final Column MARK = Column.withName("mark").build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, TOPIC, CATEGORY, SUB_CATEGORY, DEVICE_PERMISSION, ENABLED, MARK, CREATE_BY, CREATE_TIME);
    }
}

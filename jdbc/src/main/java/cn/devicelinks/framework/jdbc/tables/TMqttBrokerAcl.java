package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.MqttBrokerAcl;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link MqttBrokerAcl} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TMqttBrokerAcl extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TMqttBrokerAcl MQTT_BROKER_ACL = new TMqttBrokerAcl("mqtt_broker_acl");

    private TMqttBrokerAcl(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().intValue().build();
    public final Column USER_ID = Column.withName("user_id").intValue().build();
    public final Column TOPIC = Column.withName("topic").build();
    public final Column RW = Column.withName("rw").intValue().build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, USER_ID, TOPIC, RW, CREATE_TIME);
    }
}

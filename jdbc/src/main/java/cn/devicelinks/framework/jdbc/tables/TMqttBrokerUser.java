package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.MqttBrokerUser;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link MqttBrokerUser} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TMqttBrokerUser extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TMqttBrokerUser MQTT_BROKER_USER = new TMqttBrokerUser("mqtt_broker_user");

    private TMqttBrokerUser(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().intValue().build();
    public final Column USERNAME = Column.withName("username").build();
    public final Column PASSWORD_HASH = Column.withName("password_hash").build();
    public final Column IS_ADMIN = Column.withName("is_admin").booleanValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, USERNAME, PASSWORD_HASH, IS_ADMIN);
    }
}

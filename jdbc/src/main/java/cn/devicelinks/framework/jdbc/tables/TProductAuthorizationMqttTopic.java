package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.pojos.ProductAuthorizationMqttTopic;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;

import java.io.Serial;
import java.util.List;

/**
 * The {@link ProductAuthorizationMqttTopic} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TProductAuthorizationMqttTopic extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TProductAuthorizationMqttTopic PRODUCT_AUTHORIZATION_MQTT_TOPIC = new TProductAuthorizationMqttTopic("product_authorization_mqtt_topic");

    private TProductAuthorizationMqttTopic(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column PRODUCT_ID = Column.withName("product_id").build();
    public final Column TOPIC_ID = Column.withName("topic_id").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, PRODUCT_ID, TOPIC_ID, CREATE_TIME);
    }
}

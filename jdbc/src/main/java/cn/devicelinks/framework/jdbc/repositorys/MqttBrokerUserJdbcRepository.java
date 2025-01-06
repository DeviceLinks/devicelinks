package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.MqttBrokerUser;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TMqttBrokerUser;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * MQTT Broker User 数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class MqttBrokerUserJdbcRepository extends JdbcRepository<MqttBrokerUser, String> implements MqttBrokerUserRepository {
    public MqttBrokerUserJdbcRepository(JdbcOperations jdbcOperations) {
        super(TMqttBrokerUser.MQTT_BROKER_USER, jdbcOperations);
    }
}

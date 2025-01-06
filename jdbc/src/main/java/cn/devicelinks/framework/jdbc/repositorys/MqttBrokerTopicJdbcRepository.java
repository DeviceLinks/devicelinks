package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.MqttBrokerTopic;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TMqttBrokerTopic;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * MQTT Broker Topic 数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class MqttBrokerTopicJdbcRepository extends JdbcRepository<MqttBrokerTopic, String> implements MqttBrokerTopicRepository {
    public MqttBrokerTopicJdbcRepository(JdbcOperations jdbcOperations) {
        super(TMqttBrokerTopic.MQTT_BROKER_TOPIC, jdbcOperations);
    }
}

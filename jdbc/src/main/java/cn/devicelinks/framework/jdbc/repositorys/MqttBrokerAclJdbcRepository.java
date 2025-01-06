package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.MqttBrokerAcl;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TMqttBrokerAcl;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * MQTT Broker Acl 数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class MqttBrokerAclJdbcRepository extends JdbcRepository<MqttBrokerAcl, String> implements MqttBrokerAclRepository {
    public MqttBrokerAclJdbcRepository(JdbcOperations jdbcOperations) {
        super(TMqttBrokerAcl.MQTT_BROKER_ACL, jdbcOperations);
    }
}

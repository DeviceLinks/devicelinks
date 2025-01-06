package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.ProductAuthorizationMqttTopic;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TProductAuthorizationMqttTopic;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 产品授权MQTT主题数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ProductAuthorizationMqttTopicJdbcRepository extends JdbcRepository<ProductAuthorizationMqttTopic, String> implements ProductAuthorizationMqttTopicRepository {
    public ProductAuthorizationMqttTopicJdbcRepository(JdbcOperations jdbcOperations) {
        super(TProductAuthorizationMqttTopic.PRODUCT_AUTHORIZATION_MQTT_TOPIC, jdbcOperations);
    }
}

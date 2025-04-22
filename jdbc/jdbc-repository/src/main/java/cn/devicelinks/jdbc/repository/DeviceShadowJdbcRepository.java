package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceShadow;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.tables.TDeviceShadow;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * The {@link DeviceShadow} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceShadowJdbcRepository extends JdbcRepository<DeviceShadow, String> implements DeviceShadowRepository {
    public DeviceShadowJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceShadow.DEVICE_SHADOW, jdbcOperations);
    }
}

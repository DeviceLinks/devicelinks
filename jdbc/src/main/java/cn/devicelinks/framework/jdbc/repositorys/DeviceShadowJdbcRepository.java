package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DeviceShadow;
import cn.devicelinks.framework.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceShadow;
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

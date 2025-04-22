package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceSecret;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.jdbc.tables.TDeviceSecret.DEVICE_SECRET;

/**
 * 设备密钥数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceSecretJdbcRepository extends JdbcRepository<DeviceSecret, String> implements DeviceSecretRepository {
    public DeviceSecretJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_SECRET, jdbcOperations);
    }
}

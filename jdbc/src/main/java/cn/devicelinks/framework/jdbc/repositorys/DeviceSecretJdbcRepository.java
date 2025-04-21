package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceSecret.DEVICE_SECRET;

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

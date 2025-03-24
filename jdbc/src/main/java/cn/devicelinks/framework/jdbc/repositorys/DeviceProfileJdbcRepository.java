package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceProfile.DEVICE_PROFILE;

/**
 * The {@link DeviceProfile} Jdbc Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceProfileJdbcRepository extends JdbcRepository<DeviceProfile, String> implements DeviceProfileRepository {
    public DeviceProfileJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_PROFILE, jdbcOperations);
    }
}

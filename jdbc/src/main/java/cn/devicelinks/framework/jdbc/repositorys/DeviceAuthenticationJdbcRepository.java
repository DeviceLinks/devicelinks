package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceAuthentication;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备鉴权数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAuthenticationJdbcRepository extends JdbcRepository<DeviceAuthentication, String> implements DeviceAuthenticationRepository {
    public DeviceAuthenticationJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceAuthentication.DEVICE_AUTHENTICATION, jdbcOperations);
    }
}

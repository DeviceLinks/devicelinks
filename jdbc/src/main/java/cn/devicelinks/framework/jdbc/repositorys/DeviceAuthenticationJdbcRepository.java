package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAuthentication.DEVICE_AUTHENTICATION;

/**
 * The {@link DeviceAuthentication} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAuthenticationJdbcRepository extends JdbcRepository<DeviceAuthentication, String> implements DeviceAuthenticationRepository {
	public DeviceAuthenticationJdbcRepository(JdbcOperations jdbcOperations) {
		super(DEVICE_AUTHENTICATION, jdbcOperations);
	}
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceOta;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceOta.DEVICE_OTA;

/**
 * The {@link DeviceOta} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceOtaJdbcRepository extends JdbcRepository<DeviceOta, String> implements DeviceOtaRepository {
	public DeviceOtaJdbcRepository(JdbcOperations jdbcOperations) {
		super(DEVICE_OTA, jdbcOperations);
	}
}

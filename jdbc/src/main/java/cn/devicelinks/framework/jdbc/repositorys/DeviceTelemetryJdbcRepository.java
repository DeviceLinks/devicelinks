package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceTelemetry.DEVICE_TELEMETRY;

/**
 * The {@link DeviceTelemetry} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceTelemetryJdbcRepository extends JdbcRepository<DeviceTelemetry, String> implements DeviceTelemetryRepository {
	public DeviceTelemetryJdbcRepository(JdbcOperations jdbcOperations) {
		super(DEVICE_TELEMETRY, jdbcOperations);
	}
}

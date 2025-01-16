package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;

/**
 * The {@link Device} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceJdbcRepository extends JdbcRepository<Device, String> implements DeviceRepository {
	public DeviceJdbcRepository(JdbcOperations jdbcOperations) {
		super(DEVICE, jdbcOperations);
	}
}

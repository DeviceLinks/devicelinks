package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Ota;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TOta.OTA;

/**
 * The {@link Ota} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaJdbcRepository extends JdbcRepository<Ota, String> implements OtaRepository {
	public OtaJdbcRepository(JdbcOperations jdbcOperations) {
		super(OTA, jdbcOperations);
	}
}

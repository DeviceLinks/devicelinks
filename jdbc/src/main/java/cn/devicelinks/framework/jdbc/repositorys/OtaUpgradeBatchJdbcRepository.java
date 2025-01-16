package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeBatch;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TOtaUpgradeBatch.OTA_UPGRADE_BATCH;

/**
 * The {@link OtaUpgradeBatch} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeBatchJdbcRepository extends JdbcRepository<OtaUpgradeBatch, String> implements OtaUpgradeBatchRepository {
	public OtaUpgradeBatchJdbcRepository(JdbcOperations jdbcOperations) {
		super(OTA_UPGRADE_BATCH, jdbcOperations);
	}
}

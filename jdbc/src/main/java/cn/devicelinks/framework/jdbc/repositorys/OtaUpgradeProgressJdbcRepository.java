package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeProgress;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TOtaUpgradeProgress.OTA_UPGRADE_PROGRESS;

/**
 * The {@link OtaUpgradeProgress} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeProgressJdbcRepository extends JdbcRepository<OtaUpgradeProgress, String> implements OtaUpgradeProgressRepository {
	public OtaUpgradeProgressJdbcRepository(JdbcOperations jdbcOperations) {
		super(OTA_UPGRADE_PROGRESS, jdbcOperations);
	}
}

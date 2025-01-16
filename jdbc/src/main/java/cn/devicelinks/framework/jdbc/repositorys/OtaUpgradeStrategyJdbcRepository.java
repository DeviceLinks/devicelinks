package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeStrategy;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TOtaUpgradeStrategy.OTA_UPGRADE_STRATEGY;

/**
 * The {@link OtaUpgradeStrategy} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeStrategyJdbcRepository extends JdbcRepository<OtaUpgradeStrategy, String> implements OtaUpgradeStrategyRepository {
	public OtaUpgradeStrategyJdbcRepository(JdbcOperations jdbcOperations) {
		super(OTA_UPGRADE_STRATEGY, jdbcOperations);
	}
}

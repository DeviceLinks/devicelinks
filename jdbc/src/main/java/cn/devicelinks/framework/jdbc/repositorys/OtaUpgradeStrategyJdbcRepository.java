package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeStrategy;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TOtaUpgradeStrategy;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 固件升级策略数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeStrategyJdbcRepository extends JdbcRepository<OtaUpgradeStrategy, String> implements OtaUpgradeStrategyRepository {
    public OtaUpgradeStrategyJdbcRepository(JdbcOperations jdbcOperations) {
        super(TOtaUpgradeStrategy.OTA_UPGRADE_STRATEGY, jdbcOperations);
    }
}

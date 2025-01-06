package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeProgress;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TOtaUpgradeProgress;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 固件升级进度数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeProgressJdbcRepository extends JdbcRepository<OtaUpgradeProgress, String> implements OtaUpgradeProgressRepository {
    public OtaUpgradeProgressJdbcRepository(JdbcOperations jdbcOperations) {
        super(TOtaUpgradeProgress.OTA_UPGRADE_PROGRESS, jdbcOperations);
    }
}

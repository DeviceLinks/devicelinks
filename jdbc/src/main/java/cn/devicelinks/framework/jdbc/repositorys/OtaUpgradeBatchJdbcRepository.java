package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaUpgradeBatch;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TOtaUpgradeBatch;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 固件升级批次数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaUpgradeBatchJdbcRepository extends JdbcRepository<OtaUpgradeBatch, String> implements OtaUpgradeBatchRepository {
    public OtaUpgradeBatchJdbcRepository(JdbcOperations jdbcOperations) {
        super(TOtaUpgradeBatch.OTA_UPGRADE_BATCH, jdbcOperations);
    }
}

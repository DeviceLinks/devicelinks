package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysLog.SYS_LOG;

/**
 * The {@link SysLog} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysLogJdbcRepository extends JdbcRepository<SysLog, String> implements SysLogRepository {
	public SysLogJdbcRepository(JdbcOperations jdbcOperations) {
		super(SYS_LOG, jdbcOperations);
	}
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysUserOperateLog;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysUserOperateLog.SYS_USER_OPERATE_LOG;

/**
 * 用户操作日志数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysUserOperateLogJdbcRepository extends JdbcRepository<SysUserOperateLog, String> implements SysUserOperateLogRepository {
    public SysUserOperateLogJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_USER_OPERATE_LOG, jdbcOperations);
    }
}

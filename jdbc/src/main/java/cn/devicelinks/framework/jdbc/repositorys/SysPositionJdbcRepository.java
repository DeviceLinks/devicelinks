package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysPosition;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysPosition.SYS_POSITION;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysPositionJdbcRepository extends JdbcRepository<SysPosition, String> implements SysPositionRepository {
    public SysPositionJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_POSITION, jdbcOperations);
    }
}

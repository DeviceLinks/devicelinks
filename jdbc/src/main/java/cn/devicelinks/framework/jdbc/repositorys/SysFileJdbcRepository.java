package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysFile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TSysFile;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 文件数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysFileJdbcRepository extends JdbcRepository<SysFile, String> implements SysFileRepository {
    public SysFileJdbcRepository(JdbcOperations jdbcOperations) {
        super(TSysFile.SYS_FILE, jdbcOperations);
    }
}

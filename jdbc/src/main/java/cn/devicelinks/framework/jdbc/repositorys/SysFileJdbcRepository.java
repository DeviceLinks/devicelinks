package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysFile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysFile.SYS_FILE;

/**
 * The {@link SysFile} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysFileJdbcRepository extends JdbcRepository<SysFile, String> implements SysFileRepository {
	public SysFileJdbcRepository(JdbcOperations jdbcOperations) {
		super(SYS_FILE, jdbcOperations);
	}
}

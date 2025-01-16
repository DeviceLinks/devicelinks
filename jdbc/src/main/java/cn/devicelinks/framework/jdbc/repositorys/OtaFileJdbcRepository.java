package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaFile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TOtaFile.OTA_FILE;

/**
 * The {@link OtaFile} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaFileJdbcRepository extends JdbcRepository<OtaFile, String> implements OtaFileRepository {
	public OtaFileJdbcRepository(JdbcOperations jdbcOperations) {
		super(OTA_FILE, jdbcOperations);
	}
}

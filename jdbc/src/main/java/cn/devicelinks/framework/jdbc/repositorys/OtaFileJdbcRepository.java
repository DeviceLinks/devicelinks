package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.OtaFile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TOtaFile;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 固件文件数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaFileJdbcRepository extends JdbcRepository<OtaFile, String> implements OtaFileRepository {
    public OtaFileJdbcRepository(JdbcOperations jdbcOperations) {
        super(TOtaFile.OTA_FILE, jdbcOperations);
    }
}

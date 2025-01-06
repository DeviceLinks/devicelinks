package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Ota;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TOta;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * OTA数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class OtaJdbcRepository extends JdbcRepository<Ota, String> implements OtaRepository {
    public OtaJdbcRepository(JdbcOperations jdbcOperations) {
        super(TOta.OTA, jdbcOperations);
    }
}

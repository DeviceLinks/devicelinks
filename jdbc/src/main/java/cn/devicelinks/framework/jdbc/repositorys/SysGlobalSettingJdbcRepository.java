package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysGlobalSetting;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TSysGlobalSetting;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 全局参数数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysGlobalSettingJdbcRepository extends JdbcRepository<SysGlobalSetting, String> implements SysGlobalSettingRepository {
    public SysGlobalSettingJdbcRepository(JdbcOperations jdbcOperations) {
        super(TSysGlobalSetting.SYS_GLOBAL_SETTING, jdbcOperations);
    }
}

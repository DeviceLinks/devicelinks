package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysGlobalSetting;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysGlobalSetting.SYS_GLOBAL_SETTING;

/**
 * The {@link SysGlobalSetting} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysGlobalSettingJdbcRepository extends JdbcRepository<SysGlobalSetting, String> implements SysGlobalSettingRepository {
	public SysGlobalSettingJdbcRepository(JdbcOperations jdbcOperations) {
		super(SYS_GLOBAL_SETTING, jdbcOperations);
	}
}

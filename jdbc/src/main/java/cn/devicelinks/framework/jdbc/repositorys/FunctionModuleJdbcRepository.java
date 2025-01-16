package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TFunctionModule.FUNCTION_MODULE;

/**
 * The {@link FunctionModule} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class FunctionModuleJdbcRepository extends JdbcRepository<FunctionModule, String> implements FunctionModuleRepository {
	public FunctionModuleJdbcRepository(JdbcOperations jdbcOperations) {
		super(FUNCTION_MODULE, jdbcOperations);
	}
}

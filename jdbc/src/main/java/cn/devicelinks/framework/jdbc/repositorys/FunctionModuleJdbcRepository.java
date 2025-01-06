package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TFunctionModule;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 功能模块数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class FunctionModuleJdbcRepository extends JdbcRepository<FunctionModule, String> implements FunctionModuleRepository {
    public FunctionModuleJdbcRepository(JdbcOperations jdbcOperations) {
        super(TFunctionModule.FUNCTION_MODULE, jdbcOperations);
    }
}

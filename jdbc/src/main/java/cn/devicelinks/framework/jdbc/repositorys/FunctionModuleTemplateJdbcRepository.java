package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.FunctionModuleTemplate;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TFunctionModuleTemplate;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 功能模块模版数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class FunctionModuleTemplateJdbcRepository extends JdbcRepository<FunctionModuleTemplate, String> implements FunctionModuleTemplateRepository {
    public FunctionModuleTemplateJdbcRepository(JdbcOperations jdbcOperations) {
        super(TFunctionModuleTemplate.FUNCTION_MODULE_TEMPLATE, jdbcOperations);
    }
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysDepartmentLevel;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysDepartmentLevel.SYS_DEPARTMENT_LEVEL;

/**
 * 部门层级关系数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysDepartmentLevelJdbcRepository extends JdbcRepository<SysDepartmentLevel, String> implements SysDepartmentLevelRepository {
    public SysDepartmentLevelJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_DEPARTMENT_LEVEL, jdbcOperations);
    }
}

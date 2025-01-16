package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TSysDepartment.SYS_DEPARTMENT;

/**
 * The {@link SysDepartment} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysDepartmentJdbcRepository extends JdbcRepository<SysDepartment, String> implements SysDepartmentRepository {
	public SysDepartmentJdbcRepository(JdbcOperations jdbcOperations) {
		super(SYS_DEPARTMENT, jdbcOperations);
	}
}

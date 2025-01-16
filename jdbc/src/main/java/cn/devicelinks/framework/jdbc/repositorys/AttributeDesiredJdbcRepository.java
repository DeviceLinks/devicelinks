package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.AttributeDesired;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TAttributeDesired.ATTRIBUTE_DESIRED;

/**
 * The {@link AttributeDesired} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class AttributeDesiredJdbcRepository extends JdbcRepository<AttributeDesired, String> implements AttributeDesiredRepository {
	public AttributeDesiredJdbcRepository(JdbcOperations jdbcOperations) {
		super(ATTRIBUTE_DESIRED, jdbcOperations);
	}
}

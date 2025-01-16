package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TAttribute.ATTRIBUTE;

/**
 * The {@link Attribute} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class AttributeJdbcRepository extends JdbcRepository<Attribute, String> implements AttributeRepository {
	public AttributeJdbcRepository(JdbcOperations jdbcOperations) {
		super(ATTRIBUTE, jdbcOperations);
	}
}

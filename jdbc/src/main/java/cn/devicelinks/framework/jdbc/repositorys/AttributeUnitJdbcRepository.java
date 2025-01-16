package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.AttributeUnit;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TAttributeUnit.ATTRIBUTE_UNIT;

/**
 * The {@link AttributeUnit} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class AttributeUnitJdbcRepository extends JdbcRepository<AttributeUnit, String> implements AttributeUnitRepository {
	public AttributeUnitJdbcRepository(JdbcOperations jdbcOperations) {
		super(ATTRIBUTE_UNIT, jdbcOperations);
	}
}

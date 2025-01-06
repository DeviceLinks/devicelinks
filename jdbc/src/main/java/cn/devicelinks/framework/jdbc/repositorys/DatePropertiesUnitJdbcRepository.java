package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DataPropertiesUnit;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDataPropertiesUnit;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 数据属性单位数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DatePropertiesUnitJdbcRepository extends JdbcRepository<DataPropertiesUnit, String> implements DataPropertiesUnitRepository {
    public DatePropertiesUnitJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDataPropertiesUnit.DATA_PROPERTIES_UNIT, jdbcOperations);
    }
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DataProperties;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDataProperties;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 数据属性数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DataPropertiesJdbcRepository extends JdbcRepository<DataProperties, String> implements DataPropertiesRepository {
    public DataPropertiesJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDataProperties.DATA_PROPERTIES, jdbcOperations);
    }
}

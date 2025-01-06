package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceAttributeLatest;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备属性最新值数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeLatestJdbcRepository extends JdbcRepository<DeviceAttributeLatest, String> implements DeviceAttributeLatestRepository {
    public DeviceAttributeLatestJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceAttributeLatest.DEVICE_ATTRIBUTE_LATEST, jdbcOperations);
    }
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceAttributeDesired;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备期望属性数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeDesiredJdbcRepository extends JdbcRepository<DeviceAttributeDesired, String> implements DeviceAttributeDesiredRepository {
    public DeviceAttributeDesiredJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED, jdbcOperations);
    }
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED;

/**
 * 设备期望属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeDesiredJdbcRepository extends JdbcRepository<DeviceAttributeDesired, String> implements DeviceAttributeDesiredRepository {
    public DeviceAttributeDesiredJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE_DESIRED, jdbcOperations);
    }
}

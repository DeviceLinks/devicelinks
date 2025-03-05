package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeReported;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeReported.DEVICE_ATTRIBUTE_REPORTED;

/**
 * 设备上报属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeReportedJdbcRepository extends JdbcRepository<DeviceAttributeReported, String> implements DeviceAttributeReportedRepository {
    public DeviceAttributeReportedJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE_REPORTED, jdbcOperations);
    }
}

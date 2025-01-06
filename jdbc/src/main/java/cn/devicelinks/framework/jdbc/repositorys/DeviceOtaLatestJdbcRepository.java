package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceOtaLatest;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceOtaLatest;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备最新固件信息数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceOtaLatestJdbcRepository extends JdbcRepository<DeviceOtaLatest, String> implements DeviceOtaLatestRepository {
    public DeviceOtaLatestJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceOtaLatest.DEVICE_OTA_LATEST, jdbcOperations);
    }
}

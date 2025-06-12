package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceAttributeCreateWhitelist;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.tables.TDeviceAttributeCreateWhitelist;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备属性创建白名单数据接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceAttributeCreateWhitelistJdbcRepository extends JdbcRepository<DeviceAttributeCreateWhitelist, String> implements DeviceAttributeCreateWhitelistRepository {
    public DeviceAttributeCreateWhitelistJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceAttributeCreateWhitelist.DEVICE_ATTRIBUTE_CREATE_WHITELIST, jdbcOperations);
    }
}

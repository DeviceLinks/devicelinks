package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceShadowHistory;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TDeviceShadowHistory;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * The {@link DeviceShadowHistory} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceShadowHistoryJdbcRepository extends JdbcRepository<DeviceShadowHistory, String> implements DeviceShadowHistoryRepository {
    public DeviceShadowHistoryJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceShadowHistory.DEVICE_SHADOW_HISTORY, jdbcOperations);
    }
}

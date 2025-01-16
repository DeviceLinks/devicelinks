package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.NotificationType;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TNotificationType.NOTIFICATION_TYPE;

/**
 * The {@link NotificationType} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class NotificationTypeJdbcRepository extends JdbcRepository<NotificationType, String> implements NotificationTypeRepository {
	public NotificationTypeJdbcRepository(JdbcOperations jdbcOperations) {
		super(NOTIFICATION_TYPE, jdbcOperations);
	}
}

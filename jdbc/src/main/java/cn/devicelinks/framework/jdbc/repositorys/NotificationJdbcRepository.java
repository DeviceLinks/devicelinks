package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Notification;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TNotification.NOTIFICATION;

/**
 * The {@link Notification} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class NotificationJdbcRepository extends JdbcRepository<Notification, String> implements NotificationRepository {
	public NotificationJdbcRepository(JdbcOperations jdbcOperations) {
		super(NOTIFICATION, jdbcOperations);
	}
}

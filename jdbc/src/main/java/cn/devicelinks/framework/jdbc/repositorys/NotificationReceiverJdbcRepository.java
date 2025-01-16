package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.NotificationReceiver;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TNotificationReceiver.NOTIFICATION_RECEIVER;

/**
 * The {@link NotificationReceiver} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class NotificationReceiverJdbcRepository extends JdbcRepository<NotificationReceiver, String> implements NotificationReceiverRepository {
	public NotificationReceiverJdbcRepository(JdbcOperations jdbcOperations) {
		super(NOTIFICATION_RECEIVER, jdbcOperations);
	}
}

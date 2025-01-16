package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.NotificationTemplate;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TNotificationTemplate.NOTIFICATION_TEMPLATE;

/**
 * The {@link NotificationTemplate} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class NotificationTemplateJdbcRepository extends JdbcRepository<NotificationTemplate, String> implements NotificationTemplateRepository {
	public NotificationTemplateJdbcRepository(JdbcOperations jdbcOperations) {
		super(NOTIFICATION_TEMPLATE, jdbcOperations);
	}
}

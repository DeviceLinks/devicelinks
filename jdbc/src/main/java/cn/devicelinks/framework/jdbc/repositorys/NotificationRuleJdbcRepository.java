package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.NotificationRule;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TNotificationRule.NOTIFICATION_RULE;

/**
 * The {@link NotificationRule} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class NotificationRuleJdbcRepository extends JdbcRepository<NotificationRule, String> implements NotificationRuleRepository {
	public NotificationRuleJdbcRepository(JdbcOperations jdbcOperations) {
		super(NOTIFICATION_RULE, jdbcOperations);
	}
}

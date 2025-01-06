package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Command;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TCommand;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 指令数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class CommandJdbcRepository extends JdbcRepository<Command, String> implements CommandRepository {
    public CommandJdbcRepository(JdbcOperations jdbcOperations) {
        super(TCommand.COMMAND, jdbcOperations);
    }
}

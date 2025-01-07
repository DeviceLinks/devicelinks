package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.ProtocolGateway;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TProtocolGateway;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 协议网关数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ProtocolGatewayJdbcRepository extends JdbcRepository<ProtocolGateway, String> implements ProtocolGatewayRepository {
    public ProtocolGatewayJdbcRepository(JdbcOperations jdbcOperations) {
        super(TProtocolGateway.PROTOCOL_GATEWAY, jdbcOperations);
    }
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.SysUserSession;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.sql.FusionCondition;
import cn.devicelinks.framework.jdbc.core.sql.LimitCondition;
import cn.devicelinks.framework.jdbc.tables.TSysUserSession;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TSysUserSession.SYS_USER_SESSION;

/**
 * 用户会话数据JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class SysUserSessionJdbcRepository extends JdbcRepository<SysUserSession, String> implements SysUserSessionRepository {
    public SysUserSessionJdbcRepository(JdbcOperations jdbcOperations) {
        super(TSysUserSession.SYS_USER_SESSION, jdbcOperations);
    }

    @Override
    public SysUserSession selectLastByUsername(String username) {
        // @formatter:off
        List<SysUserSession> sessionList = this.select(FusionCondition
                .withCondition(SYS_USER_SESSION.USERNAME.eq(username))
                .sort(SYS_USER_SESSION.EXPIRES_TIME.desc())
                .limit(LimitCondition.withLimit(Constants.ONE))
                .build());
        // @formatter:on
        return ObjectUtils.isEmpty(sessionList) ? null : sessionList.getFirst();
    }

    @Override
    public SysUserSession selectByToken(String token) {
        return this.selectOne(SYS_USER_SESSION.TOKEN_VALUE.eq(token));
    }

    @Override
    public SysUserSession selectByUsernameAndToken(String username, String token) {
        return this.selectOne(SYS_USER_SESSION.USERNAME.eq(username),
                SYS_USER_SESSION.TOKEN_VALUE.eq(token));
    }
}

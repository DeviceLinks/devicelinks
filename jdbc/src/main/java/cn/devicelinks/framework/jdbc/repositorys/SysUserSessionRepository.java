package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.SysUserSession;
import cn.devicelinks.framework.jdbc.core.Repository;

/**
 * 用户会话数据接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysUserSessionRepository extends Repository<SysUserSession, String> {
    /**
     * 根据用户名查询最后的会话对象
     *
     * @param username {@link SysUserSession#getUsername()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectLastByUsername(String username);

    /**
     * 根据Token查询会话
     *
     * @param token {@link SysUserSession#getTokenValue()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectByToken(String token);

    /**
     * 根据用户名以及Token查询会话
     *
     * @param username {@link SysUserSession#getUsername()}
     * @param token    {@link SysUserSession#getTokenValue()}
     * @return {@link SysUserSession}
     */
    SysUserSession selectByUsernameAndToken(String username, String token);
}

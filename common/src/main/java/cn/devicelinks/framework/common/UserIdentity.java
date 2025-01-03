package cn.devicelinks.framework.common;

/**
 * 用户身份
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum UserIdentity {
    /**
     * 管理员
     * <p>
     * 当用户所属租户ID不为空时表示为“租户管理员”，为空时表示为“系统管理员”
     */
    administrator,
    /**
     * 普通用户
     */
    user
}

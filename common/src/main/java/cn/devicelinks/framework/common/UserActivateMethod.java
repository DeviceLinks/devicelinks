package cn.devicelinks.framework.common;

/**
 * 用户激活方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum UserActivateMethod {
    /**
     * 通过向用户邮箱发送激活链接的方式
     * <p>
     * 该方式相对安全，用户访问邮箱内的激活链接，设置密码后激活账号
     */
    SendUrlToEmail,
    /**
     * 显示激活链接
     * <p>
     * 默认方式，添加用户时直接显示激活链接，复制后发送给用户
     * 用户访问激活链接，设置密码后激活账号
     */
    ShowUrl
}

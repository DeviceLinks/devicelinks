package cn.devicelinks.framework.common;

/**
 * 记录日志的动作定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum LogAction {
    /**
     * 登录
     */
    Login,
    /**
     * 登出
     */
    Logout,
    /**
     * 新增
     */
    Add,
    /**
     * 编辑
     */
    Update,
    /**
     * 变更属性
     */
    UpdateAttribute,
    /**
     * 修改密码
     */
    ChangePwd,
    /**
     * 删除
     */
    Delete,
    /**
     * 绑定
     */
    Bind,
    /**
     * 解绑
     */
    Unbind
}

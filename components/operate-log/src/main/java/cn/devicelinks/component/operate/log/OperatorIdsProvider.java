package cn.devicelinks.component.operate.log;

/**
 * 操作者IDs信息提供者
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface OperatorIdsProvider {
    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getUserId();

    /**
     * 获取会话ID
     *
     * @return 会话ID
     */
    String getSessionId();

    /**
     * 获取用户所属部门ID
     *
     * @return 部门ID
     */
    String getDepartmentId();
}

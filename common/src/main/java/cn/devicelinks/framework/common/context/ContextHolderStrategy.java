package cn.devicelinks.framework.common.context;


/**
 * 上下文持有者策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ContextHolderStrategy<T extends Context> {
    /**
     * 获取数据权限上下文
     *
     * @return 上下文对象实例
     */
    T getContext();

    /**
     * 设置数据权限上下文
     *
     * @param context 上下文对象实例
     */
    void setContext(T context);

    /**
     * 清空数据权限上下文
     */
    void clearContext();
}

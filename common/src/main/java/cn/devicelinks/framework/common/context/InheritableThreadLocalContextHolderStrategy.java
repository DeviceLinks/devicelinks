package cn.devicelinks.framework.common.context;


/**
 * The {@link ContextHolderStrategy} InheritableThreadLocal Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class InheritableThreadLocalContextHolderStrategy<T extends Context> implements ContextHolderStrategy<T> {
    private final ThreadLocal<T> contextHolder = new InheritableThreadLocal<>();

    @Override
    public T getContext() {
        return contextHolder.get();
    }

    @Override
    public void setContext(T context) {
        contextHolder.set(context);
    }

    @Override
    public void clearContext() {
        contextHolder.remove();
    }
}

package cn.devicelinks.transport.support.context;

import cn.devicelinks.common.context.ContextHolderStrategy;
import cn.devicelinks.common.context.InheritableThreadLocalContextHolderStrategy;

/**
 * 设备上下文持有者
 *
 * @author 恒宇少年
 * @see DeviceContext
 * @since 1.0
 */
public class DeviceContextHolder {
    /**
     * The {@link DeviceContext} Context Holder Strategy
     */
    private static final ContextHolderStrategy<DeviceContext> strategy = new InheritableThreadLocalContextHolderStrategy<>();

    public static void setContext(DeviceContext context) {
        strategy.setContext(context);
    }

    public static void removeContext() {
        strategy.clearContext();
    }

    public static DeviceContext getContext() {
        return strategy.getContext();
    }
}

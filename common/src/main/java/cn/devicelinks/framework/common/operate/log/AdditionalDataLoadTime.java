package cn.devicelinks.framework.common.operate.log;

/**
 * 附加数据加载时机
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum AdditionalDataLoadTime {
    /**
     * 目标方法执行前后
     */
    All,
    /**
     * 目标方法执行之前
     */
    OperationBefore,
    /**
     * 目标方法执行之后
     */
    OperationAfter
}

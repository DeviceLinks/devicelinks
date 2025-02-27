package cn.devicelinks.framework.jdbc.core.annotation;

/**
 * ID生成策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum IdGenerationStrategy {
    /**
     * 自增
     */
    AUTO_INCREMENT,
    /**
     * The default
     *
     * @see cn.devicelinks.framework.common.utils.UUIDUtils
     */
    UUID,
    /**
     * @see cn.devicelinks.framework.common.utils.SnowflakeIdUtils
     */
    SNOWFLAKE,
    /**
     * @see cn.devicelinks.framework.common.utils.ObjectIdUtils
     */
    OBJECT_ID,
    /**
     * 不生成
     */
    NONE
}

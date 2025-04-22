package cn.devicelinks.jdbc.core.annotation;

import cn.devicelinks.common.utils.ObjectIdUtils;
import cn.devicelinks.common.utils.SnowflakeIdUtils;
import cn.devicelinks.common.utils.UUIDUtils;

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
     * @see UUIDUtils
     */
    UUID,
    /**
     * @see SnowflakeIdUtils
     */
    SNOWFLAKE,
    /**
     * @see ObjectIdUtils
     */
    OBJECT_ID,
    /**
     * 不生成
     */
    NONE
}

package cn.devicelinks.console.configuration.initializer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * 数据库初始化属性配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = DatabaseInitializerProperties.PREFIX)
public class DatabaseInitializerProperties {
    /**
     * The config prefix
     */
    public static final String PREFIX = "devicelinks.initializer.database";
    /**
     * 数据库名称
     */
    private String schemaName = "devicelinks";
    /**
     * 初始化表结构执行SQL文件位置
     */
    private Resource schemaSql;
    /**
     * 初始化表数据执行SQL文件位置
     */
    private Resource dataSql;
    /**
     * 每行SQL分隔符
     */
    private String sqlLineSeparator = ";";
    /**
     * 初始化方式
     */
    private DatabaseInitializerAway initializerAway = DatabaseInitializerAway.check;
    /**
     * 检查将要初始化的表结构数量的SQL
     * <p>
     * 该sql返回{@link Integer}类型的单值，如果该值大于{@link cn.devicelinks.common.Constants#ZERO}则不会执行初始化
     * 仅{@link #initializerAway}配置为{@link DatabaseInitializerAway#check}时生效
     */
    private String checkInitTablesCountSql;
}

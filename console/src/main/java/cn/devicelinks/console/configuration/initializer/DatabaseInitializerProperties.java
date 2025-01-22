/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
     * 该sql返回{@link Integer}类型的单值，如果该值大于{@link cn.devicelinks.framework.common.Constants#ZERO}则不会执行初始化
     * 仅{@link #initializerAway}配置为{@link DatabaseInitializerAway#check}时生效
     */
    private String checkInitTablesCountSql;
}

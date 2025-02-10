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

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 初始化数据库自动化配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EnableConfigurationProperties(DatabaseInitializerProperties.class)
@Configuration
public class DatabaseInitializerAutoConfiguration {
    private final DatabaseInitializerProperties initializerProperties;
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializerAutoConfiguration(DatabaseInitializerProperties initializerProperties, JdbcTemplate jdbcTemplate) {
        this.initializerProperties = initializerProperties;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 实例化数据库初始化器
     * <p>
     * 该类实例化后会调用{@link DatabaseInitializer#afterPropertiesSet()}方法根据配置属性进行数据库初始化逻辑
     *
     * @return {@link DatabaseInitializer}
     */
    @Bean
    @Order(Integer.MIN_VALUE)
    public DatabaseInitializer databaseInitializer() {
        return new DatabaseInitializer(this.initializerProperties, this.jdbcTemplate);
    }
}

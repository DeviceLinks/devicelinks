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

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.exception.DeviceLinksException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库初始化器
 * <p>
 * 根据配置属性以及数据源进行初始化数据库表结构以及表内的字典数据
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class DatabaseInitializer implements InitializingBean {

    private final DatabaseInitializerProperties initializerProperties;

    private final JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;

    public DatabaseInitializer(DatabaseInitializerProperties initializerProperties, JdbcTemplate jdbcTemplate) {
        this.initializerProperties = initializerProperties;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = jdbcTemplate.getDataSource();
    }

    private boolean checkWhetherInvoke() {
        if (DatabaseInitializerAway.check != this.initializerProperties.getInitializerAway()) {
            return true;
        }
        //@formatter:off
        if (ObjectUtils.isEmpty(initializerProperties.getCheckInitTablesCountSql()) ||
                ObjectUtils.isEmpty(initializerProperties.getSchemaName())) {
            throw new DeviceLinksException("[checkInitTablesCountSql] or [database] properties was not configured when initializing the database.");
        }
        // get created tables count
        Integer tableCount = this.jdbcTemplate.queryForObject(String.format(initializerProperties.getCheckInitTablesCountSql(),
                initializerProperties.getSchemaName()), Integer.class);
        //@formatter:on
        return tableCount == null || tableCount == Constants.ZERO;
    }

    private void invokeDatabaseScripts() {
        if (this.checkWhetherInvoke()) {
            if (ObjectUtils.isEmpty(initializerProperties.getSchemaSql()) && ObjectUtils.isEmpty(initializerProperties.getDataSql())) {
                log.warn("[schemaSql] and [dataSql] properties are not configured, skip database scripts initialization.");
                return;
            }
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            List<Resource> scripts = new ArrayList<>();
            if (!ObjectUtils.isEmpty(initializerProperties.getSchemaSql())) {
                scripts.add(initializerProperties.getSchemaSql());
            }
            if (!ObjectUtils.isEmpty(initializerProperties.getDataSql())) {
                scripts.add(initializerProperties.getDataSql());
            }
            populator.addScripts(scripts.toArray(Resource[]::new));
            populator.setSeparator(initializerProperties.getSqlLineSeparator());
            DatabasePopulatorUtils.execute(populator, this.dataSource);
            log.info("The database scripts:{} are initialized successfully.", scripts);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.invokeDatabaseScripts();
    }
}

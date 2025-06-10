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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TFunctionModule.FUNCTION_MODULE;


/**
 * The {@link FunctionModule} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class FunctionModuleJdbcRepository extends JdbcRepository<FunctionModule, String> implements FunctionModuleRepository {
    public FunctionModuleJdbcRepository(JdbcOperations jdbcOperations) {
        super(FUNCTION_MODULE, jdbcOperations);
    }

    @Override
    public List<FunctionModule> selectBySearchField(List<SearchFieldCondition> searchFieldConditions) {
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(FUNCTION_MODULE.getQuerySql())
                .and(FUNCTION_MODULE.DELETED.eq(false))
                .appendSearchFieldCondition(FUNCTION_MODULE, searchFieldConditions, DynamicWrapper.SelectBuilder.NONE_CONDITION_CONSUMER)
                .sort(FUNCTION_MODULE.CREATE_TIME.desc())
                .resultColumns(columns -> columns.addAll(FUNCTION_MODULE.getColumns()))
                .resultType(FunctionModule.class)
                .build();
        // @formatter:on
        return this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
    }
}

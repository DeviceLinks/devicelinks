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

import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.sql.Condition;
import cn.devicelinks.jdbc.core.sql.FusionCondition;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TSysDepartment.SYS_DEPARTMENT;

/**
 * The {@link SysDepartment} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class SysDepartmentJdbcRepository extends JdbcRepository<SysDepartment, String> implements SysDepartmentRepository {
    public SysDepartmentJdbcRepository(JdbcOperations jdbcOperations) {
        super(SYS_DEPARTMENT, jdbcOperations);
    }

    @Override
    public List<SysDepartment> selectListWithSearchConditions(List<SearchFieldCondition> searchFieldConditions) {
        // @formatter:off
        Condition[] conditions = searchFieldConditionToConditionArray(searchFieldConditions);
        FusionCondition fusionCondition = FusionCondition
                .withSort(SYS_DEPARTMENT.SORT.asc())
                .conditions(conditions)
                .build();
        // @formatter:on
        return this.select(fusionCondition);
    }
}

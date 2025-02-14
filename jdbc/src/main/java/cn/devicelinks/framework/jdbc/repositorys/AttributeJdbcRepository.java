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

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.*;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TAttribute.ATTRIBUTE;

/**
 * The {@link Attribute} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class AttributeJdbcRepository extends JdbcRepository<Attribute, String> implements AttributeRepository {

    public AttributeJdbcRepository(JdbcOperations jdbcOperations) {
        super(ATTRIBUTE, jdbcOperations);
    }

    @Override
    public PageResult<Attribute> getAttributesByPage(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        Condition[] conditions = searchFieldConditionList
                .stream()
                .map(condition -> {
                    Column searchColumn = this.table.getColumn(condition.getColumnName());
                    return Condition.withColumn(condition.getOperator(), searchColumn, condition.getValue());
                })
                .toArray(Condition[]::new);

        FusionCondition fusionCondition = FusionCondition
                .withConditions(conditions)
                .sort(sortCondition)
                .build();
        // @formatter:on

        return this.page(fusionCondition, pageQuery);
    }
}

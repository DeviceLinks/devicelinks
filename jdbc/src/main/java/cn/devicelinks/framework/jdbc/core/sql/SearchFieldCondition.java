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

package cn.devicelinks.framework.jdbc.core.sql;

import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.Getter;

/**
 * 检索字段条件
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class SearchFieldCondition {

    private final SqlFederationAway federationAway;
    private final String columnName;
    private final SqlQueryOperator operator;
    private final Object value;

    public SearchFieldCondition(SqlFederationAway federationAway, String columnName, SqlQueryOperator operator, Object value) {
        this.federationAway = federationAway;
        this.columnName = columnName;
        this.operator = operator;
        this.value = value;
    }
}

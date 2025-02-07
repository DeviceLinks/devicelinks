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

package cn.devicelinks.console.model.search;

import cn.devicelinks.framework.common.web.SearchFieldOperator;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 检索字段查询参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class SearchFieldQuery {

    private SearchFieldMatch searchMatch = SearchFieldMatch.ALL;

    private List<SearchFieldFilter> searchFields;

    public List<SearchFieldCondition> toSearchFieldConditionList() {
        List<SearchFieldCondition> searchFieldConditionList = new ArrayList<>();
        SqlFederationAway sqlFederationAway = this.toFederationAway();
        if (!ObjectUtils.isEmpty(this.searchFields)) {
            // @formatter:off
            this.searchFields.stream()
                    // ignore empty value
                    .filter(f -> !ObjectUtils.isEmpty(f.getValue()))
                    .map(filter ->
                            new SearchFieldCondition(sqlFederationAway, filter.getField(), this.toSqlQueryOperator(filter.getOperator()), filter.getValue()))
                    .forEach(searchFieldConditionList::add);
            // @formatter:on
        }
        return searchFieldConditionList;
    }

    private SqlQueryOperator toSqlQueryOperator(SearchFieldOperator searchFieldOperator) {
        if (searchFieldOperator != null) {
            return SqlQueryOperator.valueOf(searchFieldOperator.getValue());
        }
        return SqlQueryOperator.EqualTo;
    }

    private SqlFederationAway toFederationAway() {
        if (SearchFieldMatch.ALL == this.searchMatch) {
            return SqlFederationAway.AND;
        } else if (SearchFieldMatch.ANY == this.searchMatch) {
            return SqlFederationAway.OR;
        }
        return SqlFederationAway.AND;
    }
}

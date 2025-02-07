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

import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.web.SearchFieldModule;
import cn.devicelinks.framework.common.web.SearchFieldTemplate;
import cn.devicelinks.framework.common.web.SearchFieldTemplates;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 检索字段查询参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class SearchFieldQuery {

    @NotEmpty
    @EnumValid(target = SearchFieldModule.class, message = "检索字段模块参数非法")
    private String searchFieldModule;

    @NotEmpty
    @EnumValid(target = SearchFieldMatch.class, message = "检索字段运算符参数非法")
    private String searchMatch = SearchFieldMatch.ALL.toString();

    @Valid
    private List<SearchFieldFilter> searchFields;

    public List<SearchFieldCondition> toSearchFieldConditionList() {
        List<SearchFieldCondition> searchFieldConditionList = new ArrayList<>();
        SqlFederationAway sqlFederationAway = this.toFederationAway();
        if (!ObjectUtils.isEmpty(this.searchFields)) {
            List<SearchFieldTemplate> searchFieldTemplateList = SearchFieldTemplates.MODULE_SEARCH_FIELD_TEMPLATE_MAP.get(SearchFieldModule.valueOf(this.searchFieldModule));
            Map<String, SearchFieldTemplate> searchFieldTemplateMap = searchFieldTemplateList.stream().collect(Collectors.toMap(SearchFieldTemplate::getField, v -> v));
            // @formatter:off
            this.searchFields.stream()
                    // ignore empty value
                    .filter(f -> !ObjectUtils.isEmpty(f.getValue()))
                    .map(filter -> {
                        if (!searchFieldTemplateMap.containsKey(filter.getField())) {
                            throw new ApiException(StatusCode.SEARCH_FIELD_NOT_IN_MODULE, filter.getField(), this.searchFieldModule);
                        }
                        return new SearchFieldCondition(sqlFederationAway, filter.getField(), this.toSqlQueryOperator(filter.getOperator()), filter.getValue());
                    })
                    .forEach(searchFieldConditionList::add);
            // @formatter:on
        }
        return searchFieldConditionList;
    }

    private SqlQueryOperator toSqlQueryOperator(String searchFieldOperator) {
        if (searchFieldOperator != null) {
            return SqlQueryOperator.valueOf(searchFieldOperator);
        }
        return SqlQueryOperator.EqualTo;
    }

    private SqlFederationAway toFederationAway() {
        SearchFieldMatch searchMatch = SearchFieldMatch.valueOf(this.searchMatch);
        if (SearchFieldMatch.ALL == searchMatch) {
            return SqlFederationAway.AND;
        } else if (SearchFieldMatch.ANY == searchMatch) {
            return SqlFederationAway.OR;
        }
        return SqlFederationAway.AND;
    }
}

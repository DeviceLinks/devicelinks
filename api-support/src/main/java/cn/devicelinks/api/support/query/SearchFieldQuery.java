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

package cn.devicelinks.api.support.query;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.search.SearchFieldModuleFactory;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.common.web.*;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.*;
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
    @EnumValid(target = SearchFieldModuleIdentifier.class, message = "检索字段模块参数非法")
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
            List<SearchField> moduleSearchFieldList = SearchFieldModuleFactory.getSearchFields(SearchFieldModuleIdentifier.valueOf(this.searchFieldModule));
            Map<String, SearchField> moduleSearchFieldMap = moduleSearchFieldList.stream().collect(Collectors.toMap(SearchField::getField, v -> v));

            // check required fields present
            if (moduleSearchFieldList.stream().anyMatch(SearchField::isRequired)) {
                Set<String> searchFieldParameterSet = this.searchFields.stream().map(SearchFieldFilter::getField).collect(Collectors.toSet());
                List<String> requiredSearchFieldList = moduleSearchFieldList.stream().filter(SearchField::isRequired).map(SearchField::getField).toList();
                boolean allRequiredFieldsPresent = requiredSearchFieldList.stream().allMatch(searchFieldParameterSet::contains);
                if (!allRequiredFieldsPresent) {
                    throw new ApiException(StatusCodeConstants.SEARCH_FIELD_REQUIRED_NOT_PRESENT, requiredSearchFieldList);
                }
            }
            // @formatter:off
            this.searchFields.stream()
                    // ignore empty value
                    .filter(f -> !ObjectUtils.isEmpty(f.getValue()))
                    .map(filter -> {
                        // check field is in module
                        if (!moduleSearchFieldMap.containsKey(filter.getField())) {
                            throw new ApiException(StatusCode.SEARCH_FIELD_NOT_IN_MODULE, filter.getField(), this.searchFieldModule);
                        }
                        // check operator is support
                        moduleSearchFieldMap.get(filter.getField()).getOperators()
                                .stream()
                                .filter(operator -> operator.toString().equals(filter.getOperator()))
                                .findAny()
                                .orElseThrow(() -> new ApiException(StatusCode.SEARCH_FIELD_OPERATOR_NOT_SUPPORT, filter.getField(), filter.getOperator()));

                        // if value type is enum, convert value to enum object
                        SearchField searchField = moduleSearchFieldMap.get(filter.getField());
                        Object filterValue = filter.getValue();
                        if (SearchFieldValueType.ENUM == searchField.getValueType() && searchField.getEnumClass() != null) {
                            try {
                                // If an enum array is passed
                                if (filterValue instanceof List &&
                                        (SearchFieldOperator.In.toString().equals(filter.getOperator()) || SearchFieldOperator.NotIn.toString().equals(filter.getOperator()))) {
                                    List<String> enumList = (List<String>) filterValue;
                                    filterValue = enumList.stream().map(enumString -> Enum.valueOf(searchField.getEnumClass(), enumString)).toList();
                                } else {
                                    filterValue = Enum.valueOf(searchField.getEnumClass(), filter.getValue().toString());
                                }
                            } catch (Exception e) {
                                throw new ApiException(StatusCodeConstants.SEARCH_FIELD_ENUM_VALUE_ILLEGAL, filter.getField(), filter.getValue());
                            }
                        }
                        return new SearchFieldCondition(sqlFederationAway, StringUtils.lowerCamelToLowerUnder(filter.getField()), this.toSqlQueryOperator(filter.getOperator()), filterValue);
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

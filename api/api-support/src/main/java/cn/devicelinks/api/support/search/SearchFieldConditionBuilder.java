package cn.devicelinks.api.support.search;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.common.web.search.*;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.devicelinks.api.support.StatusCodeConstants.SEARCH_FIELD_NOT_IN_MODULE;
import static cn.devicelinks.api.support.StatusCodeConstants.SEARCH_FIELD_OPERATOR_NOT_SUPPORT;

/**
 * 检索字段查询条件{@link SearchFieldCondition}构建器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchFieldConditionBuilder {

    private String searchFieldModule;
    private String searchMatch;
    private List<SearchFieldFilter> searchFields;

    private SearchFieldConditionBuilder(SearchFieldQuery searchFieldQuery) {
        this.searchFieldModule = searchFieldQuery.getSearchFieldModule();
        this.searchMatch = searchFieldQuery.getSearchMatch();
        this.searchFields = searchFieldQuery.getSearchFields();
    }

    public static SearchFieldConditionBuilder from(SearchFieldQuery searchFieldQuery) {
        Assert.notNull(searchFieldQuery, "The SearchFieldQuery cannot be null.");
        return new SearchFieldConditionBuilder(searchFieldQuery);
    }

    public List<SearchFieldCondition> build() {
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
                            throw new ApiException(SEARCH_FIELD_NOT_IN_MODULE, filter.getField(), this.searchFieldModule);
                        }
                        // check operator is support
                        moduleSearchFieldMap.get(filter.getField()).getOperators()
                                .stream()
                                .filter(operator -> operator.toString().equals(filter.getOperator()))
                                .findAny()
                                .orElseThrow(() -> new ApiException(SEARCH_FIELD_OPERATOR_NOT_SUPPORT, filter.getField(), filter.getOperator()));

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

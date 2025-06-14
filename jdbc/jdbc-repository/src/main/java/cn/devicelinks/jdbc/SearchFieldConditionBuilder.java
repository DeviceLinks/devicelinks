package cn.devicelinks.jdbc;

import cn.devicelinks.common.utils.StringUtils;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.StatusCode;
import cn.devicelinks.component.web.search.*;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.jdbc.core.sql.operator.SqlQueryOperator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 检索字段查询条件{@link SearchFieldCondition}构建器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchFieldConditionBuilder {
    private final StatusCode SEARCH_FIELD_NOT_IN_MODULE = StatusCode.build("SEARCH_FIELD_NOT_IN_MODULE", "检索字段：[%s]，不在模块[%s]中.");
    private final StatusCode SEARCH_FIELD_OPERATOR_NOT_SUPPORT = StatusCode.build("SEARCH_FIELD_OPERATOR_NOT_SUPPORT", "检索字段：[%s]，不支持运算符：[%s].");
    private final StatusCode SEARCH_FIELD_ENUM_VALUE_ILLEGAL = StatusCode.build("SEARCH_FIELD_ENUM_VALUE_ILLEGAL", "检索字段：[%s]，枚举值：[%s]，并未定义，请检查是否有效.");
    private final StatusCode SEARCH_FIELD_REQUIRED_NOT_PRESENT = StatusCode.build("SEARCH_FIELD_REQUIRED_NOT_PRESENT", "检索字段：[%s]，必须全部传递.");
    private final StatusCode SEARCH_FIELD_VALUE_CANNOT_BE_NULL = StatusCode.build("SEARCH_FIELD_VALUE_CANNOT_BE_NULL", "检索字段：[%s]，值不允许为空.");

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
                    throw new ApiException(SEARCH_FIELD_REQUIRED_NOT_PRESENT, requiredSearchFieldList);
                }
            }
            // @formatter:off
            this.searchFields.stream()
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
                        // When the value is not allowed to be null, an exception is thrown
                        if(!searchField.isValueAllowNull() && filterValue == null) {
                            throw new ApiException(SEARCH_FIELD_VALUE_CANNOT_BE_NULL, searchField.getField());
                        }
                        if (filterValue != null && SearchFieldValueType.ENUM == searchField.getValueType() && searchField.getEnumClass() != null) {
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
                                throw new ApiException(SEARCH_FIELD_ENUM_VALUE_ILLEGAL, filter.getField(), filter.getValue());
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

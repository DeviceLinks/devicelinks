package cn.devicelinks.jdbc;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.common.Constants;
import cn.devicelinks.common.utils.StringUtils;
import cn.devicelinks.jdbc.core.definition.Column;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.sql.SortBy;
import cn.devicelinks.jdbc.core.sql.SortCondition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.api.model.query.PaginationQuery.DEFAULT_SORT_PROPERTY;

/**
 * The {@link PaginationQuery} Converter
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationQueryConverter {

    private static final SortCondition DEFAULT_SORT = SortCondition.withColumn(Column.withName(DEFAULT_SORT_PROPERTY).build()).asc();

    private static final PageQuery DEFAULT_PAGE_QUERY = PageQuery.of();

    private PaginationQuery paginationQuery;

    public static PaginationQueryConverter from(PaginationQuery paginationQuery) {
        return new PaginationQueryConverter(paginationQuery);
    }

    public SortCondition toSortCondition() {
        String sortProperty = paginationQuery.getSortProperty();
        String sortDirection = paginationQuery.getSortDirection();
        if (ObjectUtils.isEmpty(sortProperty) || sortDirection == null) {
            return DEFAULT_SORT;
        } else {
            String columnName = StringUtils.lowerCamelToLowerUnder(sortProperty);
            return SortCondition
                    .withColumn(Column.withName(columnName).build())
                    .by(SortBy.valueOf(sortDirection.toLowerCase()));
        }
    }

    public PageQuery toPageQuery() {
        int page = paginationQuery.getPage();
        int pageSize = paginationQuery.getPageSize();
        if (page <= Constants.ZERO || pageSize <= Constants.ZERO) {
            return DEFAULT_PAGE_QUERY;
        }
        return PageQuery.of(page, pageSize);
    }
}

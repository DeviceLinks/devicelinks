package cn.devicelinks.console.model.page;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.sql.SortBy;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;

/**
 * 分页查询请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PageRequest {
    private static final String DEFAULT_SORT_PROPERTY = "id";
    private static final SortCondition DEFAULT_SORT = SortCondition.withColumn(Column.withName(DEFAULT_SORT_PROPERTY).build()).asc();
    private static final PageQuery DEFAULT_PAGE_QUERY = PageQuery.of();

    private int pageSize;

    private int page;

    @Length(max = 50)
    private String sortProperty;

    @EnumValid(target = Direction.class, message = "排序顺序不允许传递非法值")
    private String sortDirection;

    public PageRequest(int pageSize) {
        this(pageSize, 0);
    }

    public PageRequest(int pageSize, int page) {
        this(pageSize, page, DEFAULT_SORT_PROPERTY, Direction.ASC.toString());
    }

    public PageRequest(int pageSize, int page, String sortProperty, String sortDirection) {
        this.pageSize = pageSize;
        this.page = page;
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    public SortCondition toSortCondition() {
        if (ObjectUtils.isEmpty(sortProperty) || sortDirection == null) {
            return DEFAULT_SORT;
        } else {
            return SortCondition
                    .withColumn(Column.withName(this.sortProperty).build())
                    .by(SortBy.valueOf(this.sortDirection.toLowerCase()));
        }
    }

    public PageQuery toPageQuery() {
        if (this.page <= Constants.ZERO || this.pageSize <= Constants.ZERO) {
            return DEFAULT_PAGE_QUERY;
        }
        return PageQuery.of(this.page, this.pageSize);
    }

    public enum Direction {
        ASC, DESC
    }
}

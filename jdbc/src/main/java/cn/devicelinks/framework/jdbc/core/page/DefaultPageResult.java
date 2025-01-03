package cn.devicelinks.framework.jdbc.core.page;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * The {@link PageResult} Default Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DefaultPageResult<T extends Serializable> extends AbstractPageResult<T> {

    private DefaultPageResult(int pageIndex, int pageSize) {
        super(pageIndex, pageSize);
    }

    private DefaultPageResult(int pageIndex, int pageSize, int totalRows, List<T> result) {
        super(pageIndex, pageSize, totalRows, result);
    }

    public static DefaultPageResultBuilder withPageQuery(PageQuery pageQuery) {
        return new DefaultPageResultBuilder(pageQuery);
    }

    public static class DefaultPageResultBuilder {
        private final PageQuery pageQuery;
        private int totalRows;
        private List<? extends Serializable> result;

        public DefaultPageResultBuilder(PageQuery pageQuery) {
            Assert.notNull(pageQuery, "pageQuery must not be null");
            this.pageQuery = pageQuery;
        }

        public DefaultPageResultBuilder totalRows(int totalRows) {
            this.totalRows = totalRows;
            return this;
        }

        public DefaultPageResultBuilder result(List<? extends Serializable> result) {
            this.result = result;
            return this;
        }

        public DefaultPageResult<? extends Serializable> build() {
            return new DefaultPageResult<>(pageQuery.getPageIndex(), pageQuery.getPageSize(), totalRows, result);
        }
    }
}

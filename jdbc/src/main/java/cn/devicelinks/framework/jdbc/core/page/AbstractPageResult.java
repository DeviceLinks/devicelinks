package cn.devicelinks.framework.jdbc.core.page;

import cn.devicelinks.framework.common.Constants;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * The {@link PageResult} Abstract Class Impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class AbstractPageResult<T extends Serializable> implements PageResult<T> {
    public static final String PAGE_RESULT_COUNT_NAME = "PageResultCount";
    protected final int page;
    protected final int pageSize;
    protected int totalPages;
    protected int totalRows;
    protected List<T> result;

    public AbstractPageResult(int page, int pageSize) {
        Assert.isTrue(page > Constants.ZERO, "page must be greater than 0");
        Assert.isTrue(pageSize > Constants.ZERO, "pageSize must be greater than 0");
        this.page = page;
        this.pageSize = pageSize;
    }

    public AbstractPageResult(int page, int pageSize, int totalRows, List<T> result) {
        this(page, pageSize);
        this.totalRows = totalRows;
        this.result = result;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getTotalRows() {
        return this.totalRows;
    }

    @Override
    public int getTotalPages() {
        int totalPage = getTotalRows() / getPageSize();
        return getTotalRows() % getPageSize() == Constants.ZERO ? totalPage : totalPage + Constants.ONE;
    }

    @Override
    public boolean hasNext() {
        return this.totalPages > Constants.ZERO && this.totalPages - this.page > Constants.ZERO;
    }

    @Override
    public boolean hasPrevious() {
        return this.totalPages > Constants.ZERO && this.page > Constants.ONE;
    }

    @Override
    public List<T> getResult() {
        return this.result;
    }
}

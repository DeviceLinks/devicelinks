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

package cn.devicelinks.jdbc.core.page;

import cn.devicelinks.common.Constants;
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

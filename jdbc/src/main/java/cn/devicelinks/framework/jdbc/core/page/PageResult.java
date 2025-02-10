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

package cn.devicelinks.framework.jdbc.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface PageResult<T extends Serializable> {
    /**
     * 获取当前页码索引
     * <p>
     * 页码索引从1开始，如果没有数据返回0
     *
     * @return 当前页码索引
     */
    int getPage();

    /**
     * 获取每页条数
     * <p>
     * 每页默认查询20条数据，可以自定义修改
     *
     * @return 每页条数
     */
    int getPageSize();

    /**
     * 获取数据总行数据
     *
     * @return 未分页之前的总行数
     */
    int getTotalRows();

    /**
     * 获取数据总页数
     * <p>
     * 未分页之前的总行数 % 每页条数，如果不为0则加
     *
     * @return 总页数
     */
    int getTotalPages();

    /**
     * 是否存在下一页
     *
     * @return 返回true时表示存在下一页数据
     */
    boolean hasNext();

    /**
     * 是否存在上一页数据
     *
     * @return 返回true时表示存在下一页数据
     */
    boolean hasPrevious();

    /**
     * 获取分页后当前页码的数据列表
     *
     * @return 当前页码的全部数据
     */
    List<T> getResult();
}

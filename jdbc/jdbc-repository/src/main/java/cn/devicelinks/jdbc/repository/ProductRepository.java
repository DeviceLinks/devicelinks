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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.Product;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * The {@link Product} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ProductRepository extends Repository<Product, String> {
    /**
     * 分页获取产品列表
     *
     * @param searchFieldConditionList 搜索字段条件列表
     * @param pageQuery                分页查询参数
     * @param sortCondition            排序条件
     * @return 返回一个 {@link PageResult} 对象，包含符合条件的 {@link Product} 列表和分页信息。
     */
    PageResult<Product> getProductsByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);

    /**
     * 清空产品绑定的设备配置ID
     *
     * @param profileId 设备配置ID {@link Product#getDeviceProfileId()}
     */
    void clearDeviceProfileId(String profileId);
}

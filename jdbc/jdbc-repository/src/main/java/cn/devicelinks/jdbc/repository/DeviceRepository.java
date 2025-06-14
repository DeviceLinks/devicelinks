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

import cn.devicelinks.common.DeviceType;
import cn.devicelinks.entity.Device;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * The {@link Device} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceRepository extends Repository<Device, String> {
    /**
     * 根据条件查询并分页
     *
     * @param searchFieldConditionList 搜索条件列表
     * @param pageQuery                分页查询参数
     * @param sortCondition            排序条件
     * @return 返回一个 {@link PageResult} 对象，包含符合条件的 {@link Device} 列表和分页信息。
     */
    PageResult<Device> selectByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);

    /**
     * 清空设置指定配置文件的ID
     *
     * @param profileId 设备配置文件ID {@link Device#getProfileId()}
     */
    void clearDeviceProfileId(String profileId);

    /**
     * 根据设备ID列表批量更新设备配置文件
     *
     * @param profileId 设备配置文件ID {@link Device#getProfileId()}
     * @param deviceIds 设备ID列表 {@link Device#getId()}
     */
    void updateDeviceProfileIdWithDeviceIds(String profileId, List<String> deviceIds);

    /**
     * 根据设备标签批量更新设备配置文件ID
     *
     * @param productId 产品ID {@link Device#getProductId()}
     * @param profileId 设备配置文件ID {@link Device#getProductId()}
     * @param tags      设备标签列表，多个标签之间通过"Or"方式匹配
     */
    void updateDeviceProfileIdWithTags(String productId, String profileId, List<String> tags);

    /**
     * 根据设备类型匹配更新设备匹配文件ID
     *
     * @param productId  产品ID {@link Device#getProductId()}
     * @param profileId  设备配置文件ID {@link Device#getProductId()}
     * @param deviceType 设备类型 {@link DeviceType}
     */
    void updateDeviceProfileIdWithDeviceType(String productId, String profileId, DeviceType deviceType);
}

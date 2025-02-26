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

package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

import java.util.List;

/**
 * 设备业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceService extends BaseService<Device, String> {
    /**
     * 分页获取设备列表
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return 返回 {@link PageResult} 包含设备列表和分页信息，或 {@link PageResult} 包含错误信息。
     */
    PageResult<Device> selectByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);

    /**
     * 查询指定产品下的所有设备
     *
     * @param productId 产品ID {@link Device#getProductId()}
     * @return 设备列表 {@link Device}
     */
    List<Device> selectByProductId(String productId);

    /**
     * 添加一个新的设备
     *
     * @param device                 要添加的设备 {@link Device}
     * @param authenticationMethod   认证方法 {@link DeviceAuthenticationMethod}
     * @param authenticationAddition 认证附加信息 {@link DeviceAuthenticationAddition}
     * @return 返回添加的设备 {@link Device}
     */
    Device addDevice(Device device, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition);
}

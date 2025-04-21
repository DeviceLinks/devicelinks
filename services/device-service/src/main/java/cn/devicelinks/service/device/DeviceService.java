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

package cn.devicelinks.service.device;

import cn.devicelinks.api.support.query.PaginationQuery;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentialsAddition;
import cn.devicelinks.framework.common.secret.AesSecretKeySet;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceDTO;

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
     * 根据设备名称查询设备信息
     *
     * @param deviceName 设备名称 {@link Device#getDeviceName()}
     * @return 设备基本信息 {@link Device}
     */
    Device selectByName(String deviceName);

    /**
     * 查询指定产品下的所有设备
     *
     * @param productId 产品ID {@link Device#getProductId()}
     * @return 设备列表 {@link Device}
     */
    List<Device> selectByProductId(String productId);

    /**
     * 根据设备ID查询设备详情
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return {@link DeviceDTO}
     */
    DeviceDTO selectByDeviceId(String deviceId);

    /**
     * 添加一个新的设备
     *
     * @param device              要添加的设备 {@link Device}
     * @param credentialsType     认证方法 {@link DeviceCredentialsType}
     * @param credentialsAddition 认证附加信息 {@link DeviceCredentialsAddition}
     * @return 返回添加的设备 {@link Device}
     */
    Device addDevice(Device device, DeviceCredentialsType credentialsType,
                     DeviceCredentialsAddition credentialsAddition, AesSecretKeySet deviceSecretKeySet);

    /**
     * 更新设备信息
     *
     * @param device 待更新的设备信息 {@link Device}
     * @return 更新后的设备信息 {@link Device}
     */
    Device updateDevice(Device device);

    /**
     * 删除设备信息
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return 已删除的设备信息 {@link Device}
     */
    Device deleteDevice(String deviceId);

    /**
     * 更新设备启用状态
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @param enabled  启用状态 {@link Device#isEnabled()}
     */
    void updateEnabled(String deviceId, boolean enabled);

    /**
     * 激活设备
     *
     * @param deviceId 设备ID {@link Device#getId()}
     */
    void activateDevice(String deviceId);
}

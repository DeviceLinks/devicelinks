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

import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceCredentials;
import cn.devicelinks.entity.DeviceCredentialsAddition;
import cn.devicelinks.jdbc.core.Repository;

/**
 * The {@link DeviceCredentials} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceCredentialsRepository extends Repository<DeviceCredentials, String> {
    /**
     * 通过设备ID查询设备鉴权信息
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return {@link DeviceCredentials}
     */
    DeviceCredentials selectByDeviceId(String deviceId);

    /**
     * 通过AccessToken查询鉴权信息
     *
     * @param token 令牌（静态令牌、动态令牌）
     * @return 鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials selectByToken(String token);

    /**
     * 根据令牌哈希值获取设备凭证信息
     *
     * @param tokenHash 令牌哈希值
     * @return {@link DeviceCredentials}
     */
    DeviceCredentials selectByTokenHash(String tokenHash);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceCredentialsAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials selectByClientId(String clientId);

    /**
     * 通过deviceSecret查询鉴权信息
     *
     * @param deviceSecret 设备Secret
     * @return 鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials selectByDeviceSecret(String deviceSecret);
}

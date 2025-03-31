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

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.jdbc.core.Repository;

/**
 * The {@link DeviceAuthentication} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAuthenticationRepository extends Repository<DeviceAuthentication, String> {
    /**
     * 通过设备ID查询设备鉴权信息
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByDeviceId(String deviceId);

    /**
     * 通过AccessToken查询鉴权信息
     *
     * @param staticToken 静态令牌
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByStaticToken(String staticToken);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceAuthenticationAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByClientId(String clientId);

    /**
     * 通过deviceSecret查询鉴权信息
     *
     * @param deviceSecret 设备Secret {@link DeviceAuthenticationAddition.DynamicToken#getDeviceSecret()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByDeviceSecret(String deviceSecret);
}

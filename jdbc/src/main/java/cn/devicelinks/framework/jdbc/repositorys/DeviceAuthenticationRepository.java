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
     * 通过AccessToken查询鉴权信息
     *
     * @param accessToken 请求令牌
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByAccessToken(String accessToken);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceAuthenticationAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByClientId(String clientId);

    /**
     * 通过deviceKey查询鉴权信息
     *
     * @param deviceKey    设备Key {@link DeviceAuthenticationAddition.DeviceCredential#getDeviceKey()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByDeviceKey(String deviceKey);
}

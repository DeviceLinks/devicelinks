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

package cn.devicelinks.framework.common;

/**
 * 设备鉴权方式
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum DeviceAuthenticationMethod {
    /**
     * 一型一密
     * <p>
     * 根据ProductKey以及ProductSecret进行鉴权
     */
    ProductCredential,
    /**
     * 一机一密
     * <p>
     * 根据ProductSecret、DeviceCode、DeviceSecret进行鉴权
     */
    DeviceCredential,
    /**
     * 请求令牌
     */
    AccessToken,
    /**
     * MQTT基础认证
     * <p>
     * 客户端ID、用户名、密码
     */
    MqttBasic,
    /**
     * X.509证书
     * <p>
     * PEM格式证书
     */
    X509
}

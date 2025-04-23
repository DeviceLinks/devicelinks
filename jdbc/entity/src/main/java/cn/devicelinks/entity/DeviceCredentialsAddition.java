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

package cn.devicelinks.entity;

import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.common.secret.AesProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备鉴权附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceCredentialsAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    /**
     * @see DeviceCredentialsType#StaticToken
     * @see DeviceCredentialsType#DynamicToken
     */
    private String token;

    /**
     * @see DeviceCredentialsType#X509
     */
    private String x509Pem;

    /**
     * @see DeviceCredentialsType#MqttBasic
     */
    private MqttBasic mqttBasic;
    /**
     * Aes加密算法属性，敏感数据通过该算法对称加密
     */
    private AesProperties aes;

    @Data
    @Accessors(chain = true)
    public static class MqttBasic {
        private String clientId;
        private String username;
        private String password;
    }
}

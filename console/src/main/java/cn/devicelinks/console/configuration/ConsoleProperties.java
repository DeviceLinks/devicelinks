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

package cn.devicelinks.console.configuration;

import cn.devicelinks.framework.common.secret.AesSecretKeySet;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 属性配置类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = ConsoleProperties.DEVICELINKS_CONSOLE_PREFIX)
@Data
public class ConsoleProperties {
    public static final String DEVICELINKS_CONSOLE_PREFIX = "devicelinks.console";

    private TokenSettingProperties token = new TokenSettingProperties();
    /**
     * DeviceSecret存储时AES加密算法的Keys
     */
    @NestedConfigurationProperty
    private AesSecretKeySet deviceSecretKeySet;

    /**
     * The token setting
     */
    @Data
    public static class TokenSettingProperties {
        /**
         * 令牌有效时长，单位：秒
         */
        private long expiresSeconds = 43200;
    }
}

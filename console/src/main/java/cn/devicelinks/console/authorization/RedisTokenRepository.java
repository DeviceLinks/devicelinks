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

package cn.devicelinks.console.authorization;

import cn.devicelinks.console.configuration.ConsoleProperties;
import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * The {@link TokenRepository} redis impl
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class RedisTokenRepository implements TokenRepository {

    private static final String TOKEN_KEY_FORMAT = "devicelinks:token:%s";

    private static final long DEFAULT_EXPIRES_SECONDS = 43200;

    private final ConsoleProperties.TokenSetting tokenSetting;

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisTokenRepository(ConsoleProperties consoleProperties, RedisTemplate<Object, Object> redisTemplate) {
        this.tokenSetting = consoleProperties.getTokenSetting();
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    @Override
    public long getExpiresSeconds() {
        return tokenSetting.getExpiresSeconds();
    }

    @Override
    public void save(String token, DeviceLinksUserDetails userDetails) {
        String key = this.formatKey(token);
        long expiresSeconds = this.getExpiresSeconds();
        expiresSeconds = expiresSeconds > 0 ? expiresSeconds : DEFAULT_EXPIRES_SECONDS;
        this.redisTemplate.opsForValue().set(key, userDetails, expiresSeconds, TimeUnit.SECONDS);
    }

    @Override
    public DeviceLinksUserDetails get(String token) {
        String key = this.formatKey(token);
        return (DeviceLinksUserDetails) this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String token) {
        String key = this.formatKey(token);
        this.redisTemplate.delete(key);
    }

    private String formatKey(String token) {
        return String.format(TOKEN_KEY_FORMAT, token);
    }
}

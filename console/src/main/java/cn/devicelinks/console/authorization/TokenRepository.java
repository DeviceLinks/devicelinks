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

import cn.devicelinks.framework.common.authorization.DeviceLinksUserDetails;

/**
 * The token repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface TokenRepository {
    /**
     * 返回该令牌存储器存储的令牌过期时长，单位：秒
     *
     * @return 令牌有效时长
     */
    long getExpiresSeconds();

    /**
     * 存储令牌
     *
     * @param token       令牌
     * @param userDetails 用户详情对象实例
     */
    void save(String token, DeviceLinksUserDetails userDetails);

    /**
     * 获取令牌用户详情
     *
     * @param token 令牌
     * @return {@link DeviceLinksUserDetails}
     */
    DeviceLinksUserDetails get(String token);

    /**
     * 删除指定令牌
     *
     * @param token 令牌
     */
    void remove(String token);
}

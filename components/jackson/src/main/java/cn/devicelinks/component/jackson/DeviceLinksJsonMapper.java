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

package cn.devicelinks.component.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装Jackson序列化与反序列化映射类
 * <p>
 * 默认注册{@link DeviceLinksSerializableModule}序列化模式
 *
 * @author 恒宇少年
 * @see DeviceLinksSerializableModule
 * @see ObjectMapper
 * @since 0.0.6
 */
public final class DeviceLinksJsonMapper extends ObjectMapper {
    public DeviceLinksJsonMapper() {
        super();
        this.registerModule(new DeviceLinksSerializableModule());
    }
}

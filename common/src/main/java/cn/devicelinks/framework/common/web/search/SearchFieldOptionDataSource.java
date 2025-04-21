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

package cn.devicelinks.framework.common.web.search;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 检索字段选项数据源
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum SearchFieldOptionDataSource {
    /**
     * 接口查询
     */
    API("接口查询"),
    /**
     * 静态数据
     */
    STATIC("静态数据");
    private final String description;

    SearchFieldOptionDataSource(String description) {
        this.description = description;
    }
}

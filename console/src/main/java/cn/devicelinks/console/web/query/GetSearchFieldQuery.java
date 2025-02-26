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

package cn.devicelinks.console.web.query;

import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 获取检索字段请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class GetSearchFieldQuery {

    @NotEmpty
    @EnumValid(target = SearchFieldModuleIdentifier.class, message = "检索字段模块不允许传递非法值")
    private String module;
}

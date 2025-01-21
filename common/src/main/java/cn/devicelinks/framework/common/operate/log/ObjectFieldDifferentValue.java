/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.common.operate.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 操作字段区别值定义类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectFieldDifferentValue {
    /**
     * 字段
     */
    private String field;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 操作之前的字段值
     */
    private Object beforeValue;
    /**
     * 操作之后的字段值
     */
    private Object afterValue;
    /**
     * 操作之前与之后是否不同
     */
    private boolean different;
}

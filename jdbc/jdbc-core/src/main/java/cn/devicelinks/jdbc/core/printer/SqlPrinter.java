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

package cn.devicelinks.jdbc.core.printer;

import cn.devicelinks.jdbc.core.RepositoryMethod;

import java.util.List;
import java.util.Map;

/**
 * SQL打印接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SqlPrinter {
    /**
     * 打印SQL
     *
     * @param sql             执行SQL
     * @param parameterValues 参数值列表
     * @param resultRowMap    结果行集合，当数据方法为{@link RepositoryMethod#Query}时存在该值
     * @param affectedRows    影响行数
     */
    void print(RepositoryMethod method, String sql, Object[] parameterValues, List<Map<String, Object>> resultRowMap, int affectedRows);
}

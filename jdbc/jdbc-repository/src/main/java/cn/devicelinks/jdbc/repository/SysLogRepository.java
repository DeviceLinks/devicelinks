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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.SysLog;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * The {@link SysLog} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysLogRepository extends Repository<SysLog, String> {
    /**
     * 分页获取日志列表
     *
     * @param searchFieldConditionList 检索条件列表
     * @param pageQuery     分页参数
     * @param sortCondition 排序参数
     * @return 日志列表 {@link SysLog}
     */
    PageResult<SysLog> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);
}

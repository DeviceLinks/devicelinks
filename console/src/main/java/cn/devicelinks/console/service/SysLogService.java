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

package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 操作日志业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysLogService extends BaseService<SysLog, String> {
    /**
     * 分页获取日志列表
     *
     * @param paginationQuery  分页查询参数
     * @param searchFieldQuery 检索字段查询参数
     * @return 日志列表 {@link SysLog}
     */
    PageResult<SysLog> getByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);
}

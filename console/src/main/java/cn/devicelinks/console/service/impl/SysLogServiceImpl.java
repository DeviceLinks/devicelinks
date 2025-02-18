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

package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.SysLogService;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.repositorys.SysLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 操作日志业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysLogServiceImpl extends BaseServiceImpl<SysLog, String, SysLogRepository>
        implements SysLogService {
    public SysLogServiceImpl(SysLogRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<SysLog> getByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}

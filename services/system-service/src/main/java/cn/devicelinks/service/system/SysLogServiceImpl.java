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

package cn.devicelinks.service.system;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.SysLog;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.SysLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }
}

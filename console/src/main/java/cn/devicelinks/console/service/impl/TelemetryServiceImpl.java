package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.TelemetryService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.Telemetry;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.repositorys.TelemetryRepository;
import org.springframework.stereotype.Service;

/**
 * 遥测数据业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class TelemetryServiceImpl extends BaseServiceImpl<Telemetry, String, TelemetryRepository> implements TelemetryService {
    public TelemetryServiceImpl(TelemetryRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<Telemetry> getTelemetryByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.getTelemetryByPage(searchFieldQuery.toSearchFieldConditionList(),
                paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}

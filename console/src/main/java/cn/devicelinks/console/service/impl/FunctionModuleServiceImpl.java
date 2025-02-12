package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.repositorys.FunctionModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能模块业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class FunctionModuleServiceImpl extends BaseServiceImpl<FunctionModule, String, FunctionModuleRepository> implements FunctionModuleService {
    public FunctionModuleServiceImpl(FunctionModuleRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<FunctionModule> getFunctionModulesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.selectByPage(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}

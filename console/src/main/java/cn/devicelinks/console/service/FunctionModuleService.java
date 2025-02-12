package cn.devicelinks.console.service;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 功能模块业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface FunctionModuleService {
    /**
     * 获取功能模块列表
     *
     * @param paginationQuery  分页参数实体
     * @param searchFieldQuery 检索字段参数实体
     * @return {@link FunctionModule}
     */
    PageResult<FunctionModule> getFunctionModulesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);
}

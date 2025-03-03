package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.Telemetry;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 遥测数据业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface TelemetryService extends BaseService<Telemetry, String> {
    /**
     * 分页获取遥测数据
     *
     * @param paginationQuery  分页查询参数
     * @param searchFieldQuery 搜索字段查询参数
     * @return 遥测数据列表分页对象
     */
    PageResult<Telemetry> getTelemetryByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);
}

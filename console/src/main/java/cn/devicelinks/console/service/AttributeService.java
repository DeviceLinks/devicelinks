package cn.devicelinks.console.service;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface AttributeService extends BaseService<Attribute, String> {

    /**
     * 根据分页查询参数和搜索字段查询参数获取属性列表。
     * <p>
     * 此方法用于检索符合条件的属性数据，并支持分页功能。
     *
     * @param paginationQuery  分页查询参数，包含当前页码和每页大小 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数，包含用于过滤属性的字段 {@link SearchFieldQuery}
     * @return 返回一个 {@link PageResult} 对象，其中包含符合条件的 {@link Attribute} 列表和分页信息。
     */
    PageResult<Attribute> getAttributesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);
}
package cn.devicelinks.console.service;

import cn.devicelinks.console.model.attribute.AddAttributeRequest;
import cn.devicelinks.console.model.attribute.UpdateAttributeRequest;
import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;

/**
 * 属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface AttributeService extends BaseService<Attribute, String> {
    /**
     * 添加属性
     * <p>
     * 此方法使用AddAttributeRequest对象中包含的信息创建新属性。
     * 它处理请求并将新创建的属性作为AttributeDTO返回。
     *
     * @param request AddAttributeRequest对象，包含要添加的属性的详细信息。
     *                这包括属性名称、类型和任何其他相关详细信息。
     * @return 返回一个AttributeDTO对象，表示新创建的属性。
     * 这个DTO包含刚刚添加到系统中的属性的所有详细信息。
     */
    AttributeDTO addAttribute(AddAttributeRequest request);

    /**
     * 更新属性
     * <p>
     * 此方法使用 UpdateAttributeRequest 对象中包含的信息更新由给定 attributeId 标识的属性。
     * 它处理请求并将更新后的属性作为 AttributeDTO 返回。
     *
     * @param attributeId 要更新的属性的唯一标识符。
     *                    这应该对应于系统中已存在的属性。
     * @param request     UpdateAttributeRequest 对象，包含属性的更新信息。
     *                    这可能包括对属性名称、类型或其他相关详细信息的更改。
     * @return 返回一个 AttributeDTO 对象，表示更新后的属性。
     * 这个 DTO 包含更新应用到系统后的属性的所有详细信息。
     */
    AttributeDTO updateAttribute(String attributeId, UpdateAttributeRequest request);

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

    /**
     * 根据属性ID获取属性详情。
     *
     * @param attributeId 属性 ID {@link Attribute#getId()}
     * @return 返回 {@link AttributeDTO}，包含属性的详细信息。
     */
    AttributeDTO getAttributeById(String attributeId);

    /**
     * 删除属性
     *
     * @param attributeId 属性 ID {@link Attribute#getId()}
     * @return 返回 {@link Attribute}，包含已删除的属性信息。
     */
    Attribute deleteAttribute(String attributeId);
}
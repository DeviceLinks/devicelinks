package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.ExtractUnknownReportedAttributeRequest;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttributeReported;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeReportedDTO;

/**
 * 设备上报属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeReportedService extends BaseService<DeviceAttributeReported, String> {
    /**
     * 分页获取设备上报属性
     *
     * @param searchFieldQuery 检索字段查询对象 {@link SearchFieldQuery}
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @return 设备上报属性分页对象
     */
    PageResult<DeviceAttributeReportedDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery);

    /**
     * 提取未知上报属性
     *
     * @param reportAttributeId 上报属性ID {@link DeviceAttributeReported#getId()}
     * @param request           提取未知上报属性的请求参数
     * @return 存储后的已知属性 {@link Attribute}
     */
    Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownReportedAttributeRequest request);
}

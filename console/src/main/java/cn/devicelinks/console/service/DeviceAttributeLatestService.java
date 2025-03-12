package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceAttributeChartRequest;
import cn.devicelinks.console.web.request.ExtractUnknownLatestAttributeRequest;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;

/**
 * 设备属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeLatestService extends BaseService<DeviceAttributeLatest, String> {
    /**
     * 分页获取设备属性
     *
     * @param searchFieldQuery 检索字段查询对象 {@link SearchFieldQuery}
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @return 设备属性分页对象
     */
    PageResult<DeviceAttributeLatestDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery);

    /**
     * 提取未知属性
     *
     * @param reportAttributeId 属性ID {@link DeviceAttributeLatest#getId()}
     * @param request           提取未知属性的请求参数
     * @return 存储后的已知属性 {@link Attribute}
     */
    Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownLatestAttributeRequest request);

    /**
     * 添加设备属性图表
     *
     * @param deviceId 设备ID {@link DeviceAttributeLatest#getDeviceId()}
     * @param request  添加设备属性图表请求参数 {@link AddDeviceAttributeChartRequest}
     * @return 数据图表配置ID {@link ChartDataConfig#getId()}
     */
    String addDeviceAttributeChart(String deviceId, AddDeviceAttributeChartRequest request);
}

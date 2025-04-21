package cn.devicelinks.service.device;

import cn.devicelinks.api.support.query.PaginationQuery;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.api.support.request.AddDeviceTelemetryRequest;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

/**
 * 遥测数据业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceTelemetryService extends BaseService<DeviceTelemetry, String> {
    /**
     * 分页获取遥测数据
     *
     * @param paginationQuery  分页查询参数
     * @param searchFieldQuery 搜索字段查询参数
     * @return 遥测数据列表分页对象
     */
    PageResult<DeviceTelemetry> getTelemetryByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);

    /**
     * 添加设备遥测数据
     *
     * @param deviceId 设备ID {@link DeviceTelemetry#getDeviceId()}
     * @param request  添加设备遥测数据请求实体 {@link AddDeviceTelemetryRequest}
     * @return 已添加的遥测数据实体 {@link DeviceTelemetry}
     */
    DeviceTelemetry addTelemetry(String deviceId, AddDeviceTelemetryRequest request);

    /**
     * 删除设备遥测数据
     *
     * @param deviceId    设备ID {@link DeviceTelemetry#getDeviceId()}
     * @param telemetryId 遥测数据ID {@link DeviceTelemetry#getId()}
     * @return 已删除的遥测数据 {@link DeviceTelemetry}
     */
    DeviceTelemetry deleteTelemetry(String deviceId, String telemetryId);

    /**
     * 检查遥测数据ID作为图表字段是否被允许
     *
     * @param deviceId    设备ID，图表目标ID {@link  DeviceTelemetry#getDeviceId()}
     * @param telemetryId 遥测数据ID，字段ID {@link DeviceTelemetry#getId()}
     * @return 如果允许则返回{@link DeviceTelemetry}，否者抛出{@link ApiException}异常
     */
    DeviceTelemetry checkTelemetryIdChartField(String deviceId, String telemetryId);
}

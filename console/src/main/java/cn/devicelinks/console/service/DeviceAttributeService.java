package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;

import java.util.List;

/**
 * 设备属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeService extends BaseService<DeviceAttribute, String> {
    /**
     * 分页获取设备属性
     *
     * @param searchFieldQuery 检索字段查询对象 {@link SearchFieldQuery}
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @return 设备属性分页对象
     */
    PageResult<DeviceAttributeDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery);

    /**
     * 获取设备属性最新值
     *
     * @param deviceId            设备ID {@link DeviceAttribute#getDeviceId()}
     * @param moduleId            属性所属功能模块ID {@link DeviceAttribute#getModuleId()}
     * @param attributeName       属性名称 {@link Attribute#getName()}
     * @param attributeIdentifier 属性标识符 {@link Attribute#getIdentifier()}
     * @return {@link DeviceAttributeLatestDTO}
     */
    List<DeviceAttributeLatestDTO> getLatestAttribute(String deviceId, String moduleId, String attributeName, String attributeIdentifier);

    /**
     * 提取未知属性
     *
     * @param reportAttributeId 属性ID {@link DeviceAttribute#getId()}
     * @param request           提取未知属性的请求参数
     * @return 存储后的已知属性 {@link Attribute}
     */
    Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownDeviceAttributeRequest request);

    /**
     * 检查设备属性ID作为图表字段是否被允许
     *
     * @param deviceId          设备ID，图表目标ID {@link  DeviceAttribute#getDeviceId()}
     * @param deviceAttributeId 设备属性ID，字段ID {@link DeviceAttribute#getId()}
     * @return 如果允许则返回{@link DeviceAttribute}，否者抛出{@link ApiException}异常
     */
    DeviceAttribute checkAttributeIdChartField(String deviceId, String deviceAttributeId);
}

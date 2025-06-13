package cn.devicelinks.service.device;

import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddDeviceAttributeRequest;
import cn.devicelinks.api.model.request.ExtractUnknownDeviceAttributeRequest;
import cn.devicelinks.api.model.request.UpdateDeviceAttributeRequest;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.jdbc.core.page.PageResult;

import java.time.LocalDateTime;
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
     * @param reportAttributeId  属性ID {@link DeviceAttribute#getId()}
     * @param request            提取未知属性的请求参数
     * @param authorizedAddition 当前用户认证附加信息
     * @return 存储后的已知属性 {@link Attribute}
     */
    Attribute extractUnknownAttribute(String reportAttributeId, ExtractUnknownDeviceAttributeRequest request, UserAuthorizedAddition authorizedAddition);

    /**
     * 检查设备属性ID作为图表字段是否被允许
     *
     * @param deviceId          设备ID，图表目标ID {@link  DeviceAttribute#getDeviceId()}
     * @param deviceAttributeId 设备属性ID，字段ID {@link DeviceAttribute#getId()}
     * @return 如果允许则返回{@link DeviceAttribute}，否者抛出{@link ApiException}异常
     */
    DeviceAttribute checkAttributeIdChartField(String deviceId, String deviceAttributeId);

    /**
     * 新增或更新设备属性列表
     *
     * @param deviceAttributeList 设备属性列表{@link DeviceAttribute}
     */
    void saveOrUpdateDeviceAttribute(List<DeviceAttribute> deviceAttributeList);

    /**
     * 根据标识符查询设备属性
     *
     * @param deviceId   设备ID {@link DeviceAttribute#getDeviceId()}
     * @param moduleId   属性所属的功能模块 {@link DeviceAttribute#getModuleId()}
     * @param identifier 属性标识符 {@link DeviceAttribute#getIdentifier()}
     * @return {@link DeviceAttribute}
     */
    DeviceAttribute selectByIdentifier(String deviceId, String moduleId, String identifier);

    /**
     * 查询设备上报的属性值列表
     *
     * @param deviceId    设备ID {@link DeviceAttribute#getDeviceId()}
     * @param identifiers 属性标识符列表，如果不传递则查询全部的属性 {@link DeviceAttribute#getIdentifier()}
     * @return 设备属性列表 {@link DeviceAttribute}
     */
    List<DeviceAttribute> selectDeviceAttributes(String deviceId, String[] identifiers);

    /**
     * 订阅指定设备在指定时间后的属性更新信息
     *
     * @param deviceId      设备ID {@link DeviceAttribute#getDeviceId()}
     * @param subscribeTime 订阅时间
     * @return 设备属性列表 {@link DeviceAttribute}
     */
    List<DeviceAttribute> subscribeAttributesUpdate(String deviceId, LocalDateTime subscribeTime);

    /**
     * 新增属性
     *
     * @param device  设备实例 {@link Device}
     * @param request 添加设备属性请求参数 {@link AddDeviceAttributeRequest}
     * @return 添加后的属性实例 {@link DeviceAttribute}
     */
    DeviceAttribute addDeviceAttribute(Device device, AddDeviceAttributeRequest request);

    /**
     * 更新设备属性
     *
     * @param deviceAttributeId 设备属性ID {@link DeviceAttribute#getId()}
     * @param request           更新属性请求参数
     * @return 更新后的属性实例 {@link DeviceAttribute}
     */
    DeviceAttribute updateDeviceAttribute(String deviceAttributeId, UpdateDeviceAttributeRequest request);
}

package cn.devicelinks.service.device;

import cn.devicelinks.api.model.dto.DeviceAttributeDesiredDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddDeviceDesiredAttributeRequest;
import cn.devicelinks.api.model.request.ExtractUnknownDesiredAttributeRequest;
import cn.devicelinks.api.model.request.UpdateDeviceDesiredAttributeRequest;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.AttributeScope;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.jdbc.core.page.PageResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备期望属性业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeDesiredService extends BaseService<DeviceAttributeDesired, String> {
    /**
     * 查询功能模块下指定标识符的期望属性
     *
     * @param deviceId   设备ID {@link DeviceAttributeDesired#getDeviceId()}
     * @param moduleId   功能模块ID {@link DeviceAttributeDesired#getModuleId()}
     * @param identifier 期望属性标识符 {@link DeviceAttributeDesired#getIdentifier()}
     * @return {@link DeviceAttributeDesired}
     */
    DeviceAttributeDesired selectByIdentifier(String deviceId, String moduleId, String identifier);

    /**
     * 分页获取设备期望属性
     *
     * @param searchFieldQuery 检索字段查询对象 {@link SearchFieldQuery}
     * @param paginationQuery  分页查询对象 {@link PaginationQuery}
     * @return 设备期望属性分页对象
     */
    PageResult<DeviceAttributeDesiredDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery);

    /**
     * 新增期望属性
     *
     * @param device   设备实例 {@link Device}
     * @param moduleId 功能模块ID {@link DeviceAttributeDesired#getModuleId()}
     * @param request  添加设备期望属性请求参数 {@link AddDeviceDesiredAttributeRequest}
     * @return 添加后的期望属性实例 {@link DeviceAttributeDesired}
     */
    DeviceAttributeDesired addDesiredAttribute(Device device, String moduleId, AddDeviceDesiredAttributeRequest request);

    /**
     * 更新期望属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @param request            更新期望属性请求参数
     * @return 更新后的期望属性实例 {@link DeviceAttributeDesired}
     */
    DeviceAttributeDesired updateDesiredAttribute(String desiredAttributeId, UpdateDeviceDesiredAttributeRequest request);

    /**
     * 提取未知期望属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @param request            提取未知期望属性的请求参数
     * @return 存储后的已知属性 {@link Attribute}
     */
    Attribute extractUnknownAttribute(String desiredAttributeId, ExtractUnknownDesiredAttributeRequest request, UserAuthorizedAddition authorizedAddition);

    /**
     * 删除期望属性
     *
     * @param desiredAttributeId 期望属性ID {@link DeviceAttributeDesired#getId()}
     * @return 期望属性 {@link DeviceAttributeDesired}
     */
    DeviceAttributeDesired deleteDesiredAttribute(String desiredAttributeId);

    /**
     * 查询设备晚于查询时间更新的属性期望值列表
     *
     * @param deviceId       设备ID {@link DeviceAttributeDesired#getDeviceId()}
     * @param attributeScope 属性范围 {@link Attribute#getScope()}
     * @param queryTime      查询时间
     * @return 属性期望值列表
     */
    List<DeviceAttributeDesired> selectNewlyDesiredAttributes(String deviceId, AttributeScope attributeScope, LocalDateTime queryTime);
}

package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceDesiredAttributeRequest;
import cn.devicelinks.console.web.request.ExtractUnknownDesiredAttributeRequest;
import cn.devicelinks.console.web.request.UpdateDeviceDesiredAttributeRequest;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDesiredDTO;

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
     * @param deviceId 设备ID {@link DeviceAttributeDesired#getDeviceId()}
     * @param moduleId 功能模块ID {@link DeviceAttributeDesired#getModuleId()}
     * @param request  添加设备期望属性请求参数 {@link AddDeviceDesiredAttributeRequest}
     * @return 添加后的期望属性实例 {@link DeviceAttributeDesired}
     */
    DeviceAttributeDesired addDesiredAttribute(String deviceId, String moduleId, AddDeviceDesiredAttributeRequest request);

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
    Attribute extractUnknownAttribute(String desiredAttributeId, ExtractUnknownDesiredAttributeRequest request);
}

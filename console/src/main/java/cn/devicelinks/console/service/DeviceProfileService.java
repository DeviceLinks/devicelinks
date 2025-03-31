package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceProfileRequest;
import cn.devicelinks.console.web.request.BatchSetDeviceProfileRequest;
import cn.devicelinks.console.web.request.UpdateDeviceProfileBasicInfoRequest;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.common.pojos.DeviceProfileLogAddition;
import cn.devicelinks.framework.common.pojos.DeviceProfileProvisionAddition;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.page.PageResult;

import java.util.Map;

/**
 * 设备配置文件业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceProfileService extends BaseService<DeviceProfile, String> {
    /**
     * 分页查询设备配置文件列表
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 检索字段查询参数 {@link SearchFieldQuery}
     * @return 设备配置文件列表 {@link DeviceProfile}
     */
    PageResult<DeviceProfile> getDeviceProfileListByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery);

    /**
     * 添加设备配置文件
     *
     * @param request 添加设备配置文件请求参数实体 {@link AddDeviceProfileRequest}
     * @return 已添加的设备配置文件 {@link DeviceProfile}
     */
    DeviceProfile addDeviceProfile(AddDeviceProfileRequest request);

    /**
     * 更新设备配置文件
     *
     * @param deviceProfile 待更新的设备配置文件对象实例
     * @return 更新后的设备配置文件对象实例
     */
    DeviceProfile updateDeviceProfile(DeviceProfile deviceProfile);

    /**
     * 更新基础信息
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   更新设备配置文件基本信息请求实体 {@link UpdateDeviceProfileBasicInfoRequest}
     */
    DeviceProfile updateDeviceProfileBasicInfo(String profileId, UpdateDeviceProfileBasicInfoRequest request);

    /**
     * 更新扩展配置
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param extension 扩展配置
     * @return 更新后的扩展
     */
    Map<String, Object> updateDeviceProfileExtension(String profileId, Map<String, Object> extension);

    /**
     * 更新日志附加配置
     *
     * @param profileId   设备配置文件ID {@link DeviceProfile#getId()}
     * @param logAddition 日志附加配置
     * @return 更新后的日志附加配置
     */
    DeviceProfileLogAddition updateDeviceProfileLogAddition(String profileId, DeviceProfileLogAddition logAddition);

    /**
     * 更新预配置附加信息
     *
     * @param profileId         设备配置文件ID {@link DeviceProfile#getId()}
     * @param provisionAddition 预配置 {@link DeviceProfileProvisionAddition}
     * @return 设备配置文件预配置
     */
    DeviceProfileProvisionAddition updateDeviceProfileProvisionAddition(String profileId, DeviceProfileProvisionAddition provisionAddition);

    /**
     * 删除设备配置文件
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @return 已删除的设备配置文件 {@link DeviceProfile}
     */
    DeviceProfile deleteDeviceProfile(String profileId);

    /**
     * 将设备配置文件匹配设置给设备
     *
     * @param profileId 设备配置文件ID {@link DeviceProfile#getId()}
     * @param request   匹配设置请求实体参数
     */
    void batchSet(String profileId, BatchSetDeviceProfileRequest request);
}

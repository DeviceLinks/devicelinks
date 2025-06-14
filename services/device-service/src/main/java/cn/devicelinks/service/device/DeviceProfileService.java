package cn.devicelinks.service.device;

import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.api.model.request.AddDeviceProfileRequest;
import cn.devicelinks.api.model.request.BatchSetDeviceProfileRequest;
import cn.devicelinks.api.model.request.UpdateDeviceProfileBasicInfoRequest;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.entity.DeviceProfileLogAddition;
import cn.devicelinks.entity.DeviceProfileProvisionAddition;
import cn.devicelinks.jdbc.BaseService;
import cn.devicelinks.jdbc.core.page.PageResult;

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
     * 根据预配置Key查询配置文件
     *
     * @param provisionKey {@link DeviceProfileProvisionAddition#getProvisionDeviceKey()}
     * @return {@link DeviceProfile}
     */
    DeviceProfile getByProvisionKey(String provisionKey);

    /**
     * 添加设备配置文件
     *
     * @param request            添加设备配置文件请求参数实体 {@link AddDeviceProfileRequest}
     * @param authorizedAddition 当前用户认证附加信息
     * @return 已添加的设备配置文件 {@link DeviceProfile}
     */
    DeviceProfile addDeviceProfile(AddDeviceProfileRequest request, UserAuthorizedAddition authorizedAddition);

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

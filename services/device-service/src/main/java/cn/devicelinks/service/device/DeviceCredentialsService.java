package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
import cn.devicelinks.framework.common.pojos.DeviceCredentialsAddition;
import cn.devicelinks.framework.jdbc.BaseService;

/**
 * 设备鉴权业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceCredentialsService extends BaseService<DeviceCredentials, String> {
    /**
     * 查询设备凭证
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return {@link DeviceCredentials}
     */
    DeviceCredentials selectByDeviceId(String deviceId);

    /**
     * 通过AccessToken查询鉴权信息
     *
     * @param token 请求令牌（静态令牌、动态令牌）
     * @return 鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials selectByToken(String token);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceCredentialsAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials selectByClientId(String clientId);

    /**
     * 保存设备鉴权信息
     *
     * @param deviceId            设备ID {@link DeviceCredentials#getDeviceId()}
     * @param credentialsType     鉴权方式 {@link DeviceCredentialsType}
     * @param credentialsAddition 鉴权附加数据 {@link DeviceCredentialsAddition}
     * @return 返回保存的鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials addCredentials(String deviceId, DeviceCredentialsType credentialsType, DeviceCredentialsAddition credentialsAddition);

    /**
     * 更新设备鉴权信息
     *
     * @param deviceId            设备ID {@link Device#getId()}
     * @param credentialsType     鉴权方式 {@link DeviceCredentialsType}
     * @param credentialsAddition 鉴权附加数据 {@link DeviceCredentialsAddition}
     * @return 返回保存的鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials updateCredentials(String deviceId, DeviceCredentialsType credentialsType, DeviceCredentialsAddition credentialsAddition);
}

package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.jdbc.BaseService;

/**
 * 设备鉴权业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAuthenticationService extends BaseService<DeviceAuthentication, String> {
    /**
     * 查询设备凭证
     *
     * @param deviceId 设备ID {@link Device#getId()}
     * @return {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByDeviceId(String deviceId);

    /**
     * 通过AccessToken查询鉴权信息
     *
     * @param staticToken 静态令牌
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByStaticToken(String staticToken);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceAuthenticationAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByClientId(String clientId);

    /**
     * 保存设备鉴权信息
     *
     * @param deviceId               设备ID {@link DeviceAuthentication#getDeviceId()}
     * @param authenticationMethod   鉴权方式 {@link DeviceAuthenticationMethod}
     * @param authenticationAddition 鉴权附加数据 {@link DeviceAuthenticationAddition}
     * @return 返回保存的鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication addAuthentication(String deviceId, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition);

    /**
     * 更新设备鉴权信息
     *
     * @param deviceId               设备ID {@link Device#getId()}
     * @param authenticationMethod   鉴权方式 {@link DeviceAuthenticationMethod}
     * @param authenticationAddition 鉴权附加数据 {@link DeviceAuthenticationAddition}
     * @return 返回保存的鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication updateAuthentication(String deviceId, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition);
}

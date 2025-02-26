package cn.devicelinks.console.service;

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
     * 通过AccessToken查询鉴权信息
     *
     * @param accessToken 请求令牌
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByAccessToken(String accessToken);

    /**
     * 通过clientId查询鉴权信息
     *
     * @param clientId 客户端ID {@link DeviceAuthenticationAddition.MqttBasic#getClientId()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByClientId(String clientId);

    /**
     * 通过deviceKey查询鉴权信息
     *
     * @param deviceKey    设备Key {@link DeviceAuthenticationAddition.DeviceCredential#getDeviceKey()}
     * @return 鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication selectByDeviceKey(String deviceKey);

    /**
     * 保存设备鉴权信息
     *
     * @param device                 设备信息 {@link Device}
     * @param authenticationMethod   鉴权方式 {@link DeviceAuthenticationMethod}
     * @param authenticationAddition 鉴权附加数据 {@link DeviceAuthenticationAddition}
     * @return 返回保存的鉴权信息 {@link DeviceAuthentication}
     */
    DeviceAuthentication saveAuthentication(Device device, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition);
}

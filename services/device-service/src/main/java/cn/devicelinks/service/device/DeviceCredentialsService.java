package cn.devicelinks.service.device;

import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.entity.Device;
import cn.devicelinks.entity.DeviceCredentials;
import cn.devicelinks.entity.DeviceCredentialsAddition;
import cn.devicelinks.jdbc.BaseService;

import java.time.LocalDateTime;

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
     * @param aesSecretKeySet     AES加密Key集合
     * @return 返回保存的鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials addCredentials(String deviceId,
                                     DeviceCredentialsType credentialsType,
                                     LocalDateTime expirationTime,
                                     DeviceCredentialsAddition credentialsAddition,
                                     AesSecretKeySet aesSecretKeySet);

    /**
     * 添加动态令牌
     * <p>
     * 会将设备之前所有有效的动态令牌设置为过期
     *
     * @param deviceId        设备ID {@link DeviceCredentials#getDeviceId()}
     * @param dynamicToken    动态令牌
     * @param expirationTime  过期时间
     * @param aesSecretKeySet AES加密Key集合
     * @return 添加的动态令牌凭证 {@link DeviceCredentials}
     */
    DeviceCredentials addDynamicToken(String deviceId,
                                      String dynamicToken,
                                      LocalDateTime expirationTime,
                                      AesSecretKeySet aesSecretKeySet);

    /**
     * 更新设备鉴权信息
     *
     * @param deviceId            设备ID {@link Device#getId()}
     * @param credentialsType     鉴权方式 {@link DeviceCredentialsType}
     * @param credentialsAddition 鉴权附加数据 {@link DeviceCredentialsAddition}
     * @param aesSecretKeySet     AES加密Key集合
     * @return 返回保存的鉴权信息 {@link DeviceCredentials}
     */
    DeviceCredentials updateCredentials(String deviceId,
                                        DeviceCredentialsType credentialsType,
                                        DeviceCredentialsAddition credentialsAddition,
                                        AesSecretKeySet aesSecretKeySet);
}

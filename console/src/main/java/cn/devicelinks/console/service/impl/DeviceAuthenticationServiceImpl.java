package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceAuthenticationService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
import cn.devicelinks.framework.common.utils.X509Utils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAuthenticationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * 设备鉴权业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceAuthenticationServiceImpl extends BaseServiceImpl<DeviceAuthentication, String, DeviceAuthenticationRepository> implements DeviceAuthenticationService {

    public DeviceAuthenticationServiceImpl(DeviceAuthenticationRepository repository) {
        super(repository);
    }

    @Override
    public DeviceAuthentication selectByDeviceId(String deviceId) {
        return this.repository.selectByDeviceId(deviceId);
    }

    @Override
    public DeviceAuthentication selectByAccessToken(String accessToken) {
        return this.repository.selectByAccessToken(accessToken);
    }

    @Override
    public DeviceAuthentication selectByClientId(String clientId) {
        return this.repository.selectByClientId(clientId);
    }

    @Override
    public DeviceAuthentication selectByDeviceKey(String deviceKey) {
        return this.repository.selectByDeviceKey(deviceKey);
    }

    @Override
    public DeviceAuthentication addAuthentication(String deviceId,
                                                  DeviceAuthenticationMethod authenticationMethod,
                                                  DeviceAuthenticationAddition authenticationAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, authenticationMethod, authenticationAddition, false);
        // @formatter:off
        DeviceAuthentication authentication = new DeviceAuthentication()
                .setDeviceId(deviceId)
                .setAuthenticationMethod(authenticationMethod)
                .setAddition(authenticationAddition)
                .setCreateTime(LocalDateTime.now());
        // @formatter:on
        this.repository.insert(authentication);
        return authentication;
    }

    @Override
    public DeviceAuthentication updateAuthentication(String deviceId,
                                                     DeviceAuthenticationMethod authenticationMethod,
                                                     DeviceAuthenticationAddition authenticationAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, authenticationMethod, authenticationAddition, true);

        DeviceAuthentication deviceAuthentication = selectByDeviceId(deviceId);
        if (deviceAuthentication == null || deviceAuthentication.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_AUTHENTICATION_NOT_EXISTS, deviceId);
        }
        deviceAuthentication.setAuthenticationMethod(authenticationMethod).setAddition(authenticationAddition);
        this.repository.update(deviceAuthentication);
        return deviceAuthentication;
    }


    private void validateAuthentication(String deviceId,
                                        DeviceAuthenticationMethod authenticationMethod,
                                        DeviceAuthenticationAddition authenticationAddition,
                                        boolean isUpdate) {
        switch (authenticationMethod) {
            case AccessToken:
                if (ObjectUtils.isEmpty(authenticationAddition.getAccessToken())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_ACCESS_TOKEN, authenticationAddition.getAccessToken());
                }
                DeviceAuthentication accessTokenAuthentication = this.selectByAccessToken(authenticationAddition.getAccessToken());
                if ((isUpdate && accessTokenAuthentication != null && !accessTokenAuthentication.getDeviceId().equals(deviceId)) ||
                        (!isUpdate && accessTokenAuthentication != null)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_ACCESS_TOKEN_ALREADY_EXISTS, authenticationAddition.getAccessToken());
                }
                break;
            case MqttBasic:
                // Validate MQTT Basic authentication
                DeviceAuthenticationAddition.MqttBasic mqttBasic = authenticationAddition.getMqttBasic();
                if (mqttBasic == null || ObjectUtils.isEmpty(mqttBasic.getUsername()) || ObjectUtils.isEmpty(mqttBasic.getPassword())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_MQTT_BASIC_AUTH, mqttBasic);
                }
                DeviceAuthentication mqttBasicAuthentication = this.selectByClientId(mqttBasic.getClientId());
                if ((isUpdate && mqttBasicAuthentication != null && !mqttBasicAuthentication.getDeviceId().equals(deviceId)) ||
                        (!isUpdate && mqttBasicAuthentication != null)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS, mqttBasic.getClientId());
                }
                break;
            case DeviceCredential:
                DeviceAuthenticationAddition.DeviceCredential deviceCredential = authenticationAddition.getDeviceCredential();
                if (deviceCredential == null || ObjectUtils.isEmpty(deviceCredential.getDeviceKey()) || ObjectUtils.isEmpty(deviceCredential.getDeviceSecret())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_CREDENTIAL, deviceCredential);
                }
                DeviceAuthentication deviceCredentialAuthentication = this.selectByDeviceKey(deviceCredential.getDeviceKey());
                if ((isUpdate && deviceCredentialAuthentication != null && !deviceCredentialAuthentication.getDeviceId().equals(deviceId)) ||
                        (!isUpdate && deviceCredentialAuthentication != null)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_CREDENTIAL_KEY_ALREADY_EXISTS, deviceCredential);
                }
                break;
            case X509:
                if (ObjectUtils.isEmpty(authenticationAddition.getX509Pem()) || !X509Utils.isValidX509Pem(authenticationAddition.getX509Pem())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_X509_PEM, authenticationAddition.getX509Pem());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported authentication method");
        }
    }
}

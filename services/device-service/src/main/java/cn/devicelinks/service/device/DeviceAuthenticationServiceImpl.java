package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
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
    public DeviceAuthentication selectByStaticToken(String staticToken) {
        return this.repository.selectByStaticToken(staticToken);
    }

    @Override
    public DeviceAuthentication selectByClientId(String clientId) {
        return this.repository.selectByClientId(clientId);
    }

    @Override
    public DeviceAuthentication addAuthentication(String deviceId,
                                                  DeviceAuthenticationMethod authenticationMethod,
                                                  DeviceAuthenticationAddition authenticationAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, authenticationMethod, authenticationAddition, false);
        // set generate time
        if (DeviceAuthenticationMethod.DynamicToken == authenticationMethod) {
            authenticationAddition.getDynamicToken().setSecretGenerateTime(LocalDateTime.now());
        }
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
        // set generate time
        if (DeviceAuthenticationMethod.DynamicToken == authenticationMethod) {
            authenticationAddition.getDynamicToken().setSecretGenerateTime(LocalDateTime.now());
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
            case StaticToken:
                if (ObjectUtils.isEmpty(authenticationAddition.getStaticToken())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_STATIC_TOKEN);
                }
                DeviceAuthentication staticTokenAuthentication = this.selectByStaticToken(authenticationAddition.getStaticToken());
                if ((isUpdate && staticTokenAuthentication != null && !staticTokenAuthentication.getDeviceId().equals(deviceId)) ||
                        (!isUpdate && staticTokenAuthentication != null)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_STATIC_TOKEN_ALREADY_EXISTS, authenticationAddition.getStaticToken());
                }
                break;
            case DynamicToken:
                if (authenticationAddition.getDynamicToken() == null || ObjectUtils.isEmpty(authenticationAddition.getDynamicToken().getDeviceSecret())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_DYNAMIC_TOKEN_SECRET);
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

package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceCredentials;
import cn.devicelinks.framework.common.pojos.DeviceCredentialsAddition;
import cn.devicelinks.framework.common.utils.X509Utils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceCredentialsRepository;
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
public class DeviceCredentialsServiceImpl extends BaseServiceImpl<DeviceCredentials, String, DeviceCredentialsRepository> implements DeviceCredentialsService {

    public DeviceCredentialsServiceImpl(DeviceCredentialsRepository repository) {
        super(repository);
    }

    @Override
    public DeviceCredentials selectByDeviceId(String deviceId) {
        return this.repository.selectByDeviceId(deviceId);
    }

    @Override
    public DeviceCredentials selectByStaticToken(String staticToken) {
        return this.repository.selectByStaticToken(staticToken);
    }

    @Override
    public DeviceCredentials selectByClientId(String clientId) {
        return this.repository.selectByClientId(clientId);
    }

    @Override
    public DeviceCredentials addCredentials(String deviceId,
                                            DeviceCredentialsType deviceCredentialsType,
                                            DeviceCredentialsAddition authenticationAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, deviceCredentialsType, authenticationAddition, false);
        // set generate time
        if (DeviceCredentialsType.DynamicToken == deviceCredentialsType) {
            authenticationAddition.getDynamicToken().setSecretGenerateTime(LocalDateTime.now());
        }
        // @formatter:off
        DeviceCredentials authentication = new DeviceCredentials()
                .setDeviceId(deviceId)
                .setCredentialsType(deviceCredentialsType)
                .setAddition(authenticationAddition)
                .setCreateTime(LocalDateTime.now());
        // @formatter:on
        this.repository.insert(authentication);
        return authentication;
    }

    @Override
    public DeviceCredentials updateCredentials(String deviceId,
                                               DeviceCredentialsType deviceCredentialsType,
                                               DeviceCredentialsAddition authenticationAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, deviceCredentialsType, authenticationAddition, true);

        DeviceCredentials deviceAuthentication = selectByDeviceId(deviceId);
        if (deviceAuthentication == null || deviceAuthentication.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_AUTHENTICATION_NOT_EXISTS, deviceId);
        }
        // set generate time
        if (DeviceCredentialsType.DynamicToken == deviceCredentialsType) {
            authenticationAddition.getDynamicToken().setSecretGenerateTime(LocalDateTime.now());
        }
        deviceAuthentication.setCredentialsType(deviceCredentialsType).setAddition(authenticationAddition);
        this.repository.update(deviceAuthentication);
        return deviceAuthentication;
    }


    private void validateAuthentication(String deviceId,
                                        DeviceCredentialsType deviceCredentialsType,
                                        DeviceCredentialsAddition authenticationAddition,
                                        boolean isUpdate) {
        switch (deviceCredentialsType) {
            case StaticToken:
                if (ObjectUtils.isEmpty(authenticationAddition.getStaticToken())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_STATIC_TOKEN);
                }
                DeviceCredentials staticTokenAuthentication = this.selectByStaticToken(authenticationAddition.getStaticToken());
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
                DeviceCredentialsAddition.MqttBasic mqttBasic = authenticationAddition.getMqttBasic();
                if (mqttBasic == null || ObjectUtils.isEmpty(mqttBasic.getUsername()) || ObjectUtils.isEmpty(mqttBasic.getPassword())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_MQTT_BASIC_AUTH, mqttBasic);
                }
                DeviceCredentials mqttBasicAuthentication = this.selectByClientId(mqttBasic.getClientId());
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

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
import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceCredentials.DEVICE_CREDENTIALS;

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
    public DeviceCredentials selectByToken(String staticToken) {
        return this.repository.selectByToken(staticToken);
    }

    @Override
    public DeviceCredentials selectByClientId(String clientId) {
        return this.repository.selectByClientId(clientId);
    }

    @Override
    public DeviceCredentials addCredentials(String deviceId,
                                            DeviceCredentialsType deviceCredentialsType,
                                            LocalDateTime expirationTime,
                                            DeviceCredentialsAddition credentialsAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, deviceCredentialsType, credentialsAddition, false);
        // @formatter:off
        DeviceCredentials credentials = new DeviceCredentials()
                .setDeviceId(deviceId)
                .setCredentialsType(deviceCredentialsType)
                .setAddition(credentialsAddition)
                .setExpirationTime(expirationTime)
                .setCreateTime(LocalDateTime.now());
        // @formatter:on
        this.repository.insert(credentials);
        return credentials;
    }

    @Override
    public DeviceCredentials addDynamicToken(String deviceId, String dynamicToken, LocalDateTime expirationTime) {
        // @formatter:off
        LocalDateTime currentTime = LocalDateTime.now();
        this.repository.update(List.of(DEVICE_CREDENTIALS.EXPIRATION_TIME.set(currentTime)),
                DEVICE_CREDENTIALS.DEVICE_ID.eq(deviceId),
                DEVICE_CREDENTIALS.DELETED.eq(Boolean.FALSE),
                DEVICE_CREDENTIALS.EXPIRATION_TIME.gt(currentTime));
        // @formatter:off
        return this.addCredentials(deviceId, DeviceCredentialsType.DynamicToken, expirationTime, new DeviceCredentialsAddition().setToken(dynamicToken));
    }

    @Override
    public DeviceCredentials updateCredentials(String deviceId,
                                               DeviceCredentialsType deviceCredentialsType,
                                               DeviceCredentialsAddition credentialsAddition) {
        // validate authentication
        this.validateAuthentication(deviceId, deviceCredentialsType, credentialsAddition, true);

        DeviceCredentials deviceAuthentication = selectByDeviceId(deviceId);
        if (deviceAuthentication == null || deviceAuthentication.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_AUTHENTICATION_NOT_EXISTS, deviceId);
        }
        deviceAuthentication.setCredentialsType(deviceCredentialsType).setAddition(credentialsAddition);
        this.repository.update(deviceAuthentication);
        return deviceAuthentication;
    }


    private void validateAuthentication(String deviceId,
                                        DeviceCredentialsType deviceCredentialsType,
                                        DeviceCredentialsAddition credentialsAddition,
                                        boolean isUpdate) {
        switch (deviceCredentialsType) {
            case StaticToken:
                if (ObjectUtils.isEmpty(credentialsAddition.getToken())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_STATIC_TOKEN);
                }
                DeviceCredentials staticTokenAuthentication = this.selectByToken(credentialsAddition.getToken());
                if ((isUpdate && staticTokenAuthentication != null && !staticTokenAuthentication.getDeviceId().equals(deviceId)) ||
                        (!isUpdate && staticTokenAuthentication != null)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_STATIC_TOKEN_ALREADY_EXISTS, credentialsAddition.getToken());
                }
                break;
            case MqttBasic:
                // Validate MQTT Basic authentication
                DeviceCredentialsAddition.MqttBasic mqttBasic = credentialsAddition.getMqttBasic();
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
                if (ObjectUtils.isEmpty(credentialsAddition.getX509Pem()) || !X509Utils.isValidX509Pem(credentialsAddition.getX509Pem())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_X509_PEM, credentialsAddition.getX509Pem());
                }
                break;
        }
    }
}

package cn.devicelinks.service.device;

import cn.devicelinks.api.device.center.model.response.DecryptTokenResponse;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.secret.AesProperties;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.common.utils.AesEncryptor;
import cn.devicelinks.common.utils.DigestUtils;
import cn.devicelinks.common.utils.X509Utils;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.DeviceCredentials;
import cn.devicelinks.entity.DeviceCredentialsAddition;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.repository.DeviceCredentialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceCredentials.DEVICE_CREDENTIALS;

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
    public DeviceCredentials selectAndDecryptByDeviceId(String deviceId, AesSecretKeySet aesSecretKeySet) {
        DeviceCredentials credentials = this.selectByDeviceId(deviceId);
        if (credentials == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_CREDENTIALS_NO_VALID_EXISTS, deviceId);
        }
        this.decrypt(credentials.getCredentialsType(), credentials.getAddition(), aesSecretKeySet);
        return credentials;
    }

    @Override
    public DeviceCredentials selectByToken(String staticToken) {
        return this.repository.selectByToken(staticToken);
    }

    @Override
    public DeviceCredentials selectByTokenHash(String tokenHash) {
        return repository.selectByTokenHash(tokenHash);
    }

    @Override
    public DecryptTokenResponse decryptToken(String tokenHash, AesSecretKeySet aesSecretKeySet) {
        DeviceCredentials deviceCredentials = repository.selectByTokenHash(tokenHash);
        if (deviceCredentials == null || deviceCredentials.isDeleted()) {
            throw new ApiException(StatusCodeConstants.TOKEN_INVALID);
        }
        AesProperties aesProperties = deviceCredentials.getAddition().getAes();
        AesSecretKeySet.AesSecretKey aesSecretKey = aesSecretKeySet.getAesSecretKey(aesProperties.getKeyId());
        String decryptedToken = AesEncryptor.init(aesSecretKey, aesProperties.getIv()).decrypt(deviceCredentials.getAddition().getToken());

        // @formatter:off
        return new DecryptTokenResponse()
                .setToken(decryptedToken)
                .setCredentialsType(deviceCredentials.getCredentialsType())
                .setExpirationTime(deviceCredentials.getExpirationTime())
                .setDeviceId(deviceCredentials.getDeviceId());
        // @formatter:on
    }

    @Override
    public DeviceCredentials selectByClientId(String clientId) {
        return this.repository.selectByClientId(clientId);
    }

    @Override
    public DeviceCredentials addCredentials(String deviceId,
                                            DeviceCredentialsType deviceCredentialsType,
                                            LocalDateTime expirationTime,
                                            DeviceCredentialsAddition credentialsAddition,
                                            AesSecretKeySet aesSecretKeySet) {
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

        this.encrypt(deviceCredentialsType, credentialsAddition, aesSecretKeySet);

        this.repository.insert(credentials);
        return credentials;
    }

    @Override
    public DeviceCredentials addDynamicToken(String deviceId,
                                             String dynamicToken,
                                             LocalDateTime expirationTime,
                                             AesSecretKeySet aesSecretKeySet) {
        // @formatter:off
        LocalDateTime currentTime = LocalDateTime.now();
        this.repository.update(List.of(DEVICE_CREDENTIALS.EXPIRATION_TIME.set(currentTime)),
                DEVICE_CREDENTIALS.DEVICE_ID.eq(deviceId),
                DEVICE_CREDENTIALS.DELETED.eq(Boolean.FALSE),
                DEVICE_CREDENTIALS.EXPIRATION_TIME.gt(currentTime));
        // @formatter:on
        return this.addCredentials(deviceId, DeviceCredentialsType.DynamicToken, expirationTime,
                new DeviceCredentialsAddition().setToken(dynamicToken), aesSecretKeySet);
    }

    @Override
    public DeviceCredentials updateCredentials(String deviceId,
                                               DeviceCredentialsType deviceCredentialsType,
                                               DeviceCredentialsAddition credentialsAddition,
                                               AesSecretKeySet aesSecretKeySet) {
        // validate authentication
        this.validateAuthentication(deviceId, deviceCredentialsType, credentialsAddition, true);

        DeviceCredentials deviceAuthentication = selectByDeviceId(deviceId);
        if (deviceAuthentication == null || deviceAuthentication.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_CREDENTIALS_NO_VALID_EXISTS, deviceId);
        }
        deviceAuthentication.setCredentialsType(deviceCredentialsType).setAddition(credentialsAddition);

        this.encrypt(deviceCredentialsType, credentialsAddition, aesSecretKeySet);

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

    private void encrypt(DeviceCredentialsType deviceCredentialsType, DeviceCredentialsAddition credentialsAddition, AesSecretKeySet aesSecretKeySet) {
        if (DeviceCredentialsType.StaticToken == deviceCredentialsType ||
                DeviceCredentialsType.DynamicToken == deviceCredentialsType ||
                DeviceCredentialsType.MqttBasic == deviceCredentialsType) {

            AesEncryptor encryptor = AesEncryptor.init(aesSecretKeySet);

            AesProperties aesProperties = new AesProperties()
                    .setIv(encryptor.getIv())
                    .setKeyId(encryptor.getAesSecretKey().getId());

            credentialsAddition.setAes(aesProperties);

            if (DeviceCredentialsType.StaticToken == deviceCredentialsType || DeviceCredentialsType.DynamicToken == deviceCredentialsType) {
                // Set the token hash
                credentialsAddition.setTokenHash(DigestUtils.getHexDigest(DigestUtils.SHA256, credentialsAddition.getToken().getBytes(StandardCharsets.UTF_8)));
                // Set the encrypted token
                credentialsAddition.setToken(encryptor.encrypt(credentialsAddition.getToken()));
            } else {
                // Set the encrypted password
                DeviceCredentialsAddition.MqttBasic mqttBasic = credentialsAddition.getMqttBasic();
                mqttBasic.setPassword(encryptor.encrypt(mqttBasic.getPassword()));
            }
        }
    }

    private void decrypt(DeviceCredentialsType deviceCredentialsType, DeviceCredentialsAddition credentialsAddition, AesSecretKeySet aesSecretKeySet) {
        if (DeviceCredentialsType.StaticToken == deviceCredentialsType ||
                DeviceCredentialsType.DynamicToken == deviceCredentialsType ||
                DeviceCredentialsType.MqttBasic == deviceCredentialsType) {
            AesProperties aesProperties = credentialsAddition.getAes();
            if (ObjectUtils.isEmpty(aesProperties.getIv()) || ObjectUtils.isEmpty(aesProperties.getKeyId())) {
                throw new ApiException(StatusCodeConstants.DEVICE_SECRET_KEY_INVALID);
            }

            AesSecretKeySet.AesSecretKey aesSecretKey = aesSecretKeySet.getAesSecretKey(aesProperties.getKeyId());
            AesEncryptor encryptor = AesEncryptor.init(aesSecretKey.getKey(), aesProperties.getIv());

            if (DeviceCredentialsType.StaticToken == deviceCredentialsType || DeviceCredentialsType.DynamicToken == deviceCredentialsType) {
                // Set the decrypted token
                credentialsAddition.setToken(encryptor.decrypt(credentialsAddition.getToken()));
            } else {
                // Set the decrypted password
                DeviceCredentialsAddition.MqttBasic mqttBasic = credentialsAddition.getMqttBasic();
                mqttBasic.setPassword(encryptor.decrypt(mqttBasic.getPassword()));
            }

        }
    }
}

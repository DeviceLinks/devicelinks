package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.converter.DeviceSecretConverter;
import cn.devicelinks.framework.common.DeviceSecretStatus;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.common.secret.DeviceSecretKeySet;
import cn.devicelinks.framework.common.utils.AesEncryptor;
import cn.devicelinks.framework.common.utils.AesUtils;
import cn.devicelinks.framework.common.utils.SecureRandomUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.model.dto.DeviceSecretDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceSecretRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceSecret.DEVICE_SECRET;

/**
 * 设备密钥业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceSecretServiceImpl extends BaseServiceImpl<DeviceSecret, String, DeviceSecretRepository> implements DeviceSecretService {

    private static final int DEVICE_SECRET_LENGTH = 50;

    private static final String DEVICE_SECRET_DEFAULT_VERSION = "v1";

    public DeviceSecretServiceImpl(DeviceSecretRepository repository) {
        super(repository);
    }

    @Override
    public DeviceSecret getDeviceSecret(String deviceId) {
        // @formatter:off
        return this.repository.selectOne(
                DEVICE_SECRET.DEVICE_ID.eq(deviceId),
                DEVICE_SECRET.STATUS.eq(DeviceSecretStatus.Active)
        );
        // @formatter:on
    }

    @Override
    public String decryptSecret(String encryptedSecret, String iv, String secretKeyVersion, DeviceSecretKeySet deviceSecretKeySet) {
        Assert.hasText(encryptedSecret, "The EncryptedSecret must not be null or empty.");
        Assert.hasText(iv, "The IV must not be null or empty.");
        Assert.hasText(iv, "The SecretKeyVersion must not be null or empty.");
        DeviceSecretKeySet.DeviceSecretKey deviceSecretKey = deviceSecretKeySet.getDeviceSecretKey(secretKeyVersion);
        if (ObjectUtils.isEmpty(deviceSecretKeySet) || ObjectUtils.isEmpty(deviceSecretKey)) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_KEY_INVALID);
        }
        return AesEncryptor.init(deviceSecretKey.getKey(), iv).decrypt(encryptedSecret);
    }

    @Override
    public DeviceSecretDTO regenerate(String deviceId, DeviceSecretKeySet deviceSecretKeySet) {
        this.repository.update(List.of(DEVICE_SECRET.STATUS.set(DeviceSecretStatus.Expired)),
                DEVICE_SECRET.DEVICE_ID.eq(deviceId),
                DEVICE_SECRET.STATUS.eq(DeviceSecretStatus.Active));
        return this.saveDeviceSecret(deviceId, deviceSecretKeySet);
    }

    @Override
    public void initializeSecret(String deviceId, DeviceSecretKeySet deviceSecretKeySet) {
        this.saveDeviceSecret(deviceId, deviceSecretKeySet);
    }

    private DeviceSecretDTO saveDeviceSecret(String deviceId, DeviceSecretKeySet deviceSecretKeySet) {
        String iv = AesUtils.generateBase64IV();
        DeviceSecretKeySet.DeviceSecretKey deviceSecretKey = deviceSecretKeySet.getRandomDeviceSecretKey();
        if (deviceSecretKey == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_NOT_HAVE_VERSION_kEY);
        }
        String secret = SecureRandomUtils.generateRandomHex(DEVICE_SECRET_LENGTH);
        String encryptedSecret = AesEncryptor.init(deviceSecretKey.getKey(), iv).encrypt(secret);

        // @formatter:off
        DeviceSecret deviceSecret = new DeviceSecret()
                .setDeviceId(deviceId)
                .setEncryptedSecret(encryptedSecret)
                .setIv(iv)
                .setSecretVersion(DEVICE_SECRET_DEFAULT_VERSION)
                .setSecretKeyVersion(deviceSecretKey.getVersion());
        // @formatter:on

        this.repository.insert(deviceSecret);

        DeviceSecretDTO deviceSecretDTO = DeviceSecretConverter.INSTANCE.fromDeviceSecret(deviceSecret);
        deviceSecretDTO.setSecret(secret);
        return deviceSecretDTO;
    }
}

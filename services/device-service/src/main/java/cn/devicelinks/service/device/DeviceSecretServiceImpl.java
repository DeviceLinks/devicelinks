package cn.devicelinks.service.device;

import cn.devicelinks.api.device.center.model.response.EncryptDeviceSecretResponse;
import cn.devicelinks.api.model.converter.DeviceSecretConverter;
import cn.devicelinks.api.model.dto.DeviceSecretDTO;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.DeviceSecretStatus;
import cn.devicelinks.common.secret.AesProperties;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.common.utils.AesEncryptor;
import cn.devicelinks.common.utils.AesUtils;
import cn.devicelinks.common.utils.SecureRandomUtils;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.DeviceEncryptedSecretAddition;
import cn.devicelinks.entity.DeviceSecret;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.cache.DeviceSecretCacheEvictEvent;
import cn.devicelinks.jdbc.cache.DeviceSecretCacheKey;
import cn.devicelinks.jdbc.repository.DeviceSecretRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceSecret.DEVICE_SECRET;

/**
 * 设备密钥业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceSecretServiceImpl extends CacheBaseServiceImpl<DeviceSecret, String, DeviceSecretRepository, DeviceSecretCacheKey, DeviceSecretCacheEvictEvent>
        implements DeviceSecretService {

    private static final int DEVICE_SECRET_LENGTH = 64;

    private static final String DEVICE_SECRET_DEFAULT_VERSION = "v1";

    public DeviceSecretServiceImpl(DeviceSecretRepository repository) {
        super(repository);
    }

    @Override
    public void handleCacheEvictEvent(DeviceSecretCacheEvictEvent event) {
        DeviceSecret savedDeviceSecret = event.getSavedDeviceSecret();
        if (savedDeviceSecret != null) {
            cache.put(DeviceSecretCacheKey.builder().deviceSecretId(savedDeviceSecret.getId()).build(), savedDeviceSecret);
            cache.put(DeviceSecretCacheKey.builder().deviceId(savedDeviceSecret.getDeviceId()).build(), savedDeviceSecret);
        } else {
            List<DeviceSecretCacheKey> toEvict = new ArrayList<>();
            if (!ObjectUtils.isEmpty(event.getDeviceSecretId())) {
                toEvict.add(DeviceSecretCacheKey.builder().deviceSecretId(event.getDeviceSecretId()).build());
            }
            if (!ObjectUtils.isEmpty(event.getDeviceId())) {
                toEvict.add(DeviceSecretCacheKey.builder().deviceId(event.getDeviceId()).build());
            }
            cache.evict(toEvict);
        }
    }

    @Override
    public DeviceSecret getDeviceSecret(String deviceId) {
        return cache.get(DeviceSecretCacheKey.builder().deviceId(deviceId).build(), () -> {
            // @formatter:off
            return this.repository.selectOne(
                    DEVICE_SECRET.DEVICE_ID.eq(deviceId),
                    DEVICE_SECRET.STATUS.eq(DeviceSecretStatus.Active)
            );
            // @formatter:on
        });
    }

    @Override
    public String decryptSecret(String encryptedSecret, String iv, String secretKeyVersion, AesSecretKeySet deviceSecretKeySet) {
        Assert.hasText(encryptedSecret, "The EncryptedSecret must not be null or empty.");
        Assert.hasText(iv, "The IV must not be null or empty.");
        Assert.hasText(iv, "The SecretKeyVersion must not be null or empty.");
        AesSecretKeySet.AesSecretKey deviceSecretKey = deviceSecretKeySet.getAesSecretKey(secretKeyVersion);
        if (ObjectUtils.isEmpty(deviceSecretKeySet) || ObjectUtils.isEmpty(deviceSecretKey)) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_KEY_INVALID);
        }
        return AesEncryptor.init(deviceSecretKey.getKey(), iv).decrypt(encryptedSecret);
    }

    @Override
    public EncryptDeviceSecretResponse encryptSecret(String secret, AesSecretKeySet aesSecretKeySet) {
        String iv = AesUtils.generateBase64IV();
        AesSecretKeySet.AesSecretKey aesSecretKey = aesSecretKeySet.getRandomAesSecretKey();
        if (aesSecretKey == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_NOT_HAVE_VERSION_kEY);
        }
        String encryptedSecret = AesEncryptor.init(aesSecretKey.getKey(), iv).encrypt(secret);
        return new EncryptDeviceSecretResponse().setIv(iv).setAesKeyVersion(aesSecretKey.getId()).setEncryptedSecret(encryptedSecret);
    }

    @Override
    public DeviceSecretDTO regenerate(String deviceId, AesSecretKeySet aesSecretKeySet) {
        this.repository.update(List.of(DEVICE_SECRET.STATUS.set(DeviceSecretStatus.Expired)),
                DEVICE_SECRET.DEVICE_ID.eq(deviceId),
                DEVICE_SECRET.STATUS.eq(DeviceSecretStatus.Active));
        return this.saveDeviceSecret(deviceId, aesSecretKeySet);
    }

    @Override
    public void initializeSecret(String deviceId, AesSecretKeySet aesSecretKeySet) {
        this.saveDeviceSecret(deviceId, aesSecretKeySet);
    }

    private DeviceSecretDTO saveDeviceSecret(String deviceId, AesSecretKeySet aesSecretKeySet) {
        String iv = AesUtils.generateBase64IV();
        AesSecretKeySet.AesSecretKey aesSecretKey = aesSecretKeySet.getRandomAesSecretKey();
        if (aesSecretKey == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_SECRET_NOT_HAVE_VERSION_kEY);
        }
        AesProperties aesProperties = new AesProperties(iv, aesSecretKey.getId());
        String secret = SecureRandomUtils.generateRandomHex(DEVICE_SECRET_LENGTH);
        String encryptedSecret = AesEncryptor.init(aesSecretKey.getKey(), iv).encrypt(secret);

        // @formatter:off
        DeviceSecret deviceSecret = new DeviceSecret()
                .setDeviceId(deviceId)
                .setEncryptedSecret(encryptedSecret)
                .setEncryptedSecretAddition(new DeviceEncryptedSecretAddition().setAes(aesProperties))
                .setSecretVersion(DEVICE_SECRET_DEFAULT_VERSION);
        // @formatter:on

        this.repository.insert(deviceSecret);

        DeviceSecretDTO deviceSecretDTO = DeviceSecretConverter.INSTANCE.fromDeviceSecret(deviceSecret);
        deviceSecretDTO.setSecret(secret);
        publishCacheEvictEvent(DeviceSecretCacheEvictEvent.builder().deviceSecretId(deviceSecret.getId()).deviceId(deviceSecret.getDeviceId()).build());
        return deviceSecretDTO;
    }
}

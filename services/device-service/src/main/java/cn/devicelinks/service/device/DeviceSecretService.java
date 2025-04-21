package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.common.secret.AesSecretKeySet;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.model.dto.DeviceSecretDTO;

/**
 * 设备密钥业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceSecretService extends BaseService<DeviceSecret, String> {
    /**
     * 查询设备密钥
     *
     * @param deviceId 设备ID {@link DeviceSecret#getDeviceId()}
     * @return 设备密钥实体 {@link DeviceSecret}
     */
    DeviceSecret getDeviceSecret(String deviceId);

    /**
     * 解密设备密钥
     * <p>
     * 存储在数据库中的DeviceSecret是密文存储的，返回给前端明文时需要先解密
     *
     * @param encryptedSecret    密文DeviceSecret
     * @param iv                 初始化向量
     * @param deviceSecretKeySet DeviceSecret加密Key列表
     * @return 解密后的明文DeviceSecret
     */
    String decryptSecret(String encryptedSecret, String iv, String secretKeyVersion, AesSecretKeySet deviceSecretKeySet);

    /**
     * 重新生成设备密钥
     *
     * @param deviceId           设备ID {@link DeviceSecret#getDeviceId()}
     * @param deviceSecretKeySet 设备密钥Key列表
     * @return 设备密钥传输实体 {@link DeviceSecretDTO}
     */
    DeviceSecretDTO regenerate(String deviceId, AesSecretKeySet deviceSecretKeySet);

    /**
     * 初始化设备密钥
     *
     * @param deviceId           设备ID {@link DeviceSecret#getDeviceId()}
     * @param deviceSecretKeySet 设备密钥Key列表
     */
    void initializeSecret(String deviceId, AesSecretKeySet deviceSecretKeySet);
}

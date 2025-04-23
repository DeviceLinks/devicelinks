package cn.devicelinks.common.utils;

import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.common.secret.AesSecretKeySet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AES算法加密器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AesEncryptor {
    private AesSecretKeySet.AesSecretKey aesSecretKey;
    private String key;
    private String iv;

    private AesEncryptor(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public static AesEncryptor init(String key, String iv) {
        return new AesEncryptor(key, iv);
    }

    public static AesEncryptor init(AesSecretKeySet.AesSecretKey aesSecretKey) {
        String iv = AesUtils.generateBase64IV();
        return new AesEncryptor(aesSecretKey.getKey(), iv);
    }

    public static AesEncryptor init(AesSecretKeySet aesSecretKeySet) {
        String iv = AesUtils.generateBase64IV();
        AesSecretKeySet.AesSecretKey aesSecretKey = aesSecretKeySet.getRandomAesSecretKey();
        AesEncryptor encryptor = new AesEncryptor(aesSecretKey.getKey(), iv);
        encryptor.aesSecretKey = aesSecretKey;
        return encryptor;
    }

    public String encrypt(String plainText) {
        try {
            return AesUtils.encrypt(plainText, key, iv);
        } catch (Exception e) {
            throw new DeviceLinksException("AES加密失败，原文：" + plainText, e);
        }
    }

    public String decrypt(String encryptedBase64) {
        try {
            return AesUtils.decrypt(encryptedBase64, key, iv);
        } catch (Exception e) {
            throw new DeviceLinksException("AES解密失败，密文：" + encryptedBase64, e);
        }
    }
}

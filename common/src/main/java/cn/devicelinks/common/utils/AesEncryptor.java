package cn.devicelinks.common.utils;

import cn.devicelinks.common.exception.DeviceLinksException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * AES算法加密器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AesEncryptor {
    private String base64Key;
    private String base64IV;

    private AesEncryptor(String base64Key, String base64IV) {
        this.base64Key = base64Key;
        this.base64IV = base64IV;
    }

    public static AesEncryptor init(String base64Key, String base64IV) {
        return new AesEncryptor(base64Key, base64IV);
    }

    public String encrypt(String plainText) {
        try {
            return AesUtils.encrypt(plainText, base64Key, base64IV);
        } catch (Exception e) {
            throw new DeviceLinksException("AES加密失败，原文：" + plainText, e);
        }
    }

    public String decrypt(String encryptedBase64) {
        try {
            return AesUtils.decrypt(encryptedBase64, base64Key, base64IV);
        } catch (Exception e) {
            throw new DeviceLinksException("AES解密失败，密文：" + encryptedBase64, e);
        }
    }
}

package cn.devicelinks.console;

import cn.devicelinks.framework.common.utils.AesEncryptor;
import cn.devicelinks.framework.common.utils.AesUtils;
import cn.devicelinks.framework.common.utils.SecureRandomUtils;

/**
 * AES加密工具类测试
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class AesUtilTest {
    public static void main(String[] args) throws Exception {

        String key = AesUtils.generateBase64Key(AesUtils.AesAlgorithm.Aes256Gcm);
        System.out.println("base64Key：" + key);

        String iv = AesUtils.generateBase64IV();
        System.out.println("base64IV：" + iv);


        AesEncryptor encryptor = AesEncryptor.init(key, iv);
        String encryptedBase64 = encryptor.encrypt(SecureRandomUtils.generateRandomHex(50));
        System.out.println("encryptedBase64：" + encryptedBase64);

        String plainText = encryptor.decrypt(encryptedBase64);
        System.out.println("plainText：" + plainText);
    }
}

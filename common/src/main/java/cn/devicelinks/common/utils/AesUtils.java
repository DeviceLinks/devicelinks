package cn.devicelinks.common.utils;

import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密工具类
 */
public class AesUtils {
    private static final String AES = "AES";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // in bits
    private static final int IV_LENGTH = 12;       // AES-GCM 96 bits

    public static String generateBase64Key(AesAlgorithm algorithm) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(algorithm.getKeySize()); // 128, 192, or 256
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String generateBase64IV() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }

    public static String encrypt(String plainText, String base64Key, String base64Iv) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        byte[] iv = Base64.getDecoder().decode(base64Iv);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES);

        Cipher cipher = Cipher.getInstance(AES_MODE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedBase64, String base64Key, String base64Iv) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        byte[] iv = Base64.getDecoder().decode(base64Iv);
        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES);
        Cipher cipher = Cipher.getInstance(AES_MODE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    @Getter
    public enum AesAlgorithm {
        Aes128Gcm("AES-128-GCM", 128),
        Aes256Gcm("AES-256-GCM", 256),
        ;
        private final String mode;
        private final int keySize;

        AesAlgorithm(String mode, int keySize) {
            this.mode = mode;
            this.keySize = keySize;
        }
    }
}

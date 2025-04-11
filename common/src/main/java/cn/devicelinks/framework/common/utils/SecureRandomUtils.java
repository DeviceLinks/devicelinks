package cn.devicelinks.framework.common.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 安全随机字符串工具类
 * <p>
 * 可提供bytes、hex、base64三种随机字符串
 *
 * @author 恒宇少年
 * @since 1.0
 */
public final class SecureRandomUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private SecureRandomUtils() {
    }


    public static byte[] generateRandomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }

    public static String generateRandomString(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte[] bytes = generateRandomBytes(length);
        StringBuilder sb = new StringBuilder(length);
        for (byte b : bytes) {
            // 将字节映射到字符集范围内
            int index = (b & 0xFF) % CHARACTERS.length();
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomHex(int length) {
        byte[] bytes = generateRandomBytes(length);
        return bytesToHex(bytes);
    }


    public static String generateRandomBase64(int length) {
        byte[] bytes = generateRandomBytes(length);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

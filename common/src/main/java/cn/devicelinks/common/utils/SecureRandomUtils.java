package cn.devicelinks.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecureRandomUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * 生成指定长度的随机字节数组。
     * <p>
     * 该方法使用安全的随机数生成器（SECURE_RANDOM）来生成随机字节数组。
     * 如果指定的长度为负数，将抛出IllegalArgumentException异常。
     *
     * @param length 要生成的字节数组的长度，必须为非负数。
     * @return 包含随机字节的字节数组。
     * @throws IllegalArgumentException 如果length为负数。
     */
    public static byte[] generateRandomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }

    /**
     * 生成指定长度的随机字符串。
     * <p>
     * 该函数通过生成随机字节并将其映射到预定义的字符集来创建随机字符串。
     * 如果指定的长度为负数，将抛出 IllegalArgumentException 异常。
     *
     * @param length 要生成的随机字符串的长度，必须为非负整数。
     * @return 生成的随机字符串。
     * @throws IllegalArgumentException 如果 length 为负数。
     */
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

    /**
     * 生成指定长度的随机十六进制字符串。
     *
     * @param length 生成的十六进制字符串的长度。如果长度为奇数，则最后一个字符为半个字节的十六进制。
     * @return 返回生成的随机十六进制字符串。
     */
    public static String generateRandomHex(int length) {
        int byteLength = (length + 1) / 2;
        byte[] bytes = generateRandomBytes(byteLength);
        char[] hexChars = new char[length];
        for (int i = 0; i < length / 2; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        if (length % 2 != 0) {
            int v = bytes[byteLength - 1] & 0xFF;
            hexChars[length - 1] = HEX_ARRAY[v >>> 4];
        }
        return new String(hexChars);
    }

    /**
     * 生成指定长度的随机Base64编码字符串。
     * <p>
     * 该函数首先生成指定长度的随机字节数组，然后将该字节数组进行Base64编码，
     * 最终返回编码后的字符串。
     *
     * @param length 生成的随机字节数组的长度，也是最终Base64字符串的长度。
     * @return 返回一个Base64编码的随机字符串。
     */
    public static String generateRandomBase64(int length) {
        byte[] bytes = generateRandomBytes(length);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 将字节数组转换为十六进制字符串。
     * <p>
     * 该函数遍历字节数组中的每个字节，并将其转换为两位的十六进制表示形式，
     * 最终将所有十六进制字符拼接成一个完整的字符串。
     *
     * @param bytes 需要转换的字节数组，不能为null。
     * @return 返回转换后的十六进制字符串，字符串中的每个字符代表一个字节的十六进制值。
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

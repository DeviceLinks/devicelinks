package cn.devicelinks.console.model;

import java.security.SecureRandom;

/**
 * 随机密码字符串生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SecureRandomString {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
    private static final int ACTIVATE_TOKEN_LENGTH = 50;

    public static String getRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(length);
        for (byte aByte : bytes) {
            sb.append(CHARS.charAt(Math.abs(aByte) % CHARS.length()));
        }
        return sb.toString();
    }

    public static String getActiveToken() {
        return getRandomString(ACTIVATE_TOKEN_LENGTH);
    }
}

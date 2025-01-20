package cn.devicelinks.console.model;

import java.security.SecureRandom;

/**
 * 随机密码字符串生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SecureRandomString {
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(length);
        for (byte aByte : bytes) {
            sb.append(str.charAt(Math.abs(aByte) % str.length()));
        }
        return sb.toString();
    }
}

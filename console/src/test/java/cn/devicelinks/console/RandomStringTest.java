package cn.devicelinks.console;

import cn.devicelinks.common.utils.SecureRandomUtils;

import java.util.Arrays;

/**
 * 随机字符串测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class RandomStringTest {
    public static void main(String[] args) {
        System.out.println(SecureRandomUtils.generateRandomString(30));
        System.out.println(SecureRandomUtils.generateRandomHex(16));
        System.out.println(SecureRandomUtils.generateRandomHex(32));
        System.out.println(SecureRandomUtils.generateRandomHex(64));
        System.out.println(SecureRandomUtils.generateRandomBase64(32));
        System.out.println(Arrays.toString(SecureRandomUtils.generateRandomBytes(32)));
    }
}

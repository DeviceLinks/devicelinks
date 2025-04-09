import cn.devicelinks.framework.common.utils.SecureRandomUtils;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 恒宇少年
 * @since 1.0
 */
public class SecureRandomUtilsTest {
    @Test
    void testGenerateRandomBytes() {
        int[] testLengths = {0, 1, 16, 256};
        for (int length : testLengths) {
            byte[] result = SecureRandomUtils.generateRandomBytes(length);
            assertEquals(length, result.length);
        }
    }

    @Test
    void testGenerateRandomHex() {
        int[] testLengths = {0, 1, 16, 32, 64, 128};
        for (int length : testLengths) {
            String result = SecureRandomUtils.generateRandomHex(length);
            System.out.println(result);
            // 验证长度
            assertEquals(length * 2, result.length());
            // 验证十六进制格式
            assertTrue(result.matches("^[0-9a-f]*$"));
            // 验证双向转换
            assertEquals(length, hexToBytes(result).length);
        }
    }

    @Test
    void testGenerateRandomBase64() {
        int[] testLengths = {0, 1, 16, 64};
        for (int length : testLengths) {
            String result = SecureRandomUtils.generateRandomBase64(length);
            System.out.println(result);
            // 验证解码
            byte[] decoded = Base64.getDecoder().decode(result);
            assertEquals(length, decoded.length);
            // 验证格式长度
            if (length > 0) {
                int expectedLength = (int) Math.ceil(length * 4 / 3.0);
                expectedLength = ((expectedLength + 3) / 4) * 4;  // 对齐到4的倍数
                assertEquals(expectedLength, result.length());
            }
        }
    }

    @Test
    void testNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            SecureRandomUtils.generateRandomBytes(-1);
        });
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}

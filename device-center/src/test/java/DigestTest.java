import cn.devicelinks.common.utils.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * Digest Test
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DigestTest {
    public static void main(String[] args) {
        byte[] bytes = "uyF20TxVlzh7lZbvgiZD".getBytes(StandardCharsets.UTF_8);
        String hex = DigestUtils.getHexDigest(cn.devicelinks.common.utils.DigestUtils.SHA256, bytes);
        System.out.println(hex);
    }
}

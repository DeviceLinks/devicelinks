package cn.devicelinks.framework.common.secret;

import lombok.Data;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;

/**
 * Aes加密方式密钥Key集合
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class AesSecretKeySet {

    private final List<AesSecretKey> keys;

    public AesSecretKeySet(List<AesSecretKey> keys) {
        Assert.notEmpty(keys, "The AesSecretKeySet List cannot be empty.");
        this.keys = keys;
    }

    public AesSecretKey getRandomAesSecretKey() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.keys.size());
        return this.keys.get(randomIndex);
    }

    public AesSecretKey getAesSecretKey(String version) {
        // @formatter:off
        return this.keys.stream()
                .filter(aesSecretKey -> aesSecretKey.getVersion().equals(version))
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    @Data
    public static class AesSecretKey {
        private String version;
        private String key;

        public AesSecretKey(String version, String key) {
            this.version = version;
            this.key = key;
        }
    }
}

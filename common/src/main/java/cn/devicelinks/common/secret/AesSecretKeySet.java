package cn.devicelinks.common.secret;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

/**
 * Aes加密方式密钥Key集合
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AesSecretKeySet {

    private List<AesSecretKey> keys;

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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AesSecretKey {
        private String version;
        private String key;
    }
}

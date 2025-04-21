package cn.devicelinks.framework.common.secret;

import lombok.Data;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;

/**
 * 设备密钥Key集合
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DeviceSecretKeySet {

    private final List<DeviceSecretKey> keys;

    public DeviceSecretKeySet(List<DeviceSecretKey> keys) {
        Assert.notEmpty(keys, "The DeviceSecretKey List cannot be empty.");
        this.keys = keys;
    }

    public DeviceSecretKey getRandomDeviceSecretKey() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.keys.size());
        return this.keys.get(randomIndex);
    }

    public DeviceSecretKey getDeviceSecretKey(String version) {
        // @formatter:off
        return this.keys.stream()
                .filter(deviceSecretKey -> deviceSecretKey.getVersion().equals(version))
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    @Data
    public static class DeviceSecretKey {
        private String version;
        private String key;

        public DeviceSecretKey(String version, String key) {
            this.version = version;
            this.key = key;
        }
    }
}

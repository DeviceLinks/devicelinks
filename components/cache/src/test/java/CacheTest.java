import cn.devicelinks.component.cache.core.Cache;
import cn.devicelinks.component.cache.core.CaffeineCache;
import cn.devicelinks.component.cache.core.CompositeCache;
import lombok.SneakyThrows;

import java.util.List;

/**
 * 缓存测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CacheTest {

    public static void main(String[] args) {
        //caffeineCache();
        compositeCache();
    }

    @SneakyThrows
    static void caffeineCache() {
        // default cache 60 seconds
        Cache<String, Object> cache = new CaffeineCache<>();

        String key = "test";
        cache.put(key, System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            System.out.println("cache value：" + cache.get(key));
            Thread.sleep(10 * 1000);
        }
    }

    @SneakyThrows
    static void compositeCache() {
        Cache<String, Object> caffeineCache = new CaffeineCache<>();
        Cache<String, Object> cache = new CompositeCache<>(List.of(caffeineCache));

        String key = "test";
        cache.put(key, System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            System.out.println("cache value：" + cache.get(key, key1 -> System.currentTimeMillis()));
            Thread.sleep(10 * 1000);
        }
    }
}

package cn.devicelinks.api.support.feign.cache;

import cn.devicelinks.common.Constants;
import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Feign Client接口方法缓存Key生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class FeignCacheKeyBuilder {

    private static final ObjectMapper objectMapper = new DeviceLinksJsonMapper();

    private static final String FEIGN_CACHE_PREFIX = "feign";

    public static String build(Method method, Object[] args) {
        // @formatter:off
        StringBuilder key = new StringBuilder(FEIGN_CACHE_PREFIX)
                .append(Constants.RISK)
                .append(method.getDeclaringClass().getSimpleName())
                .append(Constants.RISK)
                .append(method.getName())
                .append(Constants.RISK);
        // @formatter:on
        key.append(getRequestMd5Hex(method, args));
        return key.toString();
    }

    public static String getRequestMd5Hex(Method method, Object[] args) {
        StringBuilder key = new StringBuilder(method.getDeclaringClass().getName())
                .append("#").append(method.getName());

        if (args != null) {
            for (Object arg : args) {
                key.append(Constants.RISK).append(serialize(arg));
            }
        }

        return DigestUtils.md5Hex(key.toString()); // 用 MD5 缩短 key 长度
    }

    private static String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return Objects.toString(obj);
        }
    }
}

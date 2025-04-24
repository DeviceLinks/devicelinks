package cn.devicelinks.api.support.feign.cache;

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
    public static String build(Method method, Object[] args) {
        StringBuilder key = new StringBuilder(method.getDeclaringClass().getName())
                .append("#").append(method.getName());

        if (args != null) {
            for (Object arg : args) {
                key.append(":").append(serialize(arg));
            }
        }

        return DigestUtils.md5Hex(key.toString()); // 用 MD5 缩短 key 长度
    }

    private static String serialize(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return Objects.toString(obj);
        }
    }
}

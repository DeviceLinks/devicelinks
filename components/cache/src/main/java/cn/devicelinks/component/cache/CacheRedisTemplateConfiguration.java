package cn.devicelinks.component.cache;

import cn.devicelinks.component.jackson.DeviceLinksJsonMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis方式缓存所需的{@link RedisTemplate}配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class CacheRedisTemplateConfiguration {

    public static final String REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME = "cacheRedisTemplate";

    /**
     * 创建用户Redis缓存的{@link RedisTemplate<String,Object>}实例
     *
     * @param connectionFactory {@link RedisConnectionFactory}
     * @return {@link RedisTemplate}
     */
    @Bean
    @ConditionalOnMissingBean(name = REDIS_TEMPLATE_FOR_CACHE_BEAN_NAME)
    public RedisTemplate<String, Object> cacheRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        DeviceLinksJsonMapper jsonMapper = new DeviceLinksJsonMapper();
        jsonMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jsonMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(jsonMapper, Object.class);

        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}

package cn.devicelinks.console.configuration;

import cn.devicelinks.framework.common.api.ApiExceptionAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 接口异常统一处理自动化配置类
 *
 * @author 恒宇少年
 * @see ApiExceptionAdvice
 * @since 1.0
 */
@Configuration
public class ApiExceptionAdviceAutoConfiguration {
    /**
     * 实例化 {@link ApiExceptionAdvice}
     *
     * @return {@link ApiExceptionAdvice}
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiExceptionAdvice apiExceptionAdvice() {
        return new ApiExceptionAdvice();
    }
}

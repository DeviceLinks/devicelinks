package cn.devicelinks.component.openfeign;

import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import cn.devicelinks.component.web.utils.SignUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign Client Api请求签名拦截器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class OpenFeignClientSignRequestInterceptor implements RequestInterceptor {

    private final String apiKey;
    private final String apiSecret;

    public OpenFeignClientSignRequestInterceptor(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestTemplate.header(OpenFeignConstants.API_KEY_HEADER_NAME, apiKey);
        requestTemplate.header(OpenFeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
        requestTemplate.header(OpenFeignConstants.API_SIGN_HEADER_NAME,
                SignUtils.sign(HmacSignatureAlgorithm.HmacSHA256, apiSecret, timestamp, requestTemplate.queries(), requestTemplate.body()));
    }
}

package cn.devicelinks.transport.support.feign;

import cn.devicelinks.framework.common.feign.ApiRequestSignUtils;
import cn.devicelinks.framework.common.feign.FeignConstants;
import cn.devicelinks.transport.support.configuration.TransportProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign Client Api请求签名拦截器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceCenterSignFeignRequestInterceptor implements RequestInterceptor {

    private final TransportProperties.DeviceCenterApi deviceCenterApi;

    public DeviceCenterSignFeignRequestInterceptor(TransportProperties.DeviceCenterApi deviceCenterApi) {
        this.deviceCenterApi = deviceCenterApi;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestTemplate.header(FeignConstants.API_KEY_HEADER_NAME, deviceCenterApi.getApiKey());
        requestTemplate.header(FeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
        requestTemplate.header(FeignConstants.API_SIGN_HEADER_NAME,
                ApiRequestSignUtils.sign(deviceCenterApi.getApiSecret(), timestamp, requestTemplate));
    }
}

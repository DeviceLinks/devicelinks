package cn.devicelinks.api.device.center.configuration;

import cn.devicelinks.api.support.feign.FeignClientSignUtils;
import cn.devicelinks.api.support.feign.FeignConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign Client Api请求签名拦截器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceCenterFeignClientSignRequestInterceptor implements RequestInterceptor {

    private final DeviceCenterApiProperties deviceCenterApi;

    public DeviceCenterFeignClientSignRequestInterceptor(DeviceCenterApiProperties deviceCenterApi) {
        this.deviceCenterApi = deviceCenterApi;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestTemplate.header(FeignConstants.API_KEY_HEADER_NAME, deviceCenterApi.getApiKey());
        requestTemplate.header(FeignConstants.API_TIMESTAMP_HEADER_NAME, timestamp);
        requestTemplate.header(FeignConstants.API_SIGN_HEADER_NAME,
                FeignClientSignUtils.sign(deviceCenterApi.getApiSecret(), timestamp, requestTemplate));
    }
}

package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * 设备属性OpenFeign客户端接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface DeviceAttributeFeignClient {
    /**
     * 新增或更新设备属性
     *
     * @param request 新增或更新设备属性的请求主体实体 {@link SaveOrUpdateDeviceAttributeRequest}
     * @return 统一响应格式 {@link ApiResponse}
     */
    @RequestLine("POST /api/device/attributes")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Boolean> saveOrUpdateAttributes(SaveOrUpdateDeviceAttributeRequest request);
}

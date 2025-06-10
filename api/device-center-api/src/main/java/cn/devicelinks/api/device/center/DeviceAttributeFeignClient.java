package cn.devicelinks.api.device.center;

import cn.devicelinks.api.device.center.model.request.QueryDeviceAttributeRequest;
import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.DeviceAttribute;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * 设备属性服务的 Feign 客户端接口。
 *
 * <p>提供与设备中心模块进行远程通信的能力，主要包括：
 * <ul>
 *   <li>新增或更新设备属性</li>
 *   <li>查询设备属性值列表</li>
 * </ul>
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface DeviceAttributeFeignClient {
    /**
     * 新增或更新指定设备的属性信息
     *
     * @param request 新增或更新属性的请求体 {@link SaveOrUpdateDeviceAttributeRequest}
     * @return 操作是否成功 {@link ApiResponse}
     */
    @RequestLine("POST /api/device/attributes")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Boolean> saveOrUpdateAttributes(SaveOrUpdateDeviceAttributeRequest request);

    /**
     * 查询指定设备的属性值列表
     *
     * @param request 属性查询条件 {@link QueryDeviceAttributeRequest}
     * @return 属性值列表 {@link DeviceAttribute}
     */
    @RequestLine("POST /api/device/attributes/query")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<List<DeviceAttribute>> getAttributes(QueryDeviceAttributeRequest request);
}

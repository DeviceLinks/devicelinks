package cn.devicelinks.api.device.center;

import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.entity.DeviceProfileProvisionAddition;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 设备配置文件Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface DeviceProfileFeignClient {
    /**
     * 根据预配置Key查询设备配置文件
     *
     * @param provisionKey 预配置Key {@link DeviceProfileProvisionAddition#getProvisionDeviceKey()}
     * @return 设备配置文件 {@link DeviceProfile}
     */
    @RequestLine("GET /api/device-profiles?provisionKey={provisionKey}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<DeviceProfile> getByProvisionKey(@Param("provisionKey") String provisionKey);
}

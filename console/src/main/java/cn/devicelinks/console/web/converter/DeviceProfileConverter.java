package cn.devicelinks.console.web.converter;

import cn.devicelinks.console.web.request.AddDeviceProfileRequest;
import cn.devicelinks.console.web.request.UpdateDeviceProfileBasicInfoRequest;
import cn.devicelinks.console.web.request.UpdateDeviceProfileRequest;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 设备配置文件数据转换接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DeviceProfileConverter {
    /**
     * get new mapStruct instance
     */
    DeviceProfileConverter INSTANCE = Mappers.getMapper(DeviceProfileConverter.class);

    /**
     * Create {@link DeviceProfile} from {@link AddDeviceProfileRequest}
     *
     * @param request {@link AddDeviceProfileRequest}
     * @return {@link DeviceProfile}
     */
    @Mappings({
            @Mapping(target = "provisionRegistrationStrategy", expression = "java(cn.devicelinks.framework.common.ProvisionRegistrationStrategy.valueOf(request.getProvisionRegistrationStrategy()))")
    })
    DeviceProfile fromAddDeviceProfileRequest(AddDeviceProfileRequest request);

    /**
     * Update {@link DeviceProfile} from {@link UpdateDeviceProfileRequest}
     *
     * @param request       {@link UpdateDeviceProfileRequest}
     * @param deviceProfile {@link DeviceProfile}
     */
    @Mappings({
            @Mapping(target = "provisionRegistrationStrategy", expression = "java(cn.devicelinks.framework.common.ProvisionRegistrationStrategy.valueOf(request.getProvisionRegistrationStrategy()))")
    })
    void fromUpdateDeviceProfileRequest(UpdateDeviceProfileRequest request, @MappingTarget DeviceProfile deviceProfile);

    /**
     * Update {@link DeviceProfile} from {@link UpdateDeviceProfileBasicInfoRequest}
     *
     * @param request       {@link UpdateDeviceProfileBasicInfoRequest}
     * @param deviceProfile {@link DeviceProfile}
     */
    void updateBasicInfo(UpdateDeviceProfileBasicInfoRequest request, @MappingTarget DeviceProfile deviceProfile);
}

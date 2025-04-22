package cn.devicelinks.api.model.converter;

import cn.devicelinks.api.model.request.AddDeviceProfileRequest;
import cn.devicelinks.api.model.request.UpdateDeviceProfileBasicInfoRequest;
import cn.devicelinks.api.model.request.UpdateDeviceProfileRequest;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
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
    DeviceProfile fromAddDeviceProfileRequest(AddDeviceProfileRequest request);

    /**
     * Update {@link DeviceProfile} from {@link UpdateDeviceProfileRequest}
     *
     * @param request       {@link UpdateDeviceProfileRequest}
     * @param deviceProfile {@link DeviceProfile}
     */
    void fromUpdateDeviceProfileRequest(UpdateDeviceProfileRequest request, @MappingTarget DeviceProfile deviceProfile);

    /**
     * Update {@link DeviceProfile} from {@link UpdateDeviceProfileBasicInfoRequest}
     *
     * @param request       {@link UpdateDeviceProfileBasicInfoRequest}
     * @param deviceProfile {@link DeviceProfile}
     */
    void updateBasicInfo(UpdateDeviceProfileBasicInfoRequest request, @MappingTarget DeviceProfile deviceProfile);
}

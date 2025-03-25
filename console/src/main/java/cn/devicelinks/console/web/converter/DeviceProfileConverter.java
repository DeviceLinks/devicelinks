package cn.devicelinks.console.web.converter;

import cn.devicelinks.console.web.request.AddDeviceProfileRequest;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mappings({
            @Mapping(target = "provisionRegistrationStrategy", expression = "java(cn.devicelinks.framework.common.ProvisionRegistrationStrategy.valueOf(request.getProvisionRegistrationStrategy()))"),
            @Mapping(target = "extension", ignore = true)
    })
    DeviceProfile fromAddDeviceProfileRequest(AddDeviceProfileRequest request);
}

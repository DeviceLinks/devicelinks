package cn.devicelinks.api.support.converter;

import cn.devicelinks.api.support.request.AddDeviceRequest;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.model.dto.DeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DeviceConverter {
    /**
     * get new mapStruct instance
     */
    DeviceConverter INSTANCE = Mappers.getMapper(DeviceConverter.class);

    @Mappings({
            @Mapping(target = "deviceType", expression = "java(cn.devicelinks.framework.common.DeviceType.valueOf(request.getDeviceType()))")
    })
    Device fromAddDeviceRequest(AddDeviceRequest request);

    DeviceDTO from(Device device);
}

package cn.devicelinks.api.model.converter;

import cn.devicelinks.api.model.dto.DeviceDTO;
import cn.devicelinks.api.model.request.AddDeviceRequest;
import cn.devicelinks.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * The {@link Device} MapStruct Converter
 *
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
            @Mapping(target = "deviceType", expression = "java(cn.devicelinks.common.DeviceType.valueOf(request.getDeviceType()))")
    })
    Device fromAddDeviceRequest(AddDeviceRequest request);

    DeviceDTO from(Device device);
}

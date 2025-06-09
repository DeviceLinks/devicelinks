package cn.devicelinks.center.model.converter;

import cn.devicelinks.entity.DeviceAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * The {@link cn.devicelinks.entity.DeviceAttribute} MapStruct Converter
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DeviceAttributeConverter {
    /**
     * get new mapStruct instance
     */
    DeviceAttributeConverter INSTANCE = Mappers.getMapper(DeviceAttributeConverter.class);

    void update(DeviceAttribute requestDeviceAttribute, @MappingTarget DeviceAttribute storedDeviceAttribute);
}

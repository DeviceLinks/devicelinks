package cn.devicelinks.api.model.converter;

import cn.devicelinks.api.model.dto.DeviceSecretDTO;
import cn.devicelinks.entity.DeviceSecret;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 设备密钥转换器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DeviceSecretConverter {
    /**
     * get new mapStruct instance
     */
    DeviceSecretConverter INSTANCE = Mappers.getMapper(DeviceSecretConverter.class);

    DeviceSecretDTO fromDeviceSecret(DeviceSecret deviceSecret);
}

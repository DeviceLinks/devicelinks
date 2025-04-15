package cn.devicelinks.api.support.converter;

import cn.devicelinks.framework.common.pojos.DeviceSecret;
import cn.devicelinks.framework.jdbc.model.dto.DeviceSecretDTO;
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

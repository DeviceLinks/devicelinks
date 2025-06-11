package cn.devicelinks.transport.support.model.converter;

import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.transport.support.model.response.SubscribeDeviceAttributeDesiredResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The {@link cn.devicelinks.entity.DeviceAttributeDesired} MapStruct Converter
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DeviceAttributeDesiredConverter {
    /**
     * get new mapStruct instance
     */
    DeviceAttributeDesiredConverter INSTANCE = Mappers.getMapper(DeviceAttributeDesiredConverter.class);

    /**
     * {@link DeviceAttributeDesired} Convert to {@link SubscribeDeviceAttributeDesiredResponse.AttributeDesiredVersionValue}
     *
     * @param desired 设备期望属性对象实例 {@link DeviceAttributeDesired}
     * @return 设备查询响应的期望属性值对象实例 {@link SubscribeDeviceAttributeDesiredResponse.AttributeDesiredVersionValue}
     */
    SubscribeDeviceAttributeDesiredResponse.AttributeDesiredVersionValue fromDeviceAttributeDesired(DeviceAttributeDesired desired);

    List<SubscribeDeviceAttributeDesiredResponse.AttributeDesiredVersionValue> fromDeviceAttributeDesired(List<DeviceAttributeDesired> desired);
}

package cn.devicelinks.transport.support.model.converter;

import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.transport.support.model.params.DeviceAttributeParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * {@link SaveOrUpdateDeviceAttributeRequest.ReportedAttribute} MapStruct Converter
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface ReportedAttributeConverter {
    /**
     * get new mapStruct instance
     */
    ReportedAttributeConverter INSTANCE = Mappers.getMapper(ReportedAttributeConverter.class);

    SaveOrUpdateDeviceAttributeRequest.ReportedAttribute fromAttributeValue(DeviceAttributeParam.AttributeValue attributeValue);

    List<SaveOrUpdateDeviceAttributeRequest.ReportedAttribute> fromAttributeValue(List<DeviceAttributeParam.AttributeValue> attributeValueList);

}

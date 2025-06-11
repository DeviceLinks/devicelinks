package cn.devicelinks.transport.support.model.converter;

import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.transport.support.model.body.ReportDeviceAttributeBody;
import cn.devicelinks.transport.support.model.response.QueryDeviceAttributeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * {@link DeviceAttribute} MapStruct Converter
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

    /**
     * {@link ReportDeviceAttributeBody.AttributeValue} Convert to {@link SaveOrUpdateDeviceAttributeRequest.ReportedAttribute}
     *
     * @param attributeValue 设备上报属性值对象实例 {@link ReportDeviceAttributeBody.AttributeValue}
     * @return 转换FeignClient接口需要的上报属性值对象实例 {@link SaveOrUpdateDeviceAttributeRequest.ReportedAttribute}
     */
    SaveOrUpdateDeviceAttributeRequest.ReportedAttribute fromAttributeValue(ReportDeviceAttributeBody.AttributeValue attributeValue);

    List<SaveOrUpdateDeviceAttributeRequest.ReportedAttribute> fromAttributeValue(List<ReportDeviceAttributeBody.AttributeValue> attributeValueList);

    /**
     * {@link DeviceAttribute} Convert to {@link QueryDeviceAttributeResponse.AttributeVersionValue}
     *
     * @param deviceAttribute 设备属性 {@link DeviceAttribute}
     * @return 查询设备属性的响应对象实例 {@link QueryDeviceAttributeResponse.AttributeVersionValue}
     */
    QueryDeviceAttributeResponse.AttributeVersionValue fromDeviceAttribute(DeviceAttribute deviceAttribute);

    List<QueryDeviceAttributeResponse.AttributeVersionValue> fromDeviceAttribute(List<DeviceAttribute> deviceAttributeList);

}

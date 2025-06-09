package cn.devicelinks.transport.support.service;

import cn.devicelinks.api.device.center.DeviceAttributeFeignClient;
import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.common.RequestSource;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.transport.support.TransportStatusCodes;
import cn.devicelinks.transport.support.context.DeviceContext;
import cn.devicelinks.transport.support.model.Message;
import cn.devicelinks.transport.support.model.converter.ReportedAttributeConverter;
import cn.devicelinks.transport.support.model.params.DeviceAttributeParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备属性业务逻辑类
 * <p>
 * 为不同协议的传输服务提供公共操作设备属性的方法
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceAttributeService {

    private final DeviceAttributeFeignClient deviceAttributeFeignClient;

    public void reportAttribute(DeviceContext context, Message<DeviceAttributeParam> message) {
        List<SaveOrUpdateDeviceAttributeRequest.ReportedAttribute> reportedAttributeList =
                ReportedAttributeConverter.INSTANCE.fromAttributeValue(message.getParam().getAttributes());
        // @formatter:off
        SaveOrUpdateDeviceAttributeRequest request = new SaveOrUpdateDeviceAttributeRequest()
                .setDeviceId(context.getDeviceId())
                .setMessageId(message.getMessageId())
                .setSource(RequestSource.TransportHttp)
                .setAttributes(reportedAttributeList)
                .setTimestamp(message.getTimestamp());
        // @formatter:on
        Boolean result = ApiResponseUnwrapper.unwrap(deviceAttributeFeignClient.saveOrUpdateAttributes(request));
        if (result == null || !result) {
            throw new ApiException(TransportStatusCodes.REPORT_ATTRIBUTES_ERROR_STATUS_CODE);
        }
    }
}

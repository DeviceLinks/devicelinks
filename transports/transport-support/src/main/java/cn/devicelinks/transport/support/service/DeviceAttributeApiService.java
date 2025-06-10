package cn.devicelinks.transport.support.service;

import cn.devicelinks.api.device.center.DeviceAttributeFeignClient;
import cn.devicelinks.api.device.center.model.request.QueryDeviceAttributeRequest;
import cn.devicelinks.api.device.center.model.request.SaveOrUpdateDeviceAttributeRequest;
import cn.devicelinks.common.AttributeScope;
import cn.devicelinks.common.AttributeValueSource;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.transport.support.TransportStatusCodes;
import cn.devicelinks.transport.support.context.DeviceContext;
import cn.devicelinks.transport.support.model.BodyMessage;
import cn.devicelinks.transport.support.model.converter.DeviceAttributeConverter;
import cn.devicelinks.transport.support.model.query.QueryDeviceAttributeParam;
import cn.devicelinks.transport.support.model.body.ReportDeviceAttributeBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备属性信息API封装类
 * <p>
 * 为不同协议的传输服务提供公共操作设备属性的方法
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceAttributeApiService {

    private final DeviceAttributeFeignClient deviceAttributeFeignClient;

    /**
     * 设备上报属性信息
     *
     * @param context 当前设备上下文 {@link DeviceContext}
     * @param message 上报属性信息消息封装对象
     */
    public void reportAttribute(DeviceContext context, BodyMessage<ReportDeviceAttributeBody> message) {
        // @formatter:off
        SaveOrUpdateDeviceAttributeRequest request = message.toRequest(context,
                new SaveOrUpdateDeviceAttributeRequest()
                        .setAttributes(DeviceAttributeConverter.INSTANCE.fromAttributeValue(message.getBody().getAttributes()))
        );
        // @formatter:on
        Boolean result = ApiResponseUnwrapper.unwrap(deviceAttributeFeignClient.saveOrUpdateAttributes(request));
        if (result == null || !result) {
            throw new ApiException(TransportStatusCodes.REPORT_ATTRIBUTES_ERROR_STATUS_CODE);
        }
    }

    /**
     * 查询设备的属性列表
     *
     * <p>返回范围为 {@link AttributeScope#Device} 和 {@link AttributeScope#Common} 的属性值，
     * 包括属性值版本 {@link DeviceAttribute#getVersion()} 和最后上报时间 {@link DeviceAttribute#getLastUpdateTime()}。
     *
     * <p>若属性未定义（即为未知属性）但来源为 {@link AttributeValueSource#DeviceReport}，也将包含在结果中。
     *
     * @param context 当前设备上下文 {@link DeviceContext}
     * @param param   查询参数消息封装对象
     * @return 满足条件的设备属性列表 {@link DeviceAttribute}
     */
    public List<DeviceAttribute> getAttributes(DeviceContext context, QueryDeviceAttributeParam param) {
        // @formatter:off
        QueryDeviceAttributeRequest request = param.toRequest(context,
                new QueryDeviceAttributeRequest()
                        .setIdentifiers(param.getIdentifiers())
        );
        // @formatter:on
        return ApiResponseUnwrapper.unwrap(deviceAttributeFeignClient.getAttributes(request));
    }
}

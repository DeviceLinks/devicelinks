package cn.devicelinks.transport.http.apis;

import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.transport.support.context.DeviceContext;
import cn.devicelinks.transport.support.context.DeviceContextHolder;
import cn.devicelinks.transport.support.model.BodyMessage;
import cn.devicelinks.transport.support.model.MessageResponse;
import cn.devicelinks.transport.support.model.body.ReportDeviceAttributeBody;
import cn.devicelinks.transport.support.model.converter.DeviceAttributeConverter;
import cn.devicelinks.transport.support.model.query.QueryDeviceAttributeParam;
import cn.devicelinks.transport.support.model.response.QueryDeviceAttributeResponse;
import cn.devicelinks.transport.support.service.DeviceAttributeApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备属性接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/attributes")
@RequiredArgsConstructor
public class DeviceAttributeApi {

    private final DeviceAttributeApiService deviceAttributeService;

    /**
     * 上报属性
     * <p>
     * 设备主动上报属性值以及值版本号，如果是在功能模块下定义的属性在首次上报时可直接与属性{@link cn.devicelinks.entity.Attribute}进行关联
     * 如果并未定义则作为未知属性存储，对于未知的属性，值来源会设置成{@link cn.devicelinks.common.AttributeValueSource#DeviceReport}
     *
     * @param message 上报设备属性值的消息实体
     * @return 消息统一响应实体 {@link MessageResponse}
     */
    @PostMapping
    public MessageResponse<Boolean> reportAttributes(@RequestBody @Valid BodyMessage<ReportDeviceAttributeBody> message) {
        DeviceContext deviceContext = DeviceContextHolder.getContext();
        deviceAttributeService.reportAttribute(deviceContext, message);
        return MessageResponse.success(message.getMessageId(), Boolean.TRUE);
    }

    /**
     * 查询设备属性
     * <p>
     * 可查询的属性范围：{@link cn.devicelinks.common.AttributeScope#Device}、{@link cn.devicelinks.common.AttributeScope#Common}
     * 也可查询未知的设备属性（设备主动上报但是并未在功能模块下定义），但是仅限{@link cn.devicelinks.common.AttributeValueSource#DeviceReport}来源的属性
     *
     * @param param 查询设备属性参数实体 {@link QueryDeviceAttributeParam}
     * @return 查询设备属性响应实体 {@link QueryDeviceAttributeResponse}
     */
    @GetMapping
    public MessageResponse<QueryDeviceAttributeResponse> getAttributes(@Valid QueryDeviceAttributeParam param) {
        // @formatter:off
        List<DeviceAttribute> deviceAttributeList = deviceAttributeService.getAttributes(DeviceContextHolder.getContext(), param);
        List<QueryDeviceAttributeResponse.AttributeVersionValue> attributeVersionValueList =
                DeviceAttributeConverter.INSTANCE.fromDeviceAttribute(deviceAttributeList);
        QueryDeviceAttributeResponse response =
                new QueryDeviceAttributeResponse()
                        .setAttributes(attributeVersionValueList);
        // @formatter:on
        return MessageResponse.success(param.getMessageId(), response);
    }
}

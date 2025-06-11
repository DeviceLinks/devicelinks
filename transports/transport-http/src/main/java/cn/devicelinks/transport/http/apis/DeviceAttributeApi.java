package cn.devicelinks.transport.http.apis;

import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.transport.support.context.DeviceContext;
import cn.devicelinks.transport.support.context.DeviceContextHolder;
import cn.devicelinks.transport.support.model.BodyMessage;
import cn.devicelinks.transport.support.model.MessageResponse;
import cn.devicelinks.transport.support.model.body.ReportDeviceAttributeBody;
import cn.devicelinks.transport.support.model.converter.DeviceAttributeConverter;
import cn.devicelinks.transport.support.model.query.QueryDeviceAttributeParam;
import cn.devicelinks.transport.support.model.query.SubscribeDeviceAttributeDesiredParam;
import cn.devicelinks.transport.support.model.response.QueryDeviceAttributeResponse;
import cn.devicelinks.transport.support.model.response.SubscribeDeviceAttributeDesiredResponse;
import cn.devicelinks.transport.support.service.DeviceAttributeApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private final ConcurrentMap<String, List<DeferredResult<MessageResponse<SubscribeDeviceAttributeDesiredResponse>>>> subscribeWaitingMap = new ConcurrentHashMap<>();
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
    public MessageResponse<QueryDeviceAttributeResponse> queryAttributes(@Valid QueryDeviceAttributeParam param) {
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

    /**
     * 订阅设备属性期望值更新
     * <p>
     * 通过传递"timeout"参数来等待阻塞返回数据的时间，如果在指定超时时长内返回了数据则直接返回，如果没有则等待超时后返回
     * <p>
     * 期望属性是由控制台为设备设置的属性，可以是未知属性，也可是已知属性（功能模块下定义的属性）
     * 对于未知的属性在控制台是允许被修改数据类型{@link DeviceAttributeDesired#getDataType()}的，所以每次返回的数据类型可能会有差异
     *
     * @param param 查询设备属性参数实体 {@link QueryDeviceAttributeParam}
     * @return 查询设备属性期望值响应实体 {@link SubscribeDeviceAttributeDesiredResponse}
     */
    @GetMapping(value = "/subscribe/desired", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<MessageResponse<SubscribeDeviceAttributeDesiredResponse>> subscribeAttributesDesired(@Valid SubscribeDeviceAttributeDesiredParam param) {
        DeferredResult<MessageResponse<SubscribeDeviceAttributeDesiredResponse>> deferredResult = new DeferredResult<>(param.getTimeout());
        DeviceContext deviceContext = DeviceContextHolder.getContext();

        subscribeWaitingMap.computeIfAbsent(deviceContext.getDeviceId(), k -> new CopyOnWriteArrayList<>()).add(deferredResult);
        deferredResult.onTimeout(() -> deferredResult.setResult(MessageResponse.success(param.getMessageId(), new SubscribeDeviceAttributeDesiredResponse())));
        deferredResult.onCompletion(() -> subscribeWaitingMap.get(deviceContext.getDeviceId()).remove(deferredResult));

        deviceAttributeService.subscribeAttributesDesired(deferredResult, deviceContext, param);

        return deferredResult;
    }
}

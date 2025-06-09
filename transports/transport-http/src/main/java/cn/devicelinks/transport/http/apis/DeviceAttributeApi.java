package cn.devicelinks.transport.http.apis;

import cn.devicelinks.transport.support.context.DeviceContext;
import cn.devicelinks.transport.support.context.DeviceContextHolder;
import cn.devicelinks.transport.support.model.Message;
import cn.devicelinks.transport.support.model.MessageResponse;
import cn.devicelinks.transport.support.model.params.DeviceAttributeParam;
import cn.devicelinks.transport.support.service.DeviceAttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final DeviceAttributeService deviceAttributeService;

    /**
     * 上报属性
     *
     * @param message 上报设备属性值的消息实体
     * @return 消息统一响应实体 {@link MessageResponse}
     */
    @PostMapping(value = "/report")
    public MessageResponse<Boolean> reportAttributes(@RequestBody @Valid Message<DeviceAttributeParam> message) {
        DeviceContext deviceContext = DeviceContextHolder.getContext();
        deviceAttributeService.reportAttribute(deviceContext, message);
        return MessageResponse.success(message.getMessageId(), Boolean.TRUE);
    }
}

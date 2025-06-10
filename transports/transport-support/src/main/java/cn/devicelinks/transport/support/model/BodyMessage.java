package cn.devicelinks.transport.support.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对于{@link org.springframework.web.bind.annotation.RequestBody}注解存在主体请求数据的通用消息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BodyMessage<T> extends Message {
    /**
     * 业务参数对象实例，类型不固定
     */
    @Valid
    @NotNull
    private T body;
}

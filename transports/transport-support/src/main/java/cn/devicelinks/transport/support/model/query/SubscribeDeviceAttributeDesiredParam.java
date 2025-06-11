package cn.devicelinks.transport.support.model.query;

import cn.devicelinks.transport.support.model.QueryMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

/**
 * 订阅设备属性期望值变动参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SubscribeDeviceAttributeDesiredParam extends QueryMessage<SubscribeDeviceAttributeDesiredParam> {
    /**
     * 属性标识符列表
     */
    private String[] identifiers;
    /**
     * 订阅阻塞超时时长
     */
    @Range(min = 1, max = 5 * 60 * 1000, message = "订阅时长取值范围为1 ~ 300000.")
    private long timeout;
}

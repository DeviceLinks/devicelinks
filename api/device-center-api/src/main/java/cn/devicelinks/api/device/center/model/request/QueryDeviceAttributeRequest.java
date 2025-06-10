package cn.devicelinks.api.device.center.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 查询设备属性值请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class QueryDeviceAttributeRequest extends BaseDeviceRequest<QueryDeviceAttributeRequest> {
    /**
     * 指定查询的属性标识符列表
     */
    private String[] identifiers;
}

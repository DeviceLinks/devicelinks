package cn.devicelinks.transport.support.model.query;

import cn.devicelinks.transport.support.model.QueryMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询设备属性参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryDeviceAttributeParam extends QueryMessage<QueryDeviceAttributeParam> {
    /**
     * 属性标识符列表
     */
    private String[] identifiers;
}

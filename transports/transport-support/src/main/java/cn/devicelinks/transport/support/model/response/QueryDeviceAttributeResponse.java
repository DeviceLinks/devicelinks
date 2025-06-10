package cn.devicelinks.transport.support.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询设备属性响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class QueryDeviceAttributeResponse {

    private List<AttributeVersionValue> attributes = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class AttributeVersionValue {
        private String identifier;
        private Object value;
        private int version;
        private LocalDateTime lastUpdateTime;
    }
}

package cn.devicelinks.transport.support.model.response;

import cn.devicelinks.common.AttributeDataType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询设备属性值响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class SubscribeDeviceAttributeUpdateResponse {

    private List<AttributeUpdateVersionValue> attributes = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class AttributeUpdateVersionValue {
        private String identifier;
        private Object value;
        private AttributeDataType dataType;
        private int version;
        private LocalDateTime expiredTime;
    }
}

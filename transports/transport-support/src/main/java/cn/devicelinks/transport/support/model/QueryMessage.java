package cn.devicelinks.transport.support.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 对于查询数据的通用消息实体定义
 * <p>
 * 使用时只需要继承{@link QueryMessage}并且将泛型类型设置成实现类类型即可，具体使用方式如下：
 * // @formatter:off
 * {@code
 *  @EqualsAndHashCode(callSuper = true)
 *  @Data
 *  public class QueryDeviceAttributeParam extends QueryMessage<QueryDeviceAttributeParam> {
 *      private String[] identifiers;
 *  }
 * }
 * // @formatter:on
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class QueryMessage<T extends QueryMessage<T>> extends Message {

    @SuppressWarnings("unchecked")
    public T self() {
        return (T) this;
    }

    public T setMessageId(String messageId) {
        super.setMessageId(messageId);
        return self();
    }


    public T setVersion(String version) {
        super.setVersion(version);
        return self();
    }

    public T setTimestamp(long timestamp) {
        super.setTimestamp(timestamp);
        return self();
    }
}

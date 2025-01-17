package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 日志附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class SysLogAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private String ipAddress;
    private List<ObjectField> objectFields;
    private String failureReason;

    /**
     * 操作日志字段
     */
    @Data
    @Accessors(chain = true)
    public static class ObjectField {
        private String field;
        private String fieldName;
        private Object beforeValue;
        private Object afterValue;
        private boolean different;
    }
}

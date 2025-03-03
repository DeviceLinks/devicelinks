package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.DeviceLinksVersion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 遥测数据附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TelemetryAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private TelemetryMetadata metadata;

    @Data
    public static class TelemetryMetadata {
        private AttributeDataType dataType;
    }
}

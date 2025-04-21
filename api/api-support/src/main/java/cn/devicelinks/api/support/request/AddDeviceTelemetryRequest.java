package cn.devicelinks.api.support.request;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 新增设备遥测数据请求实体参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDeviceTelemetryRequest {

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "遥测数据标识符只允许包含字母、数字和下划线，且必须以字母开头")
    @Length(min = 1, max = 50, message = "遥测数据标识符长度必须在1 ~ 50个字符之间")
    private String identifier;

    @NotBlank(message = "遥测数据不允许为空.")
    private String value;

    @EnumValid(target = AttributeDataType.class, message = "遥测数据数据类型参数值非法")
    private String dataType;
}

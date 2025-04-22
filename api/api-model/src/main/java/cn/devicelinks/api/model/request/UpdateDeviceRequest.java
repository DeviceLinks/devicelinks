package cn.devicelinks.api.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 更新设备请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceRequest {
    @NotEmpty(message = "请选择设备配置文件")
    @Length(max = 24, message = "设备配置文件ID不可以超过24个字符")
    private String profileId;

    @Length(max = 30, message = "设备备注名称不可以超过30个字符")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "设备备注名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    private String noteName;

    private List<String> tags;

    @Length(max = 100, message = "备注不可以超过100个字符")
    private String mark;
}

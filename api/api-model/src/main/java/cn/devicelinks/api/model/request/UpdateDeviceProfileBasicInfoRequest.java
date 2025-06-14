package cn.devicelinks.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新设备配置文件基本信息请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceProfileBasicInfoRequest {
    @NotBlank
    @Length(max = 30, message = "设备配置文件名称不允许超过30个字符.")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "设备配置文件名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    private String name;

    private String productId;

    private String firmwareId;

    private String softwareId;

    @Length(max = 100, message = "设备配置文件描述不允许超过100个字符.")
    private String description;
}

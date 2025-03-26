package cn.devicelinks.console.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 新增设备标签请求参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDeviceTagRequest {

    @NotBlank(message = "设备标签名称不允许为空.")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "设备标签名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    private String name;
}

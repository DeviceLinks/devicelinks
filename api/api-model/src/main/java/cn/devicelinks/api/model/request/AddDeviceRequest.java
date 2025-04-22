package cn.devicelinks.api.model.request;

import cn.devicelinks.framework.common.DeviceCredentialsType;
import cn.devicelinks.framework.common.DeviceType;
import cn.devicelinks.framework.common.pojos.DeviceCredentialsAddition;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 添加新设备请求参数
 *
 * @author 恒宇少年
 */
@Data
public class AddDeviceRequest {
    @NotEmpty(message = "请选择设备所属产品")
    @Length(max = 24, message = "产品ID不可以超过24个字符")
    private String productId;

    @NotEmpty(message = "请选择部门")
    @Length(max = 24, message = "所属部门ID不可以超过24个字符")
    private String departmentId;

    @NotEmpty(message = "请选择设备配置文件")
    @Length(max = 24, message = "设备配置文件ID不可以超过24个字符")
    private String profileId;

    @NotEmpty(message = "设备类型不可以为空")
    @EnumValid(target = DeviceType.class, message = "设备类型参数非法")
    private String deviceType;

    @NotEmpty
    @Length(max = 50, message = "设备唯一名称不可以超过50个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-.:@]+$", message = "设备唯一名称只能包含英文字母、数字、下划线、中划线、点号、冒号和@符号")
    private String deviceName;

    @Length(max = 30, message = "设备备注名称不可以超过30个字符")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "设备备注名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    private String noteName;

    private List<String> tags;

    @Length(max = 100, message = "备注不可以超过100个字符")
    private String mark;

    @NotEmpty(message = "设备凭证类型不可以为空")
    @EnumValid(target = DeviceCredentialsType.class, message = "设备凭证类型参数非法")
    private String credentialsType;

    private DeviceCredentialsAddition credentialsAddition;
}

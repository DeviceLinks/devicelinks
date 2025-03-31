package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.DeviceType;
import cn.devicelinks.framework.common.pojos.DeviceAuthenticationAddition;
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
    // 设备信息
    @NotEmpty(message = "产品ID不可以为空")
    @Length(max = 32, message = "产品ID不可以超过32个字符")
    private String productId;

    @NotEmpty(message = "所属部门ID不可以为空")
    @Length(max = 32, message = "所属部门ID不可以超过32个字符")
    private String departmentId;

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

    // 鉴权
    @NotEmpty(message = "设备鉴权方式不可以为空")
    @EnumValid(target = DeviceAuthenticationMethod.class, message = "设备鉴权方式参数非法")
    private String authenticationMethod;

    private DeviceAuthenticationAddition authenticationAddition;
}

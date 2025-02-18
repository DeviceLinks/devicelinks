package cn.devicelinks.console.model.product;

import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新产品请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateProductRequest {
    @NotEmpty(message = "产品名称不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "产品名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头。")
    @Length(max = 30, message = "产品名称最多30个字符")
    private String name;

    @NotEmpty(message = "设备类型不能为空")
    @EnumValid(target = DeviceType.class, message = "设备类型参数非法")
    private String deviceType;

    @EnumValid(target = DeviceNetworkingAway.class, message = "网络方式参数非法")
    private String networkingAway;

    @EnumValid(target = AccessGatewayProtocol.class, message = "接入网关协议参数非法")
    private String accessGatewayProtocol;

    @NotEmpty(message = "数据格式不能为空")
    @EnumValid(target = DataFormat.class, message = "数据格式参数非法")
    private String dataFormat;

    @NotEmpty(message = "鉴权方式不能为空")
    @EnumValid(target = DeviceAuthenticationMethod.class, message = "鉴权方式参数非法")
    private String authenticationMethod;

    @Length(max = 100, message = "产品描述不可以超过100个字符")
    private String description;
}

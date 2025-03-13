package cn.devicelinks.console.web.request;

import cn.devicelinks.framework.common.ChartType;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 添加设备属性图表请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDeviceAttributeChartRequest {

    @NotBlank(message = "设备属性图表名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "设备属性图表名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头")
    @Length(max = 30, message = "设备属性图表名称最大支持30个字符")
    private String chartName;

    @NotBlank(message = "图表类型不可以为空")
    @EnumValid(target = ChartType.class, message = "图表类型参数值非法")
    private String chartType;

    @NotEmpty(message = "图表字段列表不可以为空")
    @Valid
    private List<AddDeviceAttributeChartRequest.ChartField> fields;

    @Data
    public static class ChartField {
        @NotBlank(message = "设备属性ID不可以为空")
        private String deviceAttributeId;

        @NotBlank(message = "字段描述不可以为空")
        private String fieldLabel;
    }
}

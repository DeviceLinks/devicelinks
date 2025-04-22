package cn.devicelinks.api.model.request;

import cn.devicelinks.common.DataChartType;
import cn.devicelinks.component.web.validator.EnumValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 添加遥测数据数据图表请求参数实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddTelemetryChartRequest {

    @NotBlank(message = "遥测数据图表名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "遥测数据图表名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头")
    @Length(max = 30, message = "遥测数据图表名称最大支持30个字符")
    private String chartName;

    @NotBlank(message = "图表类型不可以为空")
    @EnumValid(target = DataChartType.class, message = "图表类型参数值非法")
    private String chartType;

    @NotEmpty(message = "图表字段列表不可以为空")
    @Valid
    private List<ChartField> fields;

    @Data
    public static class ChartField {
        @NotBlank(message = "遥测数据ID不可以为空")
        private String telemetryId;

        @NotBlank(message = "字段描述不可以为空")
        private String fieldLabel;
    }
}

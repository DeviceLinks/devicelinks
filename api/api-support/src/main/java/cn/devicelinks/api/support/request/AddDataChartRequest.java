package cn.devicelinks.api.support.request;

import cn.devicelinks.framework.common.DataChartFieldType;
import cn.devicelinks.framework.common.DataChartTargetLocation;
import cn.devicelinks.framework.common.DataChartType;
import cn.devicelinks.framework.common.web.validator.EnumValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 添加数据图表请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class AddDataChartRequest {
    @NotBlank(message = "报表名称不可以为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9][\\u4e00-\\u9fa5a-zA-Z0-9_-]*$", message = "报表名称仅支持中文、大小写字母、数字、中横线、下划线，必须以中文、英文或数字开头")
    @Length(max = 30, message = "报表名称最大支持30个字符")
    private String chartName;

    @NotBlank(message = "报表类型不可以为空")
    @EnumValid(target = DataChartType.class, message = "报表类型参数值非法")
    private String chartType;

    @NotBlank(message = "图表所属目标ID不可以为空")
    private String targetId;

    @NotBlank(message = "图表所属目标位置不可以为空")
    @EnumValid(target = DataChartTargetLocation.class, message = "图表所属目标位置参数值非法")
    private String targetLocation;

    @Length(max = 100, message = "报表备注最大支持100个字符")
    private String mark;

    @NotEmpty(message = "图表字段列表不可以为空")
    @Valid
    private List<AddDataChartRequest.ChartField> fields;

    @Data
    public static class ChartField {
        @NotBlank(message = "字段ID不可以为空")
        private String fieldId;

        @NotBlank(message = "字段类型不可以为空")
        @EnumValid(target = DataChartFieldType.class, message = "字段类型参数值非法")
        private String fieldType;

        @NotBlank(message = "字段描述不可以为空")
        private String fieldLabel;
    }
}

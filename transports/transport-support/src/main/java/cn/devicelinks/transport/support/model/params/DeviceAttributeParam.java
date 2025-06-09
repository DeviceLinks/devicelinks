package cn.devicelinks.transport.support.model.params;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备上报属性的参数实体定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DeviceAttributeParam {

    @Valid
    @NotEmpty(message = "属性集合不可以为空, 至少上报一个属性.")
    private final List<AttributeValue> attributes = new ArrayList<>();

    @Data
    public static class AttributeValue {
        @NotBlank(message = "属性所属功能模块标识符参数不可以为空.")
        private String module;

        @NotBlank(message = "属性标识符参数不可以为空.")
        private String identifier;

        @NotBlank(message = "属性值参数不可为空.")
        private String value;

        @Min(value = 1, message = "属性值版本不可以小于1.")
        private int version;
    }
}

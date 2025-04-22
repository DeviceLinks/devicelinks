package cn.devicelinks.api.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 添加属性请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class AddAttributeRequest {

    @NotEmpty(message = "产品ID不可以为空")
    private String productId;

    @NotEmpty(message = "功能模块ID不可以为空")
    private String moduleId;

    @Valid
    private AttributeInfoRequest info;

    @Valid
    private List<AttributeInfoRequest> childAttributes;
}

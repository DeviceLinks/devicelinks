package cn.devicelinks.console.web.request;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * 更新属性请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateAttributeRequest {
    @Valid
    private AttributeInfoRequest info;

    @Valid
    private List<AttributeInfoRequest> childAttributes;
}

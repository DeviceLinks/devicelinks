package cn.devicelinks.framework.jdbc.model.dto;

import cn.devicelinks.framework.common.pojos.Attribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 属性数据传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttributeDTO extends Attribute {
    /**
     * 子属性列表
     */
    private List<Attribute> childAttributes;
}

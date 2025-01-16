package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据属性
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class Attribute implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    private String id;
    private String productId;
    private String moduleId;
    private String pid;
    private String name;
    private String identifier;
    private AttributeDataType dataType;
    private AttributeAddition addition;
    private boolean enabled;
    private boolean deleted;
    private String createBy;
    private LocalDateTime createTime;
    private String description;
}

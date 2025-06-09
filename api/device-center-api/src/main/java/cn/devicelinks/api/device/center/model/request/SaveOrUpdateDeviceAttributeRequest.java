package cn.devicelinks.api.device.center.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增或更新设备属性请求实体定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SaveOrUpdateDeviceAttributeRequest extends BaseDeviceRequest<SaveOrUpdateDeviceAttributeRequest> {

    private List<ReportedAttribute> attributes = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class ReportedAttribute {
        private String module;
        private String identifier;
        private String value;
        private int version;
    }
}

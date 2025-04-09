package cn.devicelinks.api.support.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新系统参数设置请求参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateSysSettingRequest {
    @NotEmpty
    @Length(max = 32)
    private String settingId;

    @NotEmpty
    private String value;
}

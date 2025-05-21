package cn.devicelinks.api.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量删除设备请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class BatchDeleteDeviceRequest {
    @NotEmpty(message = "请至少传递1台设备的ID")
    private List<String> deviceIds;
}

package cn.devicelinks.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 批量删除设备响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class BatchDeleteDeviceResponse {
    private Map<String, String> failureReasonMap;
}

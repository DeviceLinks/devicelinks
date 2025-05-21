package cn.devicelinks.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 批量更新设备状态响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class BatchUpdateDeviceEnabledResponse {
    private Map<String, String> failureReasonMap;
}

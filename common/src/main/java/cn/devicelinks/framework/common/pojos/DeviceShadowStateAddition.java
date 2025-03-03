package cn.devicelinks.framework.common.pojos;

import lombok.Data;

import java.util.Map;

/**
 * 设备影子状态附加数据
 *
 * <p>示例：
 * <pre>
 * {
 *     "state": {
 *         "humidity": 55,
 *         "temperature": 23
 *     },
 *     "metadata": {
 *         "temperature": {
 *             "timestamp": 171800003000,
 *             "attributeId": "1"
 *         }
 *     }
 * }
 * </pre>
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DeviceShadowStateAddition {
    private Map<String, Object> state;
    private Map<String, StateMetadata> metadata;

    @Data
    public static class StateMetadata {
        private String attributeId;
        private long timestamp;
    }
}
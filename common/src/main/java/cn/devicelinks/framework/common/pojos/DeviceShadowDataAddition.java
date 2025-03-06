package cn.devicelinks.framework.common.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设备影子数据附加信息
 * <pre>
 * {
 *     "module": "moduleA",
 *     "desired": {
 *         "state": {
 *             "temperature": 25
 *         },
 *         "metadata": {
 *             "temperature": {
 *                 "timestamp": 1741226222159,
 *                 "version": 1
 *             }
 *         }
 *     },
 *     "reported": {
 *         "state": {
 *             "temperature": 25
 *         },
 *         "metadata": {
 *             "temperature": {
 *                 "timestamp": 1741226270017,
 *                 "version": 1
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class DeviceShadowDataAddition {

    private String module;

    private ShadowReported reported = new ShadowReported();

    private ShadowDesired desired = new ShadowDesired();

    public DeviceShadowDataAddition(String module) {
        this.module = module;
    }

    /**
     * 设备影子上报数据
     */
    @Data
    public static class ShadowReported {
        private Map<String, Object> state = new LinkedHashMap<>();
        private Map<String, Metadata> metadata = new LinkedHashMap<>();
    }

    /**
     * 设备影子期望数据
     */
    @Data
    public static class ShadowDesired {
        private Map<String, Object> state = new LinkedHashMap<>();
        private Map<String, Metadata> metadata = new LinkedHashMap<>();
    }

    /**
     * 元数据
     */
    @Data
    @Accessors(chain = true)
    public static class Metadata {
        private long timestamp;
        private int version;

    }
}

package cn.devicelinks.framework.common.pojos;

import lombok.Data;

import java.util.Map;

/**
 * 设备影子数据附加信息
 * <p>示例：
 * <pre>
 * {
 *     "desired": {
 *         "state": {
 *             "humidity": 60,
 *             "temperature": 25
 *         },
 *         "metadata": {
 *             "humidity": {
 *                 "timestamp": 1718000001,
 *                 "attributeId": null
 *             },
 *             "temperature": {
 *                 "timestamp": 1718000000,
 *                 "attributeId": "xxxxxx"
 *             }
 *         }
 *     },
 *     "reported": {
 *         "state": {
 *             "humidity": 55,
 *             "temperature": 23
 *         },
 *         "metadata": {
 *             "temperature": {
 *                 "timestamp": 1718000030,
 *                 "attributeId": "xxxxxx"
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
public class DeviceShadowDataAddition {
    private Map<String, DeviceShadowStateAddition> reported;
    private Map<String, DeviceShadowStateAddition> desired;
}

package cn.devicelinks.framework.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据属性附加信息
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@Setter
public class DataPropertiesAddition {
    /**
     * 布尔值True映射Key
     */
    private static final String TRUE_VALUE = "1";
    /**
     * 布尔值False映射Key
     */
    private static final String FALSE_VALUE = "0";
    /**
     * 数据属性单位ID
     */
    private String unitId;
    /**
     * 数据步长
     */
    private int step;
    /**
     * 数据长度
     */
    private int dataLength;
    /**
     * 数据范围
     */
    private ValueRange valueRange;
    /**
     * 数据值映射
     */
    private Map<String, String> valueMap = new HashMap<>();

    @Setter
    @Getter
    public static class ValueRange {
        private int min;
        private int max;

    }
}

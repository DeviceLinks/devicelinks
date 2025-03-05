package cn.devicelinks.console.utils;

import cn.devicelinks.framework.common.EnumShowStyle;
import cn.devicelinks.framework.common.annotation.ApiEnum;
import cn.devicelinks.framework.common.utils.EnumUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static cn.devicelinks.framework.common.Constants.ENUM_OBJECT_LABEL_FIELD;
import static cn.devicelinks.framework.common.Constants.ENUM_SHOW_STYLE_FIELD;

/**
 * 接口枚举扫描器
 * <p>
 * 接口枚举上都会声明{@link ApiEnum}注解，标注该枚举可以通过"/api/common/enum"接口获取。
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class ApiEnumScanner {

    private static final String ENUM_SCAN_PACKAGE = "cn.devicelinks.framework.common";

    private static final Map<String, List<ApiEnumObject>> API_ENUM_MAP = new LinkedHashMap<>();

    static {
        Set<Class<? extends Enum>> enumClassSet = EnumUtils.getEnumsInPackage(ENUM_SCAN_PACKAGE, clazz -> clazz.isAnnotationPresent(ApiEnum.class));
        // @formatter:off
        for (Class<? extends Enum> enumClass : enumClassSet) {
            List<ApiEnumObject> enumValueObjectList = Arrays.stream(enumClass.getEnumConstants())
                    .map(enumConstant -> {
                        ApiEnumObject apiEnumObject = new ApiEnumObject()
                                .setLabel(enumConstant.toString())
                                .setShowStyle(EnumShowStyle.Default)
                                .setValue(enumConstant.name());

                        // All fields in enum
                        Map<String, Field> enumFields = Arrays
                                .stream(enumConstant.getClass().getDeclaredFields())
                                .collect(Collectors.toMap(Field::getName, v -> v));

                        // Set label
                        try {
                            if (enumFields.containsKey(ENUM_OBJECT_LABEL_FIELD)) {
                                Field field = enumFields.get(ENUM_OBJECT_LABEL_FIELD);
                                field.setAccessible(true);
                                String label = (String) field.get(enumConstant);
                                apiEnumObject.setLabel(!ObjectUtils.isEmpty(label) ? label : enumConstant.toString());
                            }
                        } catch (Exception e) {
                            log.error("Failed to get label for enum constant {} in class {}", enumConstant, enumClass, e);
                        }

                        // Set showStyle
                        try {
                            if(enumFields.containsKey(ENUM_SHOW_STYLE_FIELD)) {
                                Field field = enumConstant.getClass().getDeclaredField(ENUM_SHOW_STYLE_FIELD);
                                field.setAccessible(true);
                                EnumShowStyle showStyle = (EnumShowStyle) field.get(enumConstant);
                                apiEnumObject.setShowStyle(!ObjectUtils.isEmpty(showStyle) ? showStyle : EnumShowStyle.Default);
                            }
                        } catch (Exception e) {
                            log.error("Failed to get showStyle for enum constant {} in class {}", enumConstant, enumClass, e);
                        }

                        return apiEnumObject;
                    }).toList();
            // @formatter:on
            API_ENUM_MAP.put(enumClass.getSimpleName(), enumValueObjectList);
        }
    }

    public static Map<String, List<ApiEnumObject>> getAllApiEnums() {
        return API_ENUM_MAP;
    }


    @Data
    @Accessors(chain = true)
    public static class ApiEnumObject {
        private String label;
        private String value;
        private EnumShowStyle showStyle;
    }
}

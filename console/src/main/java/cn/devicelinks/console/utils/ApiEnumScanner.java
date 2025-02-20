package cn.devicelinks.console.utils;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import cn.devicelinks.framework.common.utils.EnumUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;

import static cn.devicelinks.framework.common.Constants.ENUM_OBJECT_LABEL_FIELD;

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
                                .setValue(enumConstant.name());
                        try {
                            Field field = enumConstant.getClass().getDeclaredField(ENUM_OBJECT_LABEL_FIELD);
                            field.setAccessible(true);
                            String description = (String) field.get(enumConstant);
                            apiEnumObject.setLabel(!ObjectUtils.isEmpty(description) ? description : enumConstant.toString());
                        } catch (Exception e) {
                            log.error("Failed to get description for enum constant {} in class {}", enumConstant, enumClass, e);
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
    }
}

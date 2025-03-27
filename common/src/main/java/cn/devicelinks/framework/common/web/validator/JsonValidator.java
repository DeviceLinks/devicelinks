package cn.devicelinks.framework.common.web.validator;

import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Json格式字符串验证器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class JsonValidator implements ConstraintValidator<JsonValid, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        if (value instanceof String) {
            try {
                String json = (String) value;
                DeviceLinksJsonMapper objectMapper = new DeviceLinksJsonMapper();
                objectMapper.readTree(json);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}

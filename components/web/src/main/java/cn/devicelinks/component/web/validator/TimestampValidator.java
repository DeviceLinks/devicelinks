package cn.devicelinks.component.web.validator;

import cn.devicelinks.common.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 时间戳数据验证器
 * <p>
 * 验证时间戳是否有效，包含：是否早于当前时间戳，验证对象为null不进行验证。
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class TimestampValidator implements ConstraintValidator<TimestampValid, Object> {
    @Override
    public boolean isValid(Object timestamp, ConstraintValidatorContext constraintValidatorContext) {
        if (timestamp == null) {
            return true;
        }
        try {
            long ts = Long.parseLong(timestamp.toString());
            long currentTime = System.currentTimeMillis();
            if (ts > Constants.ZERO && ts < currentTime && String.valueOf(ts).matches("^\\d{13}$")) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }
}

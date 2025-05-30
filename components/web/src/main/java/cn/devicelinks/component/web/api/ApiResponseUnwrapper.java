package cn.devicelinks.component.web.api;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * {@link ApiResponse}对象解析器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ApiResponseUnwrapper {
    public static <T> T unwrap(ApiResponse<T> response) {
        return tryUnwrap(response, null).orElse(null);
    }

    public static <T> T unwrap(ApiResponse<T> response, String errorMsg) {
        return tryUnwrap(response, errorMsg).orElse(null);
    }

    public static <T> Optional<T> tryUnwrap(ApiResponse<T> response, String errorMsg) {
        Assert.notNull(response, "ApiResponse实例为空，无法解析.");
        if (ApiResponse.SUCCESS.getCode().equals(response.getCode())) {
            return Optional.ofNullable(response.getData());
        } else {
            throw new ApiException(StatusCode.build(response.getCode(),
                    (!ObjectUtils.isEmpty(errorMsg) ? errorMsg : response.getMessage())));
        }
    }
}

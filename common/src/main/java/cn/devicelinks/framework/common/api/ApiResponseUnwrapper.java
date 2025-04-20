package cn.devicelinks.framework.common.api;

import cn.devicelinks.framework.common.exception.DeviceLinksException;
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
        return tryUnwrap(response)
                .orElseThrow(() -> new DeviceLinksException("Api接口请求失败，错误信息: " + response.getMessage()));
    }

    public static <T> T unwrap(ApiResponse<T> response, String errorMsg) {
        return tryUnwrap(response)
                .orElseThrow(() -> new DeviceLinksException("Api接口请求失败，错误信息: " + (!ObjectUtils.isEmpty(errorMsg) ? errorMsg : response.getMessage())));
    }

    public static <T> Optional<T> tryUnwrap(ApiResponse<T> response) {
        Assert.notNull(response, "ApiResponse实例为空，无法解析.");
        if (StatusCode.SUCCESS.getCode().equals(response.getCode())) {
            return Optional.ofNullable(response.getData());
        }
        return Optional.empty();
    }
}

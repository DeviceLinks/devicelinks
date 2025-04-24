package cn.devicelinks.transport.support;

import cn.devicelinks.component.web.api.ApiException;

/**
 * 令牌校验业务接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface TokenValidationService {
    /**
     * 验证令牌
     *
     * @param token 请求时携带的明文令牌
     * @return 返回true时表示验证通过，返回false时验证不通过
     */
    TokenValidationResponse validationToken(String token) throws ApiException;
}

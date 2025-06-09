package cn.devicelinks.transport.http.authorization.endpoint.credentials;

import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationExceptionFailureHandler;
import cn.devicelinks.component.web.ApiResponseHttpMessageConverter;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.StatusCode;
import cn.devicelinks.transport.support.TransportStatusCodes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static cn.devicelinks.api.support.StatusCodeConstants.BUSINESS_EXCEPTION;
import static cn.devicelinks.api.support.StatusCodeConstants.UNKNOWN_ERROR;

/**
 * 设备动态令牌颁发过滤器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceDynamicTokenIssuedFilter extends OncePerRequestFilter {

    private static final String PARAMETER_DEVICE_ID = "deviceId";

    private static final String PARAMETER_DEVICE_NAME = "deviceName";

    private static final String PARAMETER_TIMESTAMP = "timestamp";

    private static final String PARAMETER_SIGN_ALGORITHM = "signAlgorithm";

    private static final String PARAMETER_SIGN = "sign";

    private final StatusCode DEVICE_ID_CANNOT_EMPTY = StatusCode.build("DEVICE_ID_CANNOT_EMPTY", "[" + PARAMETER_DEVICE_ID + "]不可以为空.");

    private final StatusCode DEVICE_NAME_CANNOT_EMPTY = StatusCode.build("DEVICE_NAME_CANNOT_EMPTY", "[" + PARAMETER_DEVICE_NAME + "]不可以为空.");

    private final StatusCode TIMESTAMP_CANNOT_EMPTY = StatusCode.build("TIMESTAMP_CANNOT_EMPTY", "[" + PARAMETER_TIMESTAMP + "]不可以为空.");

    private final StatusCode SIGN_CANNOT_EMPTY = StatusCode.build("SIGN_CANNOT_EMPTY", "[" + PARAMETER_SIGN + "]不可以为空.");

    private final AuthenticationManager authenticationManager;

    private final RequestMatcher requestMatcher;

    private final HttpMessageConverter<ApiResponse<?>> httpMessageConverter = new ApiResponseHttpMessageConverter();

    private final AuthenticationConverter authenticationConverter = this::createAuthentication;

    private final AuthenticationSuccessHandler authenticationSuccessHandler = this::sendDynamicTokenResponse;

    private final AuthenticationFailureHandler authenticationFailureHandler = new DeviceLinksAuthorizationExceptionFailureHandler();

    public DeviceDynamicTokenIssuedFilter(AuthenticationManager authenticationManager, RequestMatcher requestMatcher) {
        this.authenticationManager = authenticationManager;
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            DeviceDynamicTokenIssuedAuthenticationToken authRequest = (DeviceDynamicTokenIssuedAuthenticationToken) authenticationConverter.convert(request);
            DeviceDynamicTokenIssuedAuthenticationToken dynamicTokenIssuedAuthenticationToken =
                    (DeviceDynamicTokenIssuedAuthenticationToken) this.authenticationManager.authenticate(authRequest);
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, dynamicTokenIssuedAuthenticationToken);
        } catch (AuthenticationException e) {
            DeviceLinksAuthorizationException deviceLinksAuthorizationException = new DeviceLinksAuthorizationException(e.getMessage());
            if (e instanceof DeviceLinksAuthorizationException) {
                deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) e;
            }
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, deviceLinksAuthorizationException);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            DeviceLinksAuthorizationException deviceLinksAuthorizationException = new DeviceLinksAuthorizationException(UNKNOWN_ERROR);
            if (e instanceof ApiException apiException) {
                deviceLinksAuthorizationException = new DeviceLinksAuthorizationException(BUSINESS_EXCEPTION, apiException.getMessage());
            }
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, deviceLinksAuthorizationException);
        }
    }

    private Authentication createAuthentication(HttpServletRequest request) throws AuthenticationException {
        String deviceId = request.getParameter(PARAMETER_DEVICE_ID);
        if (ObjectUtils.isEmpty(deviceId)) {
            throw new DeviceLinksAuthorizationException(DEVICE_ID_CANNOT_EMPTY);
        }
        String deviceName = request.getParameter(PARAMETER_DEVICE_NAME);
        if (ObjectUtils.isEmpty(deviceName)) {
            throw new DeviceLinksAuthorizationException(DEVICE_NAME_CANNOT_EMPTY);
        }
        String timestamp = request.getParameter(PARAMETER_TIMESTAMP);
        if (ObjectUtils.isEmpty(timestamp)) {
            throw new DeviceLinksAuthorizationException(TIMESTAMP_CANNOT_EMPTY);
        }
        String signAlgorithm = request.getParameter(PARAMETER_SIGN_ALGORITHM);
        HmacSignatureAlgorithm hmacSignatureAlgorithm;
        if (!ObjectUtils.isEmpty(signAlgorithm)) {
            try {
                hmacSignatureAlgorithm = HmacSignatureAlgorithm.valueOf(signAlgorithm);
            } catch (Exception e) {
                throw new DeviceLinksAuthorizationException(TransportStatusCodes.SIGN_ALGORITHM_NOT_SUPPORT);
            }
        } else {
            hmacSignatureAlgorithm = HmacSignatureAlgorithm.HmacSHA256;
        }
        String sign = request.getParameter(PARAMETER_SIGN);
        if (ObjectUtils.isEmpty(sign)) {
            throw new DeviceLinksAuthorizationException(SIGN_CANNOT_EMPTY);
        }
        return DeviceDynamicTokenIssuedAuthenticationToken.unauthenticated(request, deviceId, deviceName, timestamp, hmacSignatureAlgorithm, sign);
    }

    private void sendDynamicTokenResponse(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws IOException {
        DeviceDynamicTokenIssuedAuthenticationToken authenticationToken = (DeviceDynamicTokenIssuedAuthenticationToken) authentication;
        IssuedDynamicTokenResponse dynamicTokenResponse = new IssuedDynamicTokenResponse(authenticationToken.getToken(), authenticationToken.getExpiresAt());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.httpMessageConverter.write(ApiResponse.success(dynamicTokenResponse), null, httpResponse);
    }
}

package cn.devicelinks.component.authorization;

import cn.devicelinks.component.web.ApiResponseHttpMessageConverter;
import cn.devicelinks.component.web.api.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * The {@link DeviceLinksAuthorizationException} AuthenticationFailureHandler Impl
 *
 * @author 恒宇少年
 * @see AuthenticationFailureHandler
 * @see DeviceLinksAuthorizationException
 * @since 1.0
 */
public class DeviceLinksAuthorizationExceptionFailureHandler implements AuthenticationFailureHandler {

    private final HttpMessageConverter<ApiResponse<?>> httpMessageConverter = new ApiResponseHttpMessageConverter();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        DeviceLinksAuthorizationException deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) authenticationException;
        ApiResponse<?> errorResponse = ApiResponse.error(deviceLinksAuthorizationException.getStatusCode(), deviceLinksAuthorizationException.getMessageVariables());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.httpMessageConverter.write(errorResponse, null, httpResponse);
    }
}

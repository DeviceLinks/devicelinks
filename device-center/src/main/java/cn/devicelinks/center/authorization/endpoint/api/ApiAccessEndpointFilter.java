package cn.devicelinks.center.authorization.endpoint.api;

import cn.devicelinks.center.authorization.ApiKeyAuthenticationRequest;
import cn.devicelinks.center.authorization.ApiKeyResolver;
import cn.devicelinks.center.authorization.DefaultApiKeyResolver;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.authorization.DeviceLinksAuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Api访问认证端点过滤器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ApiAccessEndpointFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private final AuthenticationFailureHandler authenticationFailureHandler = this::sendErrorResponse;

    private final ApiKeyResolver apiKeyResolver = new DefaultApiKeyResolver();

    private final HttpMessageConverter<ApiResponse> resourceAccessHttpMessageConverter = new ApiAccessHttpMessageConverter();

    public ApiAccessEndpointFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            ApiKeyAuthenticationRequest apiKeyAuthenticationRequest = this.apiKeyResolver.resolve(request);
            // @formatter:off
            ApiAccessAuthenticationToken apiAccessAuthenticationToken =
                    new ApiAccessAuthenticationToken(
                            apiKeyAuthenticationRequest.getApiKey(),
                            apiKeyAuthenticationRequest.getSign(),
                            apiKeyAuthenticationRequest.getTimestamp(),
                            request);
            // @formatter:on
            apiAccessAuthenticationToken = (ApiAccessAuthenticationToken) this.authenticationManager.authenticate(apiAccessAuthenticationToken);
            apiAccessAuthenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(apiAccessAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (DeviceLinksAuthorizationException invalid) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, invalid);
        } catch (Exception e) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new DeviceLinksAuthorizationException(StatusCode.SYSTEM_EXCEPTION));
        }
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException authenticationException) throws IOException {
        DeviceLinksAuthorizationException deviceLinksAuthorizationException = (DeviceLinksAuthorizationException) authenticationException;
        ApiResponse errorResponse = ApiResponse.error(deviceLinksAuthorizationException.getStatusCode());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.resourceAccessHttpMessageConverter.write(errorResponse, null, httpResponse);
    }
}

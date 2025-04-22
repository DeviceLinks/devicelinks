package cn.devicelinks.transport.http.authorization.endpoint.access;

import cn.devicelinks.component.authorization.DeviceLinksAuthorizationException;
import cn.devicelinks.component.authorization.DeviceLinksAuthorizationExceptionFailureHandler;
import cn.devicelinks.component.web.resolver.BearerTokenResolver;
import cn.devicelinks.component.web.resolver.DefaultBearerTokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static cn.devicelinks.api.support.StatusCodeConstants.INVALID_TOKEN;
import static cn.devicelinks.api.support.StatusCodeConstants.SYSTEM_EXCEPTION;

/**
 * 设备携带令牌访问的过滤器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceTokenAccessFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private final AuthenticationFailureHandler authenticationFailureHandler = new DeviceLinksAuthorizationExceptionFailureHandler();

    public DeviceTokenAccessFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.bearerTokenResolver.resolve(request);
            if (ObjectUtils.isEmpty(token)) {
                throw new DeviceLinksAuthorizationException(INVALID_TOKEN);
            }

            // Create unauthenticated DeviceTokenAccessAuthenticationToken
            DeviceTokenAccessAuthenticationToken deviceTokenAccessAuthenticationToken = DeviceTokenAccessAuthenticationToken.unauthenticated(token);

            deviceTokenAccessAuthenticationToken =
                    (DeviceTokenAccessAuthenticationToken) this.authenticationManager.authenticate(deviceTokenAccessAuthenticationToken);

            deviceTokenAccessAuthenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(deviceTokenAccessAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (DeviceLinksAuthorizationException invalid) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, invalid);
        } catch (Exception e) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new DeviceLinksAuthorizationException(SYSTEM_EXCEPTION));
        }
    }
}

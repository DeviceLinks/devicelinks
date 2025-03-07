/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.common.api;

import cn.devicelinks.framework.common.exception.ApiException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 统一接口异常通知类
 *
 * @author 恒宇少年
 * @see ApiException
 * @see MethodArgumentNotValidException
 * @since 1.0
 */
@RestControllerAdvice
@ResponseBody
@ResponseStatus(HttpStatus.OK)
@Slf4j
public class ApiExceptionAdvice {
    public static StatusCode PARAM_INVALID = StatusCode.build("PARAM_INVALID", "参数验证失败.");
    public static StatusCode PARAM_INVALID_WITH_MSG = StatusCode.build("PARAM_INVALID_WITH_MSG", "参数验证失败，提示：%s.");
    public static StatusCode REQUEST_VARIABLES_INVALID = StatusCode.build("REQUEST_VARIABLES_INVALID", "请求参数值非法.");
    public static StatusCode REQUEST_BODY_UNABLE_PARSE = StatusCode.build("REQUEST_BODY_UNABLE_PARSE_CODE", "请求主体无法解析.");
    public static StatusCode SYSTEM_EXCEPTION_STATUS = StatusCode.build("SYSTEM_EXCEPTION", "系统开小差啦.");
    public static StatusCode HTTP_METHOD_NOT_SUPPORT = StatusCode.build("HTTP_METHOD_NOT_SUPPORT", "不支持请求方法：%s.");
    public static StatusCode NO_RESOURCE_FOUND = StatusCode.build("NO_RESOURCE_FOUND", "资源([%s] /%s )不存在，无法访问.");
    public static StatusCode PARAMETER_MISSING = StatusCode.build("PARAMETER_MISSING", "参数[%s]并未传递, 该参数必须传递.");
    public static StatusCode AUTHORIZATION_DENIED = StatusCode.build("AUTHORIZATION_DENIED", "无权限访问.");

    @Autowired
    private MessageSource messageSource;

    /**
     * 处理遇到的{@link ApiException}异常
     *
     * @param exception {@link ApiException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(ApiException.class)
    public ApiResponse handleApiException(ApiException exception) {
        String errorMsg = exception.getStatusCode().formatMessage(exception.getMessageVariables());
        log.error(errorMsg, exception);
        return ApiResponse.error(exception.getStatusCode(), exception.getMessageVariables());
    }

    /**
     * 处理遇到的{@link AuthorizationDeniedException}异常
     *
     * @param exception {@link AuthorizationDeniedException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ApiResponse handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(AUTHORIZATION_DENIED);
    }

    /**
     * 处理遇到的{@link MissingServletRequestParameterException}异常
     *
     * @param exception {@link MissingServletRequestParameterException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ApiResponse.error(PARAMETER_MISSING, exception.getParameterName());
    }

    /**
     * 处理遇到的{@link MethodArgumentNotValidException}异常
     *
     * @param exception {@link MethodArgumentNotValidException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        try {
            String message = this.getErrorFieldMessage(exception.getBindingResult());
            return ApiResponse.error(StatusCode.build(PARAM_INVALID.getCode(), message));
        } catch (Exception e) {
            return ApiResponse.error(SYSTEM_EXCEPTION_STATUS);
        }
    }

    /**
     * 处理遇到的{@link HandlerMethodValidationException}异常
     *
     * @param exception {@link HandlerMethodValidationException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ApiResponse handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(REQUEST_VARIABLES_INVALID);
    }

    /**
     * 处理遇到的{@link BindException}异常
     *
     * @param exception {@link BindException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException exception) {
        String message = this.getErrorFieldMessage(exception.getBindingResult());
        return ApiResponse.error(StatusCode.build(PARAM_INVALID.getCode(), message));
    }

    /**
     * 处理遇到{@link UnexpectedTypeException}异常
     *
     * @param exception {@link UnexpectedTypeException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler({UnexpectedTypeException.class, MethodArgumentTypeMismatchException.class})
    public ApiResponse handleUnexpectedTypeException(Exception exception) {
        if (exception instanceof MethodArgumentTypeMismatchException argumentTypeMismatchException) {
            String errorMsg = "Parameter: " + argumentTypeMismatchException.getName() + ", Unacceptable value: " + argumentTypeMismatchException.getValue() + ".";
            return ApiResponse.error(PARAM_INVALID_WITH_MSG, errorMsg);
        }
        return ApiResponse.error(PARAM_INVALID);
    }

    /**
     * 处理遇到{@link ConstraintViolationException}异常
     *
     * @param exception {@link ConstraintViolationException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException exception) {
        return ApiResponse.error(PARAM_INVALID, exception);
    }

    /**
     * 处理遇到{@link HttpMessageNotReadableException}异常
     *
     * @param exception {@link HttpMessageNotReadableException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ApiResponse.error(REQUEST_BODY_UNABLE_PARSE);
    }

    /**
     * 处理遇到{@link HttpRequestMethodNotSupportedException}异常
     *
     * @param exception {@link HttpRequestMethodNotSupportedException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return ApiResponse.error(HTTP_METHOD_NOT_SUPPORT, exception.getMethod());
    }

    /**
     * 处理遇到{@link NoResourceFoundException}异常
     *
     * @param exception {@link NoResourceFoundException}异常对象实例
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse noResourceFoundException(NoResourceFoundException exception) {
        return ApiResponse.error(NO_RESOURCE_FOUND, exception.getHttpMethod().name(), exception.getResourcePath());
    }

    /**
     * 处理其他异常
     *
     * @param exception {@link Exception}
     * @return {@link ApiResponse}
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception exception) {
        log.error("System Exception", exception);
        return ApiResponse.error(SYSTEM_EXCEPTION_STATUS);
    }

    /**
     * 获取全部验证不通过字段格式化后的错误信息
     *
     * @param bindingResult The {@link BindingResult} instance
     * @return 格式化后全部验证不通过的字段异常内容
     */
    private String getErrorFieldMessage(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // @formatter:off
        return fieldErrors.stream()
                .map(fieldError -> {
                    Locale currentLocale = LocaleContextHolder.getLocale();
                    return fieldError.getField() + ":" + messageSource.getMessage(fieldError, currentLocale);
                }).collect(Collectors.joining(";"));
        // @formatter:on
    }
}

package cn.devicelinks.console.model;

import cn.devicelinks.framework.common.api.StatusCode;

/**
 * The {@link StatusCode} Constants
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface StatusCodeConstants {
    StatusCode USER_NOT_FOUND = StatusCode.build("USER_NOT_FOUND", "用户：%s，不存在.");
    StatusCode USER_ALREADY_EXISTS = StatusCode.build("USER_ALREADY_EXISTS","用户已经存在，请检查账号、邮箱、手机号是否重复.");
    StatusCode USER_EMAIL_CANNOT_EMPTY = StatusCode.build("USER_EMAIL_CANNOT_EMPTY","用户邮箱地址不允许为空.");
}

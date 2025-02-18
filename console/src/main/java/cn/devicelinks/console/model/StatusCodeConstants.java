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

package cn.devicelinks.console.model;

import cn.devicelinks.framework.common.api.StatusCode;

/**
 * The {@link StatusCode} Constants
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface StatusCodeConstants {
    /**
     * User
     */
    StatusCode USER_NOT_FOUND = StatusCode.build("USER_NOT_FOUND", "用户：%s，不存在.");
    StatusCode USER_ALREADY_EXISTS = StatusCode.build("USER_ALREADY_EXISTS", "用户已经存在，请检查账号、邮箱、手机号是否重复.");
    StatusCode USER_EMAIL_CANNOT_EMPTY = StatusCode.build("USER_EMAIL_CANNOT_EMPTY", "用户邮箱地址不允许为空.");

    /**
     * Department
     */
    StatusCode DEPARTMENT_NOT_FOUND = StatusCode.build("DEPARTMENT_NOT_FOUND", "部门：%s，不存在.");
    StatusCode DEPARTMENT_ALREADY_EXISTS = StatusCode.build("DEPARTMENT_ALREADY_EXISTS", "部门已经存在，请检查名称、标识符是否重复.");
    StatusCode DEPARTMENT_PARENT_NOT_EXISTS = StatusCode.build("DEPARTMENT_PARENT_NOT_EXISTS", "上级部门不存在.");

    /**
     * Function Module
     */
    StatusCode FUNCTION_MODULE_NOT_FOUND = StatusCode.build("FUNCTION_MODULE_NOT_FOUND", "功能模块：%s，不存在.");
    StatusCode FUNCTION_MODULE_ALREADY_EXISTS = StatusCode.build("FUNCTION_MODULE_ALREADY_EXISTS", "功能模块已经存在，请检查名称、标识符是否重复.");

    /**
     * Product
     */
    StatusCode PRODUCT_NOT_EXISTS = StatusCode.build("PRODUCT_NOT_EXISTS", "产品：%s，不存在.");
    StatusCode PRODUCT_ALREADY_EXISTS = StatusCode.build("PRODUCT_ALREADY_EXISTS", "产品：%s，已经存在，请检查名称、ProductKey是否重复.");
    StatusCode SEARCH_FIELD_REQUIRED_NOT_PRESENT = StatusCode.build("SEARCH_FIELD_REQUIRED_NOT_PRESENT", "检索字段：[%s]，必须全部传递.");
    StatusCode PRODUCT_NOT_IN_DEVELOPMENT_STATUS = StatusCode.build("PRODUCT_NOT_IN_DEVELOPMENT_STATUS", "产品：%s，不在开发状态，无法操作.");
    StatusCode PRODUCT_PUBLISHED = StatusCode.build("PRODUCT_PUBLISHED", "产品：%s，已发布，无法操作.");
    StatusCode PRODUCT_HAS_RELATED_DEVICES = StatusCode.build("PRODUCT_HAS_RELATED_DEVICES", "产品：%s，存在关联的设备，无法删除.");

    StatusCode ATTRIBUTE_NOT_FOUND = StatusCode.build("ATTRIBUTE_NOT_FOUND", "属性：%s，不存在.");
}

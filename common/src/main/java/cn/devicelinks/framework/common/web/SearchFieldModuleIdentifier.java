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

package cn.devicelinks.framework.common.web;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum SearchFieldModuleIdentifier {
    /**
     * 用户
     */
    User("用户模块"),
    /**
     * 设备
     */
    Device("设备模块"),
    /**
     * 日志
     */
    Log("日志模块"),
    /**
     * 通知
     */
    Notification("通知模块"),
    /**
     * 功能模块
     */
    FunctionModule("产品功能模块"),
    /**
     * 属性
     */
    Attribute("属性模块"),
    /**
     * 产品
     */
    Product("产品模块"),
    /**
     * 设备遥测数据
     */
    DeviceTelemetry("设备遥测数据"),
    /**
     * 设备属性
     */
    DeviceAttribute("设备属性"),
    /**
     * 设备期望属性
     */
    DeviceDesiredAttribute("设备期望属性");

    private final String description;

    SearchFieldModuleIdentifier(String description) {
        this.description = description;
    }
}

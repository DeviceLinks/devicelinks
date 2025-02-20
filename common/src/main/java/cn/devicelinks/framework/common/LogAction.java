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

package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 记录日志的动作定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum LogAction {
    Login("登录", true, true),
    Logout("登出", true, true),
    Add("新增", false, true),
    Update("编辑", true, true),
    UpdateStatus("更新状态", true, true),
    ChangePwd("修改密码", true, true),
    Delete("删除", true, false),
    Bind("绑定", false, true),
    Unbind("解绑", true, false),
    Publish("发布", true, true),
    RegenerateKeySecret("重新生成KeySecret", true, true);

    private final String description;
    private final boolean haveBeforeData;
    private final boolean haveAfterData;

    LogAction(String description, boolean haveBeforeData, boolean haveAfterData) {
        this.description = description;
        this.haveBeforeData = haveBeforeData;
        this.haveAfterData = haveAfterData;
    }
}

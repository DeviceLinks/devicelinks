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

import lombok.Getter;

/**
 * 记录日志的动作定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum LogAction {
    /**
     * 登录
     */
    Login(true, true),
    /**
     * 登出
     */
    Logout(true, true),
    /**
     * 新增
     */
    Add(false, true),
    /**
     * 编辑
     */
    Update(true, true),
    /**
     * 更新启用/状态
     */
    UpdateStatus(true, true),
    /**
     * 修改密码
     */
    ChangePwd(true, true),
    /**
     * 删除
     */
    Delete(true, false),
    /**
     * 绑定
     */
    Bind(false, true),
    /**
     * 解绑
     */
    Unbind(true, false),
    /**
     * 发布
     */
    Publish(true, true),
    /**
     * 重新生成KeySecret
     */
    RegenerateKeySecret(true, true);
    /**
     * 是否存在操作之前的数据
     * <p>
     * 如：当动作为"Add"时，则不存在操作之前的数据了；当动作为"Update"时，则存在操作之前的数据
     */
    private final boolean haveBeforeData;
    /**
     * 是否存在操作之后的数据
     * <p>
     * 如：当动作为"Delete"时（物理删除），则不存在操作之前的数据
     */
    private final boolean haveAfterData;

    LogAction(boolean haveBeforeData, boolean haveAfterData) {
        this.haveBeforeData = haveBeforeData;
        this.haveAfterData = haveAfterData;
    }
}

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
 * OTA升级策略重试间隔
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum OtaUpgradeStrategyRetryInterval {
    /**
     * 不重试
     */
    Not("不重试"),
    /**
     * 立即重试
     */
    Immediately("立即重试"),
    /**
     * 10分钟后重试
     */
    Minute10("10分钟后重试"),
    /**
     * 30分钟后重试
     */
    Minute30("30分钟后重试"),
    /**
     * 1小时后重试
     */
    Hour1("1小时后重试"),
    /**
     * 24小时后重试
     */
    Hour24("24小时后重试"),
    ;
    private final String description;

    OtaUpgradeStrategyRetryInterval(String description) {
        this.description = description;
    }
}

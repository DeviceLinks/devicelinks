/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.common.operate.log;

import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 操作日志对象类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class OperationLogObject {
    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 操作动作
     */
    private LogAction operateAction;
    /**
     * 操作对象类型
     */
    private LogObjectType objectType;
    /**
     * 操作对象
     */
    private String object;
    /**
     * 操作对象字段列表
     */
    private String objectFields;
    /**
     * 操作描述
     */
    private String msg;
    /**
     * 目标方法是否执行成功
     */
    private boolean executionSucceed;
    /**
     * 日志所关联的操作人编号
     */
    private String operatorId;
    /**
     * IP地址
     */
    private String ipAddress;
    /**
     * 失败原因
     */
    private String failureReason;
    /**
     * 日志生成的时间
     */
    private LocalDateTime time = LocalDateTime.now();
}

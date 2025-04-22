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

package cn.devicelinks.console.component;

import cn.devicelinks.component.operate.log.OperationLogObject;
import cn.devicelinks.component.operate.log.OperationLogStorage;
import cn.devicelinks.component.jackson.utils.JacksonUtils;
import cn.devicelinks.entity.SysLog;
import cn.devicelinks.entity.SysLogAddition;
import cn.devicelinks.service.system.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.util.StackTraceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志数据存储实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class OperationLogStorageSupport implements OperationLogStorage {
    @Autowired
    private SysLogService logService;

    @Override
    public void storage(OperationLogObject object) {
        // @formatter:off
        SysLog operateLog = new SysLog()
                .setUserId(object.getOperatorId())
                .setSessionId(object.getSessionId())
                .setAction(object.getAction())
                .setObjectType(object.getObjectType())
                .setObjectId(object.getObjectId())
                .setMsg(object.getMsg())
                .setSuccess(object.isExecutionSucceed())
                .setCreateTime(object.getTime())
                .setAddition(
                        new SysLogAddition()
                                .setIpAddress(object.getIpAddress())
                                .setOs(object.getOs())
                                .setBrowser(object.getBrowser())
                                .setFailureReason(object.getFailureReason())
                                .setFailureStackTrace(object.getFailureCause() != null ? StackTraceUtil.getStackTrace(object.getFailureCause()) : null)
                                .setBeforeObject(object.getBeforeObject() != null ? JacksonUtils.objectToJson(object.getBeforeObject()) : null)
                                .setAfterObject(object.getAfterObject() != null ? JacksonUtils.objectToJson(object.getAfterObject()) : null)
                )
                .setActivityData(object.getActivateData());
        // @formatter:on
        this.logService.insert(operateLog);
    }
}

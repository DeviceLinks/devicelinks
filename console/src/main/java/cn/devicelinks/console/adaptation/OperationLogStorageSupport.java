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

package cn.devicelinks.console.adaptation;

import cn.devicelinks.console.service.SysLogService;
import cn.devicelinks.framework.common.operate.log.OperationLogObject;
import cn.devicelinks.framework.common.operate.log.OperationLogStorage;
import cn.devicelinks.framework.common.pojos.SysLog;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
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
    private SysLogService operateLogService;

    @Override
    public void storage(OperationLogObject object) {
        // @formatter:off
        SysLog operateLog = new SysLog()
                .setId(UUIDUtils.generateNoDelimiter())
                //.setTenantId(UserAdditionContextHolder.getContext().getUser().getTenantId())
                .setUserId(object.getOperatorId())
                .setResourceCode(object.getResourceCode().toString())
                .setAction(object.getOperateAction())
                .setObjectType(object.getObjectType())
                .setObject(object.getObject())
                .setMsg(object.getMsg())
                .setObjectFields(object.getObjectFields())
                .setSuccess(object.isExecutionSucceed())
                .setFailureReason(object.getFailureReason())
                .setIpAddress(object.getIpAddress())
                .setOperateTime(object.getTime());
        // @formatter:on
        this.operateLogService.insert(operateLog);
    }
}

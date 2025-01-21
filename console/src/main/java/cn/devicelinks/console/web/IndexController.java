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

package cn.devicelinks.console.web;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.ObjectAdditionField;
import cn.devicelinks.framework.common.operate.log.ObjectField;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.SysUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
public class IndexController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SysUserService userService;

    @PostMapping(value = "/post")
    @OperationLog(action = LogAction.Add, objectType = LogObjectType.User,
            objectId = "{#p0.userId}",
            object = "{@sysUserJdbcRepository.selectOne(#p0.userId)}",
            activateData = "{#p0}",
            msg = "更新了用户 {#p0.name} 基本信息.",
            additionFields = {
                    @ObjectAdditionField(field = ObjectField.user_department, value = "{@sysDepartmentJdbcRepository.selectOne(#p0.departmentId).getName()}")
            }
    )
    public ApiResponse post(@RequestBody @Valid PostBody body) {
        userService.selectByAccount("admin");
        SysUser sysUser = UserDetailsContext.getCurrentUser();
        return ApiResponse.success(sysUser);
    }

    @PostMapping(value = "/update")
    @OperationLog(action = LogAction.Update, objectType = LogObjectType.User,
            objectId = "{#before.id}",
            object = "{@sysUserJdbcRepository.selectOne(#p0.userId)}",
            activateData = "{#p0}",
            msg = "更新了用户 {#before.account} 基本信息.",
            additionFields = {
                    @ObjectAdditionField(field = ObjectField.user_department,
                            condition = "{#target!=null && #target.departmentId!=null}",
                            preValueLoad = "{@sysDepartmentJdbcRepository.selectOne(#target.departmentId)}",
                            value = "{#pre0!=null ? #pre0.getName() : null}"),
                    @ObjectAdditionField(field = ObjectField.user_department,
                            condition = "{#target!=null && #target.positionId!=null}",
                            preValueLoad = "{@sysPositionJdbcRepository.selectOne(#target.positionId)}",
                            value = "{#pre0!=null ? #pre0.getName() : null}")
            }
    )
    public ApiResponse update(@RequestBody @Valid PostBody body) {
        SysUser user = userService.selectById(body.getUserId());
        user.setName(user.getName() + "_1");
        userService.update(user);
        return ApiResponse.success(user);
    }

    @GetMapping(value = "/users")
    public ApiResponse getAccount() {
        return ApiResponse.success(userService.selectAll());
    }

    @GetMapping
    public ApiResponse index(@RequestParam("age") @Valid @Min(value = 10, message = "年龄最小为10岁.") Integer age) {
        if (age < 10) {
            throw new ApiException(StatusCode.build("-1", "error"));
        }
        return ApiResponse.success(StatusCode.build("0", "success"));
    }

    @Data
    public static class PostBody {
        private String userId = "f88ad32b644511efbab90242ac110002";
        @NotEmpty
        private String name;
        @Min(value = 10, message = "年龄不能小于10岁")
        private int age;
        private String departmentId = "5667c16f637611efbab90242ac110002";
        private String positionId;
    }
}

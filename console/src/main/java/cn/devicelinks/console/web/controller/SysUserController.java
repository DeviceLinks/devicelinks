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

package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddUserRequest;
import cn.devicelinks.console.web.request.UpdateUserRequest;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.authorization.AuthorizedUserAddition;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.common.utils.StringUtils;
import cn.devicelinks.framework.jdbc.model.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class SysUserController {
    private static final int ACTIVATE_TOKEN_LENGTH = 50;
    private final SysUserService userService;

    /**
     * 分页获取用户列表
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     * @return 用户列表 {@link UserDTO}
     */
    @PostMapping(value = "/filter")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    public ApiResponse getUsers(@Valid PaginationQuery paginationQuery, @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        return ApiResponse.success(this.userService.getUsers(searchFieldQuery, paginationQuery));
    }

    /**
     * 获取用户
     *
     * @param userId 用户ID {@link SysUser#getId()}
     * @return 用户实例 {@link SysUser}
     */
    @GetMapping(value = "/{userId}")
    public ApiResponse getUserById(@Valid @PathVariable("userId") @Length(max = 32) String userId) throws ApiException {
        return ApiResponse.success(this.userService.selectById(userId));
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户实例 {@link SysUser}
     */
    @GetMapping(value = "/me")
    public ApiResponse getCurrentUser() throws ApiException {
        AuthorizedUserAddition userAddition = UserDetailsContext.getUserAddition();
        return ApiResponse.success(userAddition);
    }

    /**
     * 添加用户
     *
     * @param request {@link AddUserRequest}
     * @return 添加后的用户实例 {@link SysUser}
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.User,
            objectId = "{#executionSucceed ? #result.data.id : #p0.account}",
            msg = "{#executionSucceed ? '用户添加成功' : '用户添加失败'}",
            activateData = "{#p0}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    public ApiResponse addUser(@Valid @RequestBody AddUserRequest request) throws ApiException {
        if (UserActivateMethod.SendUrlToEmail.toString().equals(request.getActivateMethod()) && ObjectUtils.isEmpty(request.getEmail())) {
            throw new ApiException(StatusCodeConstants.USER_EMAIL_CANNOT_EMPTY);
        }
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        // @formatter:off
        SysUser user = new SysUser()
                .setAccount(request.getAccount())
                .setName(request.getUsername())
                .setEmail(request.getEmail())
                .setActivateToken(StringUtils.getRandomString(ACTIVATE_TOKEN_LENGTH))
                .setDepartmentId(ObjectUtils.isEmpty(request.getDepartmentId()) ? currentUser.getDepartmentId() : request.getDepartmentId())
                .setCreateBy(currentUser.getId())
                .setMark(request.getMark());
        // @formatter:on
        user = this.userService.addUser(user, UserActivateMethod.valueOf(request.getActivateMethod()));
        return ApiResponse.success(user);
    }

    /**
     * 更新用户
     *
     * @param userId  用户ID {@link SysUser#getId()}
     * @param request 更新用户参数实体 {@link UpdateUserRequest}
     * @return 更新后的用户 {@link SysUser}
     */
    @PostMapping(value = "/{userId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.User,
            objectId = "{#p0}",
            object = "{@sysUserServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '用户更新成功' : '用户更新失败'}",
            activateData = "{#p1}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    public ApiResponse updateUser(@Valid @PathVariable("userId") @Length(max = 32) String userId,
                                  @Valid @RequestBody UpdateUserRequest request) throws ApiException {
        SysUser storedUser = this.userService.selectById(userId);
        if (ObjectUtils.isEmpty(storedUser)) {
            throw new ApiException(StatusCodeConstants.USER_NOT_FOUND, userId);
        }
        // @formatter:off
        storedUser.setName(request.getUsername())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone())
                .setDepartmentId(request.getDepartmentId())
                .setMark(request.getMark());
        // @formatter:on
        this.userService.updateUser(storedUser);
        return ApiResponse.success(storedUser);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID {@link SysUser#getId()}
     */
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.User,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '用户删除成功' : '用户删除失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    @DeleteMapping(value = "/{userId}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    public ApiResponse deleteUser(@Valid @PathVariable("userId") @Length(max = 32) String userId) throws ApiException {
        SysUser storedUser = this.userService.selectById(userId);
        if (ObjectUtils.isEmpty(storedUser)) {
            throw new ApiException(StatusCodeConstants.USER_NOT_FOUND, userId);
        }
        this.userService.deleteUser(userId);
        return ApiResponse.success(storedUser);
    }

    /**
     * 启用/禁用用户状态
     *
     * @param userId  用户ID {@link SysUser#getId()}
     * @param enabled 是否启用
     */
    @OperationLog(action = LogAction.UpdateStatus,
            objectType = LogObjectType.User,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '用户状态更新成功' : '用户状态更新失败'}",
            activateData = "{#p1}")
    @PostMapping(value = "/status/{userId}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    public ApiResponse updateStatus(@Valid @PathVariable("userId") @Length(max = 32) String userId,
                                    @RequestParam boolean enabled) throws ApiException {
        SysUser storedUser = this.userService.selectById(userId);
        if (ObjectUtils.isEmpty(storedUser)) {
            throw new ApiException(StatusCodeConstants.USER_NOT_FOUND, userId);
        }
        this.userService.updateEnabled(userId, enabled);
        return ApiResponse.success();
    }
}

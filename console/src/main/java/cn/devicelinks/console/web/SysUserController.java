package cn.devicelinks.console.web;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.model.*;
import cn.devicelinks.console.model.page.PageRequest;
import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.UserActivateMethod;
import cn.devicelinks.framework.common.UserIdentity;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    private final SysUserService userService;

    /**
     * 分页获取用户列表
     *
     * @param query    查询参数实体实例 {@link UsersQuery}
     * @param pageSize 每页条数
     * @param page     当前页码
     * @return 用户列表 {@link UserDTO}
     */
    @GetMapping
    public ApiResponse getUsers(@Valid UsersQuery query,
                                @RequestParam int pageSize,
                                @RequestParam int page) throws ApiException {
        PageRequest pageRequest = new PageRequest(pageSize, page);
        PageResult<UserDTO> userPageResult = this.userService.getUsers(query, pageRequest);
        return ApiResponse.success(userPageResult);
    }

    /**
     * 获取用户
     *
     * @param userId 用户ID {@link SysUser#getId()}
     * @return 用户实例 {@link SysUser}
     */
    @GetMapping(value = "/{userId}")
    public ApiResponse getUserById(@PathVariable("userId") String userId) throws ApiException {
        SysUser user = this.userService.selectById(userId);
        return ApiResponse.success(user);
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
            objectId = "{#result.data.id}",
            object = "{@sysUserServiceImpl.selectById(#result.data.id)}")
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
                .setActivateToken(SecureRandomString.getActiveToken())
                .setIdentity(UserIdentity.User)
                .setDepartmentId(ObjectUtils.isEmpty(request.getDepartmentId()) ? currentUser.getDepartmentId() : request.getDepartmentId())
                .setCreateBy(currentUser.getId())
                .setCreateTime(LocalDateTime.now())
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
            object = "{@sysUserServiceImpl.selectById(#p0)}")
    public ApiResponse updateUser(@PathVariable("userId") String userId,
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
            object = "{@sysUserServiceImpl.selectById(#p0)}")
    @DeleteMapping(value = "/{userId}")
    public ApiResponse deleteUser(@PathVariable("userId") String userId) throws ApiException {
        this.userService.deleteUser(userId);
        return ApiResponse.success();
    }

    /**
     * 启用/禁用用户状态
     *
     * @param userId  用户ID {@link SysUser#getId()}
     * @param enabled 是否启用
     */
    @OperationLog(action = LogAction.UpdateStatus,
            objectType = LogObjectType.User,
            objectId = "{#p0}")
    @PostMapping(value = "/status/{userId}")
    public ApiResponse updateStatus(@PathVariable("userId") String userId,
                                    @RequestParam boolean enabled) throws ApiException {
        this.userService.updateEnabled(userId, enabled);
        return ApiResponse.success();
    }
}

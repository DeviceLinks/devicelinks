package cn.devicelinks.console.web;

import cn.devicelinks.framework.jdbc.model.dto.UserDTO;
import cn.devicelinks.console.model.parameter.GetUserListParameter;
import cn.devicelinks.console.service.SysUserService;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @GetMapping
    public ApiResponse getUserListByPageable(@Valid @RequestBody GetUserListParameter parameter) throws ApiException {
        PageResult<UserDTO> userPageResult = this.userService.selectByPageable(parameter);
        return ApiResponse.success(userPageResult);
    }

    @GetMapping(value = "/{userId}")
    public ApiResponse getUser(@PathVariable("userId") String userId) throws ApiException {
        SysUser user = this.userService.selectById(userId);
        return ApiResponse.success(user);
    }
}

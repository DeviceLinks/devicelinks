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

package cn.devicelinks.console.web;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.model.department.AddDepartmentRequest;
import cn.devicelinks.console.model.StatusCodeConstants;
import cn.devicelinks.console.model.department.UpdateDepartmentRequest;
import cn.devicelinks.console.service.SysDepartmentService;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.SysDepartment;
import cn.devicelinks.framework.common.pojos.SysUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 部门相关接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/department")
@RequiredArgsConstructor
public class SysDepartmentController {

    private final SysDepartmentService departmentService;

    /**
     * 获取部门信息
     *
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     * @return {@link SysDepartment}
     */
    @GetMapping(value = "/{departmentId}")
    public ApiResponse getDepartmentById(@PathVariable @Valid @Length(max = 32) String departmentId) throws ApiException {
        return ApiResponse.success(this.departmentService.selectById(departmentId));
    }

    /**
     * 新增部门
     *
     * @param request 新增部门请求实体参数 {@link AddDepartmentRequest}
     * @return 新增后的部门实例对象 {@link SysDepartment}
     */
    @PostMapping
    @PreAuthorize("hasAuthority('SystemAdmin')")
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Department,
            objectId = "{#executionSucceed ? #result.data.id : #p0.name}",
            msg = "{#executionSucceed ? '新增部门成功' : '新增部门失败'}",
            activateData = "{#p0}")
    public ApiResponse addDepartment(@RequestBody @Valid AddDepartmentRequest request) throws ApiException {
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        // @formatter:off
        SysDepartment department = new SysDepartment()
                .setName(request.getName())
                .setIdentifier(request.getIdentifier())
                .setPid(request.getPid())
                .setSort(request.getSort())
                .setLevel(request.getLevel())
                .setDescription(request.getDescription())
                .setCreateBy(currentUser.getId())
                .setCreateTime(LocalDateTime.now());
        // @formatter:off
        department = this.departmentService.addDepartment(department);
        return ApiResponse.success(department);
    }

    /**
     * 编辑部门
     *
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     * @param request      编辑部门请求实体参数 {@link UpdateDepartmentRequest}
     * @return 编辑后的部门实例对象 {@link SysDepartment}
     */
    @PostMapping(value = "/{departmentId}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.Department,
            objectId = "{#p0}",
            object = "{@sysDepartmentServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '编辑部门成功' : '编辑部门失败'}",
            activateData = "{#p1}")
    public ApiResponse updateDepartment(@PathVariable @Valid @Length(max = 32) String departmentId,
                                        @RequestBody @Valid UpdateDepartmentRequest request) throws ApiException {
        SysDepartment storedDepartment = this.departmentService.selectById(departmentId);
        if (storedDepartment == null) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_NOT_FOUND);
        }
        // @formatter:off
        storedDepartment.setName(request.getName())
                .setPid(request.getPid())
                .setSort(request.getSort())
                .setDescription(request.getDescription());
        // @formatter:on
        storedDepartment = this.departmentService.updateDepartment(storedDepartment);
        return ApiResponse.success(storedDepartment);
    }

    /**
     * 删除部门
     *
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     * @return 删除后的部门实例对象 {@link SysDepartment}
     */
    @DeleteMapping(value = "/{departmentId}")
    @PreAuthorize("hasAuthority('SystemAdmin')")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Department,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '删除部门成功' : '删除部门失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse deleteDepartment(@PathVariable String departmentId) throws ApiException {
        SysDepartment storedDepartment = this.departmentService.selectById(departmentId);
        if (storedDepartment == null) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_NOT_FOUND);
        }
        this.departmentService.deleteDepartment(departmentId);
        return ApiResponse.success(storedDepartment);
    }
}

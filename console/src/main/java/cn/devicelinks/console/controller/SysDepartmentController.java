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

package cn.devicelinks.console.controller;

import cn.devicelinks.api.model.request.AddDepartmentRequest;
import cn.devicelinks.api.model.request.UpdateDepartmentRequest;
import cn.devicelinks.api.model.response.DepartmentTree;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.service.system.SysDepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 查询部门列表
     *
     * @return 返回未被删除的部门列表，不进行分页
     * @throws ApiException 查询过程中遇到的异常
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Department)
    public ApiResponse<List<SysDepartment>> getAllDepartments(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        List<SysDepartment> sysDepartmentList = this.departmentService.selectList(searchFieldQuery);
        return ApiResponse.success(sysDepartmentList);
    }

    /**
     * 查询部门列表，返回Tree结构
     *
     * @return 返回未被删除的部门列表，不进行分页
     * @throws ApiException 查询过程中遇到的异常
     */
    @PostMapping(value = "/tree/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Department)
    public ApiResponse<List<DepartmentTree.DepartmentTreeNode>> getDepartmentTree(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        List<SysDepartment> sysDepartmentList = this.departmentService.selectList(searchFieldQuery);
        DepartmentTree departmentTree = DepartmentTree.with(sysDepartmentList).buildTree();
        return ApiResponse.success(departmentTree.getNodeList());
    }


    /**
     * 获取部门信息
     *
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     * @return {@link SysDepartment}
     */
    @GetMapping(value = "/{departmentId}")
    public ApiResponse<SysDepartment> getDepartmentById(@PathVariable @Valid @Length(max = 32) String departmentId) throws ApiException {
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
    public ApiResponse<SysDepartment> addDepartment(@RequestBody @Valid AddDepartmentRequest request) throws ApiException {
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        // @formatter:off
        SysDepartment department = new SysDepartment()
                .setName(request.getName())
                .setIdentifier(request.getIdentifier())
                .setPid(request.getPid())
                .setSort(request.getSort())
                .setLevel(request.getLevel())
                .setDescription(request.getDescription())
                .setCreateBy(currentUser.getId());
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
    public ApiResponse<SysDepartment> updateDepartment(@PathVariable @Valid @Length(max = 32) String departmentId,
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
    public ApiResponse<SysDepartment> deleteDepartment(@PathVariable String departmentId) throws ApiException {
        SysDepartment storedDepartment = this.departmentService.selectById(departmentId);
        if (storedDepartment == null) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_NOT_FOUND);
        }
        this.departmentService.deleteDepartment(departmentId);
        return ApiResponse.success(storedDepartment);
    }
}

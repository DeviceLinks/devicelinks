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

package cn.devicelinks.service.system;

import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.jdbc.BaseService;

import java.util.List;

/**
 * 部门业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysDepartmentService extends BaseService<SysDepartment, String> {

    /**
     * 查询部门列表
     *
     * @param searchFieldQuery 查询字段封装对象
     * @return 部门列表 {@link SysDepartment}
     */
    List<SysDepartment> selectList(SearchFieldQuery searchFieldQuery);

    /**
     * 添加部门
     * @param department {@link SysDepartment}
     * @return 已添加部门的对象 {@link SysDepartment}
     */
    SysDepartment addDepartment(SysDepartment department);

    /**
     * 编辑部门
     * @param department {@link SysDepartment}
     * @return 已编辑部门的对象 {@link SysDepartment}
     */
    SysDepartment updateDepartment(SysDepartment department);

    /**
     * 删除部门
     * @param departmentId 部门ID {@link SysDepartment#getId()}
     */
    void deleteDepartment(String departmentId);
}

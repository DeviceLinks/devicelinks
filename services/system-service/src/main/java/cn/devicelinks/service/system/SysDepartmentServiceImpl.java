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

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.SysDepartment;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.cache.SysDepartmentCacheEvictEvent;
import cn.devicelinks.jdbc.cache.SysDepartmentCacheKey;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.jdbc.repository.SysDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TSysDepartment.SYS_DEPARTMENT;

/**
 * 部门业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysDepartmentServiceImpl extends CacheBaseServiceImpl<SysDepartment, String, SysDepartmentRepository, SysDepartmentCacheKey, SysDepartmentCacheEvictEvent> implements SysDepartmentService {
    public SysDepartmentServiceImpl(SysDepartmentRepository repository) {
        super(repository);
    }

    @Override
    @TransactionalEventListener(classes = SysDepartmentCacheEvictEvent.class)
    public void handleCacheEvictEvent(SysDepartmentCacheEvictEvent event) {
        SysDepartment savedDepartment = event.getSavedDepartment();
        if (savedDepartment != null) {
            cache.put(SysDepartmentCacheKey.builder().departmentId(savedDepartment.getId()).build(), savedDepartment);
        } else {
            List<SysDepartmentCacheKey> toEvict = new ArrayList<>(1);
            if (!ObjectUtils.isEmpty(event.getDepartmentId())) {
                toEvict.add(SysDepartmentCacheKey.builder().departmentId(event.getDepartmentId()).build());
            }
            cache.evict(toEvict);
        }
    }

    @Override
    public List<SysDepartment> selectList(SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditions = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        return this.repository.selectListWithSearchConditions(searchFieldConditions);
    }

    @Override
    public SysDepartment addDepartment(SysDepartment department) {
        SysDepartment storedDepartment = this.check(department, false);
        if (storedDepartment != null) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_ALREADY_EXISTS);
        }
        this.repository.insert(department);
        return department;
    }

    @Override
    public SysDepartment updateDepartment(SysDepartment department) {
        SysDepartment storedDepartment = this.check(department, true);
        if (storedDepartment != null && !storedDepartment.getId().equals(department.getId())) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_ALREADY_EXISTS);
        }
        this.repository.update(department);
        publishCacheEvictEvent(SysDepartmentCacheEvictEvent.builder().departmentId(department.getId()).build());
        return department;
    }

    @Override
    public void deleteDepartment(String departmentId) {
        this.repository.update(
                List.of(SYS_DEPARTMENT.DELETED.set(true)),
                SYS_DEPARTMENT.ID.eq(departmentId)
        );
        publishCacheEvictEvent(SysDepartmentCacheEvictEvent.builder().departmentId(departmentId).build());
    }

    private SysDepartment check(SysDepartment department, boolean doUpdate) {
        // check parent department exists
        if (!ObjectUtils.isEmpty(department.getPid()) && ObjectUtils.isEmpty(this.selectById(department.getPid()))) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_PARENT_NOT_EXISTS);
        }
        // check already exists
        List<ConditionGroup> conditionGroups = new ArrayList<>();

        // update
        if (doUpdate) {
            conditionGroups.add(ConditionGroup.withCondition(SqlFederationAway.OR,
                    SYS_DEPARTMENT.NAME.eq(department.getName()),
                    SYS_DEPARTMENT.IDENTIFIER.eq(department.getIdentifier())));
            conditionGroups.add(ConditionGroup.withCondition(SYS_DEPARTMENT.ID.neq(department.getId())));
        }
        // insert
        else {
            conditionGroups.add(ConditionGroup.withCondition(SqlFederationAway.OR,
                    SYS_DEPARTMENT.NAME.eq(department.getName()),
                    SYS_DEPARTMENT.IDENTIFIER.eq(department.getIdentifier())));
        }
        return this.repository.selectOne(conditionGroups.toArray(ConditionGroup[]::new));
    }
}

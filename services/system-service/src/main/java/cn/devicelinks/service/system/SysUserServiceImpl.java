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
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.common.UserActivateMethod;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.api.model.dto.UserDTO;
import cn.devicelinks.jdbc.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TSysUser.SYS_USER;

/**
 * 用户业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String, SysUserRepository> implements SysUserService {

    public SysUserServiceImpl(SysUserRepository repository) {
        super(repository);
    }

    @Override
    public SysUser addUser(SysUser sysUser, UserActivateMethod userActivateMethod) {
        SysUser storedUser = this.checkUserAlreadyExists(sysUser, false);
        if (storedUser != null) {
            throw new ApiException(StatusCodeConstants.USER_ALREADY_EXISTS);
        }
        sysUser.setActivateMethod(userActivateMethod);
        this.insert(sysUser);
        if (UserActivateMethod.SendUrlToEmail == userActivateMethod) {
            // TODO 通过发送邮件方式激活时，需要向邮箱发送账号激活邮件
        }
        return sysUser;
    }

    @Override
    public SysUser updateUser(SysUser sysUser) {
        SysUser storedUser = this.checkUserAlreadyExists(sysUser, true);
        if (storedUser != null && !storedUser.getId().equals(sysUser.getId())) {
            throw new ApiException(StatusCodeConstants.USER_ALREADY_EXISTS);
        }
        this.update(sysUser);
        return sysUser;
    }

    @Override
    public SysUser selectByAccount(String account) {
        return this.repository.selectByAccount(account);
    }

    @Override
    public void updateLastLoginTime(String userId, LocalDateTime lastLoginTime) {
        // @formatter:off
        this.repository.update(
                List.of(SYS_USER.LAST_LOGIN_TIME.set(lastLoginTime)),
                SYS_USER.ID.eq(userId));
        // @formatter:on
    }

    @Override
    public PageResult<UserDTO> getUsers(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.selectByPage(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public void deleteUser(String userId) {
        this.repository.update(
                List.of(SYS_USER.DELETED.set(true)),
                SYS_USER.ID.eq(userId));
    }

    @Override
    public void updateEnabled(String userId, boolean enabled) {
        this.repository.update(
                List.of(SYS_USER.ENABLED.set(enabled)),
                SYS_USER.ID.eq(userId));
    }

    private SysUser checkUserAlreadyExists(SysUser sysUser, boolean doUpdate) {
        // check already exists
        List<ConditionGroup> conditionGroups = new ArrayList<>();

        // update
        if (doUpdate) {
            conditionGroups.add(ConditionGroup.withCondition(SqlFederationAway.OR,
                    SYS_USER.EMAIL.eq(sysUser.getEmail()),
                    SYS_USER.PHONE.eq(sysUser.getPhone())));
            conditionGroups.add(ConditionGroup.withCondition(SYS_USER.ID.neq(sysUser.getId())));
        }
        // insert
        else {
            conditionGroups.add(ConditionGroup.withCondition(SqlFederationAway.OR,
                    SYS_USER.ACCOUNT.eq(sysUser.getAccount()),
                    SYS_USER.EMAIL.eq(sysUser.getEmail()),
                    SYS_USER.PHONE.eq(sysUser.getPhone())));
        }
        return this.repository.selectOne(conditionGroups.toArray(ConditionGroup[]::new));
    }
}

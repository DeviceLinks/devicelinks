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

package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.UserIdentity;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.SysUser;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link SysUser} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TSysUser extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TSysUser SYS_USER = new TSysUser("sys_user");

    private TSysUser(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column ACCOUNT = Column.withName("account").build();
    public final Column EMAIL = Column.withName("email").build();
    public final Column PHONE = Column.withName("phone").build();
    public final Column PWD = Column.withName("pwd").build();
    public final Column ACTIVATE_METHOD = Column.withName("activate_method").typeMapper(ColumnValueMappers.USER_ACTIVATE_METHOD).build();
    public final Column ACTIVATE_TOKEN = Column.withName("activate_token").build();
    public final Column DEPARTMENT_ID = Column.withName("department_id").build();
    public final Column IDENTITY = Column.withName("identity").typeMapper(ColumnValueMappers.USER_IDENTITY).defaultValue(() -> UserIdentity.User).build();
    public final Column LAST_LOGIN_TIME = Column.withName("last_login_time").localDateTimeValue().build();
    public final Column LAST_CHANGE_PWD_TIME = Column.withName("last_change_pwd_time").localDateTimeValue().build();
    public final Column ENABLED = Column.withName("enabled").booleanValue().defaultValue(() -> Boolean.TRUE).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();
    public final Column MARK = Column.withName("mark").build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, ACCOUNT, EMAIL, PHONE, PWD, ACTIVATE_METHOD, ACTIVATE_TOKEN, DEPARTMENT_ID, IDENTITY, LAST_LOGIN_TIME, LAST_CHANGE_PWD_TIME, ENABLED, DELETED, CREATE_BY, CREATE_TIME, MARK);
    }
}
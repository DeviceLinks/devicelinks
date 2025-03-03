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

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAuthentication;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.sql.DynamicWrapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceAuthentication.DEVICE_AUTHENTICATION;

/**
 * The {@link DeviceAuthentication} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAuthenticationJdbcRepository extends JdbcRepository<DeviceAuthentication, String> implements DeviceAuthenticationRepository {
    public DeviceAuthenticationJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_AUTHENTICATION, jdbcOperations);
    }

    @Override
    public DeviceAuthentication selectByDeviceId(String deviceId) {
        return this.selectOne(DEVICE_AUTHENTICATION.DEVICE_ID.eq(deviceId), DEVICE_AUTHENTICATION.DELETED.eq(Boolean.FALSE));
    }

    @Override
    public DeviceAuthentication selectByAccessToken(String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new IllegalArgumentException("accessToken can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(DEVICE_AUTHENTICATION.getQuerySql())
                .resultColumns(columns -> columns.addAll(DEVICE_AUTHENTICATION.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.accessToken') = ?", accessToken)
                .resultType(DeviceAuthentication.class)
                .build();
        // @formatter:on
        List<DeviceAuthentication> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }

    @Override
    public DeviceAuthentication selectByClientId(String clientId) {
        if (ObjectUtils.isEmpty(clientId)) {
            throw new IllegalArgumentException("clientId can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(DEVICE_AUTHENTICATION.getQuerySql())
                .resultColumns(columns -> columns.addAll(DEVICE_AUTHENTICATION.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.mqttBasic.clientId') = ?", clientId)
                .resultType(DeviceAuthentication.class)
                .build();
        // @formatter:on
        List<DeviceAuthentication> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }

    @Override
    public DeviceAuthentication selectByDeviceKey(String deviceKey) {
        if (ObjectUtils.isEmpty(deviceKey)) {
            throw new IllegalArgumentException("deviceKey can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(DEVICE_AUTHENTICATION.getQuerySql())
                .resultColumns(columns -> columns.addAll(DEVICE_AUTHENTICATION.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.deviceCredential.deviceKey') = ?", deviceKey)
                .resultType(DeviceAuthentication.class)
                .build();
        // @formatter:on
        List<DeviceAuthentication> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }
}

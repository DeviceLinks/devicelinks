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

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceCredentials;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.jdbc.tables.TDeviceCredentials;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceCredentials.DEVICE_CREDENTIALS;

/**
 * The {@link DeviceCredentials} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceCredentialsJdbcRepository extends JdbcRepository<DeviceCredentials, String> implements DeviceCredentialsRepository {
    public DeviceCredentialsJdbcRepository(JdbcOperations jdbcOperations) {
        super(TDeviceCredentials.DEVICE_CREDENTIALS, jdbcOperations);
    }

    @Override
    public DeviceCredentials selectByDeviceId(String deviceId) {
        return this.selectOne(TDeviceCredentials.DEVICE_CREDENTIALS.DEVICE_ID.eq(deviceId), TDeviceCredentials.DEVICE_CREDENTIALS.DELETED.eq(Boolean.FALSE));
    }

    @Override
    public DeviceCredentials selectByToken(String token) {
        if (ObjectUtils.isEmpty(token)) {
            throw new IllegalArgumentException("accessToken can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(TDeviceCredentials.DEVICE_CREDENTIALS.getQuerySql())
                .resultColumns(columns -> columns.addAll(TDeviceCredentials.DEVICE_CREDENTIALS.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.token') = ?", token)
                .resultType(DeviceCredentials.class)
                .build();
        // @formatter:on
        List<DeviceCredentials> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }

    @Override
    public DeviceCredentials selectByClientId(String clientId) {
        if (ObjectUtils.isEmpty(clientId)) {
            throw new IllegalArgumentException("clientId can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(TDeviceCredentials.DEVICE_CREDENTIALS.getQuerySql())
                .resultColumns(columns -> columns.addAll(TDeviceCredentials.DEVICE_CREDENTIALS.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.mqttBasic.clientId') = ?", clientId)
                .resultType(DeviceCredentials.class)
                .build();
        // @formatter:on
        List<DeviceCredentials> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }

    @Override
    public DeviceCredentials selectByDeviceSecret(String deviceSecret) {
        if (ObjectUtils.isEmpty(deviceSecret)) {
            throw new IllegalArgumentException("deviceSecret can't be null");
        }
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(DEVICE_CREDENTIALS.getQuerySql())
                .resultColumns(columns -> columns.addAll(DEVICE_CREDENTIALS.getColumns()))
                .appendCondition(Boolean.TRUE, "json_extract(addition, '$.dynamicToken.deviceSecret') = ?", deviceSecret)
                .resultType(DeviceCredentials.class)
                .build();
        // @formatter:on
        List<DeviceCredentials> authenticationList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return authenticationList.isEmpty() ? null : authenticationList.getFirst();
    }
}

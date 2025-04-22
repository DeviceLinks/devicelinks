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

import cn.devicelinks.common.DeviceType;
import cn.devicelinks.entity.Device;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.*;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.jdbc.tables.TDevice;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.devicelinks.jdbc.tables.TDevice.DEVICE;

/**
 * The {@link Device} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceJdbcRepository extends JdbcRepository<Device, String> implements DeviceRepository {

    public DeviceJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE, jdbcOperations);
    }

    @Override
    public PageResult<Device> selectByPageable(List<SearchFieldCondition> searchFieldConditionList,
                                               PageQuery pageQuery,
                                               SortCondition sortCondition) {
        // @formatter:off
        Condition[] conditions = this.searchFieldConditionToConditionArray(searchFieldConditionList);
        FusionCondition fusionCondition = FusionCondition
                .withSort(sortCondition)
                .conditions(conditions)
                .build();
        // @formatter:on
        return this.page(fusionCondition, pageQuery);
    }

    @Override
    public void clearDeviceProfileId(String profileId) {
        Assert.hasText(profileId, "The profileId cannot be empty");
        this.update(List.of(TDevice.DEVICE.PROFILE_ID.set(null)), TDevice.DEVICE.PROFILE_ID.eq(profileId));
    }

    @Override
    public void updateDeviceProfileIdWithDeviceIds(String profileId, List<String> deviceIds) {
        this.update(List.of(TDevice.DEVICE.PROFILE_ID.set(profileId)),
                TDevice.DEVICE.ID.in(deviceIds.stream().map(deviceId -> (Object) deviceId).toList()));
    }

    @Override
    public void updateDeviceProfileIdWithTags(String productId, String profileId, List<String> tags) {
        String tagSql = tags.stream().map(tag -> "find_in_set(?, tags) > 0").collect(Collectors.joining(SqlFederationAway.OR.getValue()));
        DynamicWrapper dynamicWrapper = DynamicWrapper.modify("update device set profile_id = ?")
                .parameters(params -> params.add(profileId))
                .appendCondition(!ObjectUtils.isEmpty(tagSql), "( " + tagSql + " )", tags)
                .appendCondition(!ObjectUtils.isEmpty(productId), SqlFederationAway.AND, TDevice.DEVICE.PRODUCT_ID.eq(productId))
                .build();
        this.dynamicModify(dynamicWrapper.dynamic(), dynamicWrapper.parameters());
    }

    @Override
    public void updateDeviceProfileIdWithDeviceType(String productId, String profileId, DeviceType deviceType) {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(TDevice.DEVICE.DEVICE_TYPE.eq(deviceType));
        if (!ObjectUtils.isEmpty(productId)) {
            conditions.add(TDevice.DEVICE.PRODUCT_ID.eq(productId));
        }
        this.update(List.of(TDevice.DEVICE.PROFILE_ID.set(profileId)), conditions.toArray(Condition[]::new));
    }
}

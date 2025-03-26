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
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;

/**
 * The {@link Device} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceJdbcRepository extends JdbcRepository<Device, String> implements DeviceRepository {

    private static final String CLEAR_DEVICE_PROFILE_ID_SQL = "update device set profile_id = null where profile_id = ?";

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
        Dynamic dynamic = Dynamic.buildModify(CLEAR_DEVICE_PROFILE_ID_SQL, List.of(DEVICE.PRODUCT_ID));
        this.dynamicModify(dynamic, profileId);
    }
}

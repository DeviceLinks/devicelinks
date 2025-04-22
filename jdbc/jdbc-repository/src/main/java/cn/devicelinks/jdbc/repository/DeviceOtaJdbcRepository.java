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

import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.entity.DeviceOta;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.definition.DynamicColumn;
import cn.devicelinks.jdbc.core.sql.Dynamic;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceOta.DEVICE_OTA;
import static cn.devicelinks.jdbc.tables.TFunctionModule.FUNCTION_MODULE;

/**
 * The {@link DeviceOta} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceOtaJdbcRepository extends JdbcRepository<DeviceOta, String> implements DeviceOtaRepository {
    // @formatter:off
	private static final String SELECT_DEVICE_OTA_DTO_SQL =
			"select fm.id module_id, fm.identifier module_identifier, do.id ota_id,do.latest_version ota_version" +
			" from device_ota do" +
			" left join function_module fm on fm.id = do.module_id" +
            " where do.device_id = ?";
	// @formatter:on

    public DeviceOtaJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_OTA, jdbcOperations);
    }

    @Override
    public List<DeviceFunctionModuleOtaDTO> selectByDeviceId(String deviceId) {
        // @formatter:off
		DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_DEVICE_OTA_DTO_SQL)
				.resultColumns(resultColumns -> {
                    resultColumns.add(DynamicColumn.withColumn(FUNCTION_MODULE.ID).alias("module_id").build());
                    resultColumns.add(DynamicColumn.withColumn(FUNCTION_MODULE.IDENTIFIER).alias("module_identifier").build());
                    resultColumns.add(DynamicColumn.withColumn(DEVICE_OTA.ID).alias("ota_id").build());
                    resultColumns.add(DynamicColumn.withColumn(DEVICE_OTA.LATEST_VERSION).alias("ota_version").build());
				});
		// @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(DeviceFunctionModuleOtaDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.dynamicSelect(dynamic, deviceId);
    }
}

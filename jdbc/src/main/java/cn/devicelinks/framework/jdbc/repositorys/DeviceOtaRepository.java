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

import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.framework.common.pojos.DeviceOta;
import cn.devicelinks.framework.jdbc.core.Repository;

import java.util.List;

/**
 * The {@link DeviceOta} Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceOtaRepository extends Repository<DeviceOta, String> {
    /**
     * 查询指定设备各个功能模块的ota版本
     *
     * @param deviceId 设备ID {@link DeviceOta#getDeviceId()}
     * @return {@link DeviceFunctionModuleOtaDTO}
     */
    List<DeviceFunctionModuleOtaDTO> selectByDeviceId(String deviceId);
}

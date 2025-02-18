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

package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceService;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;

/**
 * 设备业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceServiceImpl extends BaseServiceImpl<Device, String, DeviceRepository> implements DeviceService {
    public DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public List<Device> selectByProductId(String productId) {
        Assert.hasText(productId, "productId不能为空");
        return this.repository.select(DEVICE.PRODUCT_ID.eq(productId), DEVICE.DELETED.eq(Boolean.FALSE));
    }
}

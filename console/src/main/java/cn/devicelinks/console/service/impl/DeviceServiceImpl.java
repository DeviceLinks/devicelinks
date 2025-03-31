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

import cn.devicelinks.console.service.*;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.converter.DeviceConverter;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.*;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.model.dto.DeviceDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ProductService productService;
    @Autowired
    private SysDepartmentService departmentService;
    @Autowired
    private DeviceAuthenticationService deviceAuthenticationService;
    @Autowired
    private DeviceShadowService deviceShadowService;
    @Autowired
    private DeviceOtaService deviceOtaService;

    public DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<Device> selectByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.selectByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public List<Device> selectByProductId(String productId) {
        Assert.hasText(productId, "productId不能为空");
        return this.repository.select(DEVICE.PRODUCT_ID.eq(productId), DEVICE.DELETED.eq(Boolean.FALSE));
    }

    @Override
    public DeviceDTO selectByDeviceId(String deviceId) {
        Device device = this.selectById(deviceId);
        if (device == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }

        DeviceDTO deviceDTO = DeviceConverter.INSTANCE.from(device);

        DeviceAuthentication deviceAuthentication = this.deviceAuthenticationService.selectByDeviceId(deviceId);
        if (deviceAuthentication == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_AUTHENTICATION_NOT_EXISTS, deviceId);
        }
        deviceDTO.setAuthenticationMethod(deviceAuthentication.getAuthenticationMethod());
        List<DeviceFunctionModuleOtaDTO> deviceOtaList = this.deviceOtaService.selectByDeviceId(deviceId);
        if (!ObjectUtils.isEmpty(deviceOtaList)) {
            deviceDTO.setModuleVersion(deviceOtaList.stream()
                    .collect(Collectors.toMap(DeviceFunctionModuleOtaDTO::getModuleIdentifier, DeviceFunctionModuleOtaDTO::getOtaVersion)));
        }
        return deviceDTO;
    }

    @Override
    public Device addDevice(Device device, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition) {
        // check request data
        this.checkData(device, false);

        // Save device data
        this.repository.insert(device);

        // Save authentication data
        this.deviceAuthenticationService.addAuthentication(device.getId(), authenticationMethod, authenticationAddition);

        // init device shadow data
        this.deviceShadowService.initialShadow(device.getId());
        return device;
    }


    @Override
    public Device updateDevice(Device device) {
        this.checkData(device, true);
        this.repository.update(device);
        return device;
    }

    @Override
    public Device deleteDevice(String deviceId) {
        Device device = this.selectById(deviceId);
        if (device == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        if (device.isEnabled()) {
            throw new ApiException(StatusCodeConstants.DEVICE_IS_ENABLE_NOT_ALLOWED_DELETE, device.getDeviceName());
        }
        this.repository.update(List.of(DEVICE.DELETED.set(Boolean.TRUE)), DEVICE.ID.eq(device.getId()));
        return device;
    }

    @Override
    public void updateEnabled(String deviceId, boolean enabled) {
        Device device = this.selectById(deviceId);
        if (device == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        this.repository.update(List.of(DEVICE.ENABLED.set(enabled)), DEVICE.ID.eq(device.getId()));
    }

    private void checkData(Device device, boolean doUpdate) {
        // check product exists
        Product product = this.productService.selectById(device.getProductId());
        if (product == null || product.isDeleted()) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, device.getProductId());
        }

        // check department exists
        SysDepartment department = this.departmentService.selectById(device.getDepartmentId());
        if (department == null || department.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_NOT_FOUND, device.getDepartmentId());
        }

        // check already exists
        List<ConditionGroup> conditionGroups = new ArrayList<>();

        // update
        if (doUpdate) {
            conditionGroups.add(
                    ConditionGroup.withCondition(
                            DEVICE.PRODUCT_ID.eq(device.getProductId()),
                            DEVICE.DEVICE_NAME.eq(device.getDeviceName()),
                            DEVICE.DELETED.eq(Boolean.FALSE)
                    )
            );
            conditionGroups.add(ConditionGroup.withCondition(DEVICE.ID.neq(device.getId())));
        }
        // insert
        else {
            conditionGroups.add(
                    ConditionGroup.withCondition(
                            DEVICE.PRODUCT_ID.eq(device.getProductId()),
                            DEVICE.DEVICE_NAME.eq(device.getDeviceName()),
                            DEVICE.DELETED.eq(Boolean.FALSE)
                    )
            );
        }
        // @formatter:off
        Device storedDevice = this.repository.selectOne(conditionGroups.toArray(ConditionGroup[]::new));
        // @formatter:on
        if (storedDevice != null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ALREADY_EXISTS, device.getDeviceName());
        }
    }
}

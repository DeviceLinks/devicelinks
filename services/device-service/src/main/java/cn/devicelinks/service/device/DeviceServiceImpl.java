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

package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.converter.DeviceConverter;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.common.DeviceCredentialsType;
import cn.devicelinks.common.DeviceStatus;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.api.model.dto.DeviceDTO;
import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.entity.*;
import cn.devicelinks.jdbc.repository.DeviceRepository;
import cn.devicelinks.service.ota.DeviceOtaService;
import cn.devicelinks.service.product.ProductService;
import cn.devicelinks.service.system.SysDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.devicelinks.jdbc.tables.TDevice.DEVICE;

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
    private DeviceCredentialsService deviceCredentialsService;
    @Autowired
    private DeviceSecretService deviceSecretService;
    @Autowired
    private DeviceShadowService deviceShadowService;
    @Autowired
    private DeviceOtaService deviceOtaService;
    @Autowired
    private DeviceProfileService deviceProfileService;
    @Autowired
    private DeviceTagService deviceTagService;

    public DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<Device> selectByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.selectByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public Device selectByName(String deviceName) {
        return this.repository.selectOne(DEVICE.DEVICE_NAME.eq(deviceName));
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

        DeviceCredentials deviceCredentials = this.deviceCredentialsService.selectByDeviceId(deviceId);
        if (deviceCredentials == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_AUTHENTICATION_NOT_EXISTS, deviceId);
        }
        deviceDTO.setCredentialsType(deviceCredentials.getCredentialsType());
        List<DeviceFunctionModuleOtaDTO> deviceOtaList = this.deviceOtaService.selectByDeviceId(deviceId);
        if (!ObjectUtils.isEmpty(deviceOtaList)) {
            deviceDTO.setModuleVersion(deviceOtaList.stream()
                    .collect(Collectors.toMap(DeviceFunctionModuleOtaDTO::getModuleIdentifier, DeviceFunctionModuleOtaDTO::getOtaVersion)));
        }
        return deviceDTO;
    }

    @Override
    public Device addDevice(Device device, DeviceCredentialsType credentialsType, DeviceCredentialsAddition credentialsAddition, AesSecretKeySet deviceSecretKeySet) {
        // check request data
        this.checkData(device, false);

        // Save device data
        this.repository.insert(device);

        // Added non-existent tags
        if (!ObjectUtils.isEmpty(device.getTags())) {
            for (String tag : device.getTags()) {
                if (!ObjectUtils.isEmpty(tag)) {
                    this.deviceTagService.addDeviceTag(new DeviceTag().setName(tag).setCreateBy(device.getCreateBy()));
                }
            }
        }

        // Save Device Secret
        this.deviceSecretService.initializeSecret(device.getId(), deviceSecretKeySet);

        // Save device credentials
        this.deviceCredentialsService.addCredentials(device.getId(), credentialsType, null, credentialsAddition);

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

    @Override
    public void activateDevice(String deviceId) {
        this.repository.update(List.of(DEVICE.STATUS.set(DeviceStatus.Activated),
                        DEVICE.ACTIVATION_TIME.set(LocalDateTime.now())),
                DEVICE.ID.eq(deviceId));
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

        // check device profile exists
        DeviceProfile deviceProfile = this.deviceProfileService.selectById(device.getProfileId());
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, device.getProfileId());
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

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

import cn.devicelinks.api.device.center.model.request.DynamicRegistrationRequest;
import cn.devicelinks.api.device.center.model.response.DynamicRegistrationResponse;
import cn.devicelinks.api.model.converter.DeviceConverter;
import cn.devicelinks.api.model.dto.DeviceDTO;
import cn.devicelinks.api.model.dto.DeviceFunctionModuleOtaDTO;
import cn.devicelinks.api.model.dto.DeviceSecretDTO;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.common.*;
import cn.devicelinks.common.secret.AesSecretKeySet;
import cn.devicelinks.component.operate.log.OperationLogRecorder;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.*;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.cache.DeviceCacheEvictEvent;
import cn.devicelinks.jdbc.cache.DeviceCacheKey;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceRepository;
import cn.devicelinks.jdbc.repository.SysUserRepository;
import cn.devicelinks.service.ota.DeviceOtaService;
import cn.devicelinks.service.product.ProductService;
import cn.devicelinks.service.system.SysDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.devicelinks.jdbc.tables.TDevice.DEVICE;
import static cn.devicelinks.jdbc.tables.TSysUser.SYS_USER;

/**
 * 设备业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceServiceImpl extends CacheBaseServiceImpl<Device, String, DeviceRepository, DeviceCacheKey, DeviceCacheEvictEvent> implements DeviceService {
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
    @Autowired
    private SysUserRepository sysUserRepository;

    public DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    @TransactionalEventListener(classes = DeviceCacheEvictEvent.class)
    public void handleCacheEvictEvent(DeviceCacheEvictEvent event) {
        Device savedDevice = event.getSavedDevice();
        if (savedDevice != null) {
            cache.put(DeviceCacheKey.builder().deviceId(savedDevice.getId()).build(), savedDevice);
            cache.put(DeviceCacheKey.builder().deviceName(savedDevice.getDeviceName()).build(), savedDevice);
        } else {
            List<DeviceCacheKey> toEvict = new ArrayList<>();
            if (!ObjectUtils.isEmpty(event.getDeviceId())) {
                toEvict.add(DeviceCacheKey.builder().deviceId(event.getDeviceId()).build());
            }
            if (!ObjectUtils.isEmpty(event.getDeviceName())) {
                toEvict.add(DeviceCacheKey.builder().deviceName(event.getDeviceName()).build());
            }
            cache.evict(toEvict);
        }
    }

    @Override
    public PageResult<Device> selectByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.selectByPageable(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public Device selectByName(String deviceName) {
        Assert.hasText(deviceName, "The DeviceName cannot be empty string.");
        return cache.get(DeviceCacheKey.builder().deviceName(deviceName).build(),
                () -> this.repository.selectOne(DEVICE.DEVICE_NAME.eq(deviceName)));
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
            throw new ApiException(StatusCodeConstants.DEVICE_CREDENTIALS_NO_VALID_EXISTS, deviceId);
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
    public Device addDevice(Device device, DeviceCredentialsType credentialsType, DeviceCredentialsAddition credentialsAddition, AesSecretKeySet aesSecretKeySet) {
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
        this.deviceSecretService.initializeSecret(device.getId(), aesSecretKeySet);

        // Save device credentials
        this.deviceCredentialsService.addCredentials(device.getId(), credentialsType, null, credentialsAddition, aesSecretKeySet);

        // init device shadow data
        this.deviceShadowService.initialShadow(device.getId());
        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().savedDevice(device).build());
        return device;
    }


    @Override
    public Device updateDevice(Device device) {
        this.checkData(device, true);
        this.repository.update(device);
        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().deviceId(device.getId()).deviceName(device.getDeviceName()).build());
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
        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().deviceId(device.getId()).deviceName(device.getDeviceName()).build());
        return device;
    }

    @Override
    public Map<String, String> batchDeleteDevices(List<String> deviceIds) {
        Map<String, String> failureReasonMap = new LinkedHashMap<>();
        deviceIds.forEach(deviceId -> {
            try {
                Device deletedDevice = this.deleteDevice(deviceId);
                OperationLogRecorder.success(deviceId, deletedDevice);
            } catch (Exception e) {
                if (e instanceof ApiException apiException) {
                    String errorMsg = apiException.getStatusCode().formatMessage(apiException.getMessageVariables());
                    failureReasonMap.put(deviceId, errorMsg);
                } else {
                    log.error("删除设备时遇到未知的异常，设备ID：" + deviceId, e);
                    failureReasonMap.put(deviceId, StatusCodeConstants.UNKNOWN_ERROR.formatMessage(e.getMessage()));
                }
                OperationLogRecorder.error(deviceId, deviceId, e);
            }
        });
        return failureReasonMap;
    }

    @Override
    public void updateEnabled(String deviceId, boolean enabled) {
        Device device = this.selectById(deviceId);
        if (device == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        this.repository.update(List.of(DEVICE.ENABLED.set(enabled)), DEVICE.ID.eq(device.getId()));
        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().deviceId(device.getId()).deviceName(device.getDeviceName()).build());
    }

    @Override
    public Map<String, String> batchUpdateEnabled(UpdateDeviceEnabledAction updateEnabledAction, List<String> deviceIds) {
        Map<String, String> failureReasonMap = new LinkedHashMap<>();
        deviceIds.forEach(deviceId -> {
            try {
                this.updateEnabled(deviceId, UpdateDeviceEnabledAction.Enable == updateEnabledAction);
                OperationLogRecorder.success(deviceId, updateEnabledAction);
            } catch (Exception e) {
                if (e instanceof ApiException apiException) {
                    String errorMsg = apiException.getStatusCode().formatMessage(apiException.getMessageVariables());
                    failureReasonMap.put(deviceId, errorMsg);
                } else {
                    log.error("更新设备启用状态时遇到未知的异常，设备ID：" + deviceId, e);
                    failureReasonMap.put(deviceId, StatusCodeConstants.UNKNOWN_ERROR.formatMessage(e.getMessage()));
                }
                OperationLogRecorder.error(deviceId, deviceId, e);
            }
        });
        return failureReasonMap;
    }

    @Override
    public void activateDevice(String deviceId) {
        Device device = this.selectById(deviceId);
        if (device == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        this.repository.update(List.of(DEVICE.STATUS.set(DeviceStatus.Activated),
                        DEVICE.ACTIVATION_TIME.set(LocalDateTime.now())),
                DEVICE.ID.eq(deviceId));
        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().deviceId(device.getId()).deviceName(device.getDeviceName()).build());
    }

    @Override
    public DynamicRegistrationResponse dynamicRegistration(DynamicRegistrationRequest request, AesSecretKeySet aesSecretKeySet) {
        Product product = productService.selectById(request.getProductId());
        if (product == null || product.isDeleted()) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, request.getProductId());
        }
        if (!product.isDynamicRegistration()) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_SUPPORTED_DYNAMIC_REGISTER, request.getProductId());
        }
        if (DynamicRegistrationMethod.ProvisionKey.toString().equals(request.getRegistrationMethod())) {
            DeviceProfile deviceProfile = deviceProfileService.selectById(request.getProfileId());
            if (deviceProfile == null || deviceProfile.isDeleted()) {
                throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, request.getProfileId());
            }
            DeviceProfileProvisionAddition profileProvisionAddition = deviceProfile.getProvisionAddition();
            if (profileProvisionAddition == null || profileProvisionAddition.getStrategy() == null || DeviceProvisionStrategy.AllowCreateDevice != profileProvisionAddition.getStrategy()) {
                throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_CREATION_NOT_ALLOWED, request.getProfileId());
            }
        }
        Device device = selectByName(request.getDeviceName());
        if (device != null && (device.isEnabled() || !device.isDeleted())) {
            throw new ApiException(StatusCodeConstants.DEVICE_ALREADY_EXISTS, request.getDeviceName());
        }
        SysUser systemAdmin = sysUserRepository.selectOne(SYS_USER.IDENTITY.eq(UserIdentity.SystemAdmin));
        // @formatter:off
        device = new Device()
                .setDeviceName(request.getDeviceName())
                .setDeviceType(DeviceType.Direct)
                .setProductId(request.getProductId())
                .setProfileId(request.getProfileId())
                .setDepartmentId(systemAdmin.getDepartmentId())
                .setCreateBy(systemAdmin.getId());
        // @formatter:on
        // check request data
        this.checkData(device, false);

        // Save device data
        this.repository.insert(device);

        // Save Device Secret
        DeviceSecretDTO deviceSecretDTO = this.deviceSecretService.initializeSecret(device.getId(), aesSecretKeySet);

        // init device shadow data
        this.deviceShadowService.initialShadow(device.getId());

        publishCacheEvictEvent(DeviceCacheEvictEvent.builder().savedDevice(device).build());

        // @formatter:off
        return new DynamicRegistrationResponse()
                .setDeviceId(device.getId())
                .setDeviceName(request.getDeviceName())
                .setDeviceSecret(deviceSecretDTO.getSecret());
        // @formatter:on
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
        if (!ObjectUtils.isEmpty(device.getProfileId())) {
            DeviceProfile deviceProfile = this.deviceProfileService.selectById(device.getProfileId());
            if (deviceProfile == null || deviceProfile.isDeleted()) {
                throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, device.getProfileId());
            }
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

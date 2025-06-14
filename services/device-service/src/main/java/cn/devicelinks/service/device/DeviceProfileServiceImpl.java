package cn.devicelinks.service.device;

import cn.devicelinks.api.model.converter.DeviceProfileConverter;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.api.model.request.AddDeviceProfileRequest;
import cn.devicelinks.api.model.request.BatchSetDeviceProfileRequest;
import cn.devicelinks.api.model.request.UpdateDeviceProfileBasicInfoRequest;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.common.Constants;
import cn.devicelinks.common.DeviceProfileBatchSetAway;
import cn.devicelinks.common.DeviceType;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.*;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.PaginationQueryConverter;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.cache.DeviceProfileCacheEvictEvent;
import cn.devicelinks.jdbc.cache.DeviceProfileCacheKey;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceProfileRepository;
import cn.devicelinks.jdbc.repository.DeviceRepository;
import cn.devicelinks.jdbc.repository.FunctionModuleRepository;
import cn.devicelinks.jdbc.repository.ProductRepository;
import cn.devicelinks.service.ota.OtaService;
import cn.devicelinks.service.product.FunctionModuleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.devicelinks.jdbc.tables.TDevice.DEVICE;
import static cn.devicelinks.jdbc.tables.TDeviceProfile.DEVICE_PROFILE;
import static cn.devicelinks.jdbc.tables.TFunctionModule.FUNCTION_MODULE;

/**
 * 设备配置文件业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceProfileServiceImpl extends CacheBaseServiceImpl<DeviceProfile, String, DeviceProfileRepository, DeviceProfileCacheKey, DeviceProfileCacheEvictEvent> implements DeviceProfileService {

    @Autowired
    private OtaService otaService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FunctionModuleRepository functionModuleRepository;

    public DeviceProfileServiceImpl(DeviceProfileRepository repository) {
        super(repository);
    }

    @Override
    @TransactionalEventListener(classes = DeviceProfileCacheEvictEvent.class)
    public void handleCacheEvictEvent(DeviceProfileCacheEvictEvent event) {
        DeviceProfile savedDeviceProfile = event.getSavedDeviceProfile();
        if (savedDeviceProfile != null) {
            cache.put(DeviceProfileCacheKey.builder().deviceProfileId(savedDeviceProfile.getId()).build(), savedDeviceProfile);
        } else {
            if (!ObjectUtils.isEmpty(event.getDeviceProfileId())) {
                cache.evict(DeviceProfileCacheKey.builder().deviceProfileId(event.getDeviceProfileId()).build());
            }
            if (!ObjectUtils.isEmpty(event.getProvisionKey())) {
                cache.evict(DeviceProfileCacheKey.builder().provisionKey(event.getProvisionKey()).build());
            }
        }
    }

    @Override
    public PageResult<DeviceProfile> getDeviceProfileListByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getDeviceProfileListByPageable(searchFieldConditionList,
                converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public DeviceProfile getByProvisionKey(String provisionKey) {
        Assert.hasText(provisionKey, "The provision key cannot be null or empty");
        return this.cache.get(DeviceProfileCacheKey.builder().provisionKey(provisionKey).build(),
                () -> this.repository.getByProvisionKey(provisionKey));
    }

    @Override
    public DeviceProfile addDeviceProfile(AddDeviceProfileRequest request, UserAuthorizedAddition authorizedAddition) {
        DeviceProfile deviceProfile = DeviceProfileConverter.INSTANCE.fromAddDeviceProfileRequest(request);
        // check request data
        this.checkData(deviceProfile, false);
        deviceProfile
                .setCreateBy(authorizedAddition.getUserId());
        this.repository.insert(deviceProfile);
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder().savedDeviceProfile(deviceProfile).build());
        return deviceProfile;
    }

    @Override
    public DeviceProfile updateDeviceProfile(DeviceProfile deviceProfile) {
        this.checkData(deviceProfile, true);
        this.repository.update(deviceProfile);
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return deviceProfile;
    }

    @Override
    public DeviceProfile updateDeviceProfileBasicInfo(String profileId, UpdateDeviceProfileBasicInfoRequest request) {
        DeviceProfile deviceProfile = this.selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        DeviceProfileConverter.INSTANCE.updateBasicInfo(request, deviceProfile);

        // check basic info
        this.checkBasicInfo(deviceProfile, true);

        this.repository.update(deviceProfile);
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return deviceProfile;
    }

    @Override
    public Map<String, Object> updateDeviceProfileExtension(String profileId, Map<String, Object> extension) {
        DeviceProfile deviceProfile = selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        this.repository.update(List.of(DEVICE_PROFILE.EXTENSION.set(extension)), DEVICE_PROFILE.ID.eq(profileId));
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return extension;
    }

    @Override
    public DeviceProfileLogAddition updateDeviceProfileLogAddition(String profileId, DeviceProfileLogAddition logAddition) {
        DeviceProfile deviceProfile = selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }

        // check addition data
        deviceProfile.setLogAddition(logAddition);
        this.checkLogAdditionData(deviceProfile);

        this.repository.update(List.of(DEVICE_PROFILE.LOG_ADDITION.set(logAddition)), DEVICE_PROFILE.ID.eq(profileId));
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return logAddition;
    }

    @Override
    public DeviceProfileProvisionAddition updateDeviceProfileProvisionAddition(String profileId, DeviceProfileProvisionAddition provisionAddition) {
        DeviceProfile deviceProfile = selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }

        // check addition data
        deviceProfile.setProvisionAddition(provisionAddition);
        this.checkProvisionAddition(deviceProfile);

        this.repository.update(List.of(DEVICE_PROFILE.PROVISION_ADDITION.set(provisionAddition)), DEVICE_PROFILE.ID.eq(profileId));
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return provisionAddition;
    }

    @Override
    public DeviceProfile deleteDeviceProfile(String profileId) {
        DeviceProfile deviceProfile = selectById(profileId);
        if (deviceProfile == null) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        if (deviceProfile.isDefaultProfile()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_DEFAULT_CANNOT_DELETE, profileId);
        }
        if (!deviceProfile.isDeleted()) {
            // Update is deleted
            deviceProfile.setDeleted(Boolean.TRUE);
            this.update(deviceProfile);

            // Clear device profile id for devices and products
            this.deviceRepository.clearDeviceProfileId(profileId);
            this.productRepository.clearDeviceProfileId(profileId);
        }
        // @formatter:off
        publishCacheEvictEvent(DeviceProfileCacheEvictEvent.builder()
                .deviceProfileId(deviceProfile.getId())
                .provisionKey(deviceProfile.getProvisionAddition() != null ? deviceProfile.getProvisionAddition().getProvisionDeviceKey() : null)
                .build());
        // @formatter:on
        return deviceProfile;
    }

    @Override
    public void batchSet(String profileId, BatchSetDeviceProfileRequest request) {
        DeviceProfile deviceProfile = this.selectById(profileId);
        if (deviceProfile == null || deviceProfile.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_NOT_EXISTS, profileId);
        }
        DeviceProfileBatchSetAway batchSetAway = DeviceProfileBatchSetAway.valueOf(request.getBatchSetAway());
        switch (batchSetAway) {
            // Batch settings based on device ids
            case SpecifyDevice -> {
                if (ObjectUtils.isEmpty(request.getDeviceIds())) {
                    throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID, request.getBatchSetAway());
                }
                // If the deviceProfile belongs to a product, the deviceIds parameter contains only the device ID of the product.
                this.deviceRepository.updateDeviceProfileIdWithDeviceIds(deviceProfile.getId(), request.getDeviceIds());
            }
            // Batch settings based on device tags
            case DeviceTag -> {
                if (ObjectUtils.isEmpty(request.getDeviceTags())) {
                    throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID, request.getBatchSetAway());
                }
                this.deviceRepository.updateDeviceProfileIdWithTags(deviceProfile.getProductId(), deviceProfile.getId(), request.getDeviceTags());
            }
            // Batch settings based on device type
            case DeviceType -> {
                if (ObjectUtils.isEmpty(request.getDeviceType())) {
                    throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_BATCH_SET_PARAMETER_INVALID, request.getBatchSetAway());
                }
                this.deviceRepository.updateDeviceProfileIdWithDeviceType(deviceProfile.getProductId(),
                        deviceProfile.getId(), DeviceType.valueOf(request.getDeviceType()));
            }
        }
    }

    /**
     * 检查数据
     *
     * @param deviceProfile 设备配置文件对象实例
     * @param doUpdate      是否为更新操作
     */
    private void checkData(DeviceProfile deviceProfile, boolean doUpdate) {
        // Check Basic info
        this.checkBasicInfo(deviceProfile, doUpdate);
        // Check Log Level
        this.checkLogAdditionData(deviceProfile);
        // Check Provision
        this.checkProvisionAddition(deviceProfile);
    }

    private void checkBasicInfo(DeviceProfile deviceProfile, boolean doUpdate) {
        // Check Firmware Exists
        if (!ObjectUtils.isEmpty(deviceProfile.getFirmwareId())) {
            Ota ota = otaService.selectByFirmwareId(deviceProfile.getFirmwareId());
            if (ota == null || ota.isDeleted()) {
                throw new ApiException(StatusCodeConstants.OTA_FIRMWARE_NOT_EXISTS, deviceProfile.getFirmwareId());
            }
        }
        // Check Software Exists
        if (!ObjectUtils.isEmpty(deviceProfile.getSoftwareId())) {
            Ota ota = otaService.selectBySoftwareId(deviceProfile.getSoftwareId());
            if (ota == null || ota.isDeleted()) {
                throw new ApiException(StatusCodeConstants.OTA_SOFTWARE_NOT_EXISTS, deviceProfile.getSoftwareId());
            }
        }
        // Check uniqueness
        List<ConditionGroup> conditionGroups = new ArrayList<>();

        // update
        if (doUpdate) {
            conditionGroups.add(
                    ConditionGroup.withCondition(
                            DEVICE_PROFILE.NAME.eq(deviceProfile.getName()),
                            DEVICE_PROFILE.DELETED.eq(Boolean.FALSE)
                    )
            );
            conditionGroups.add(ConditionGroup.withCondition(DEVICE.ID.neq(deviceProfile.getId())));
        }
        // insert
        else {
            conditionGroups.add(
                    ConditionGroup.withCondition(
                            DEVICE_PROFILE.NAME.eq(deviceProfile.getName()),
                            DEVICE_PROFILE.DELETED.eq(Boolean.FALSE)
                    )
            );
        }
        // @formatter:off
        DeviceProfile storedDeviceProfile = this.repository.selectOne(conditionGroups.toArray(ConditionGroup[]::new));
        // @formatter:on
        if (storedDeviceProfile != null) {
            throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_ALREADY_EXISTS, deviceProfile.getName());
        }
    }

    private void checkLogAdditionData(DeviceProfile deviceProfile) {
        if (!ObjectUtils.isEmpty(deviceProfile.getLogAddition()) && !ObjectUtils.isEmpty(deviceProfile.getLogAddition().getLogLevels())) {

            Map<String, DeviceProfileLogAddition.FunctionModuleLogLevel> functionModuleLogLevelMap = deviceProfile.getLogAddition().getLogLevels();

            // Device profiles belong to products
            if (!ObjectUtils.isEmpty(deviceProfile.getProductId())) {
                Product product = this.productRepository.selectOne(deviceProfile.getProductId());
                if (product == null || product.isDeleted()) {
                    throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, deviceProfile.getProductId());
                }
                List<FunctionModule> productFunctionModuleList = this.functionModuleRepository.select(
                        FUNCTION_MODULE.CREATE_TIME.desc(),
                        FUNCTION_MODULE.PRODUCT_ID.eq(deviceProfile.getProductId()),
                        FUNCTION_MODULE.DELETED.eq(Boolean.FALSE));
                Map<String, FunctionModule> productFunctionModuleMap = productFunctionModuleList.stream()
                        .filter(fm -> !fm.isDeleted())
                        .collect(Collectors.toMap(FunctionModule::getIdentifier, v -> v));
                for (String functionModuleIdentifier : functionModuleLogLevelMap.keySet()) {
                    if (!productFunctionModuleMap.containsKey(functionModuleIdentifier)) {
                        throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, functionModuleIdentifier);
                    }
                }
            }
            // Device profiles do not belong to products
            else {
                if (functionModuleLogLevelMap.size() != Constants.ONE || !functionModuleLogLevelMap.containsKey(FunctionModuleServiceImpl.DEFAULT_FUNCTION_MODULE_IDENTIFIER)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_LOG_LEVELS_INVALID);
                }
            }
        }
    }

    private void checkProvisionAddition(DeviceProfile deviceProfile) {
        if (!ObjectUtils.isEmpty(deviceProfile.getProvisionAddition())) {
            DeviceProfileProvisionAddition provisionAddition = deviceProfile.getProvisionAddition();
            if (ObjectUtils.isEmpty(provisionAddition.getProvisionDeviceKey()) || ObjectUtils.isEmpty(provisionAddition.getProvisionDeviceSecret())) {
                throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_PROVISION_ADDITION_INVALID);
            }
        }
    }
}

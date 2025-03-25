package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.DeviceProfileService;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.service.OtaService;
import cn.devicelinks.console.service.ProductService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.converter.DeviceProfileConverter;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddDeviceProfileRequest;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.ProvisionRegistrationStrategy;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.*;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.repositorys.DeviceProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.devicelinks.console.service.impl.FunctionModuleServiceImpl.DEFAULT_FUNCTION_MODULE_IDENTIFIER;
import static cn.devicelinks.framework.jdbc.tables.TDevice.DEVICE;
import static cn.devicelinks.framework.jdbc.tables.TDeviceProfile.DEVICE_PROFILE;

/**
 * 设备配置文件业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceProfileServiceImpl extends BaseServiceImpl<DeviceProfile, String, DeviceProfileRepository> implements DeviceProfileService {

    @Autowired
    private OtaService otaService;

    @Autowired
    private FunctionModuleService functionModuleService;

    @Autowired
    private ProductService productService;

    public DeviceProfileServiceImpl(DeviceProfileRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceProfile> getDeviceProfileListByPageable(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.getDeviceProfileListByPageable(searchFieldQuery.toSearchFieldConditionList(),
                paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public DeviceProfile addDeviceProfile(AddDeviceProfileRequest request) {
        DeviceProfile deviceProfile = DeviceProfileConverter.INSTANCE.fromAddDeviceProfileRequest(request);
        // check request data
        this.checkData(deviceProfile, false);
        deviceProfile
                .setCreateBy(UserDetailsContext.getUserId());
        this.repository.insert(deviceProfile);
        return deviceProfile;
    }

    @Override
    public DeviceProfile updateDeviceProfile(DeviceProfile deviceProfile) {
        this.checkData(deviceProfile, true);
        // From enabled to disabled, clear the pre-registration configuration
        if (ProvisionRegistrationStrategy.Disabled == deviceProfile.getProvisionRegistrationStrategy() && !ObjectUtils.isEmpty(deviceProfile.getProvisionRegistrationAddition())) {
            deviceProfile.setProvisionRegistrationAddition(null);
        }
        this.repository.update(deviceProfile);
        return deviceProfile;
    }

    /**
     * 检查数据
     *
     * @param deviceProfile 设备配置文件对象实例
     * @param doUpdate      是否为更新操作
     */
    private void checkData(DeviceProfile deviceProfile, boolean doUpdate) {
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
        // Check Provision Registration
        if (deviceProfile.getProvisionRegistrationStrategy() != null &&
                ProvisionRegistrationStrategy.Disabled != deviceProfile.getProvisionRegistrationStrategy() &&
                !ObjectUtils.isEmpty(deviceProfile.getProvisionRegistrationAddition())) {
            DeviceProfileProvisionRegistrationAddition provisionRegistrationAddition = deviceProfile.getProvisionRegistrationAddition();
            switch (deviceProfile.getProvisionRegistrationStrategy()) {
                case AllowCreateDevice, CheckDevice -> {
                    if (ObjectUtils.isEmpty(provisionRegistrationAddition.getDeviceKey()) || ObjectUtils.isEmpty(provisionRegistrationAddition.getDeviceSecret())) {
                        throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_PROVISION_REGISTRATION_ADDITION_INVALID);
                    }
                }
                case X509 -> {
                    if (ObjectUtils.isEmpty(provisionRegistrationAddition.getX509Pem()) ||
                            (provisionRegistrationAddition.isAllowCreateDeviceByX509Certificate() && ObjectUtils.isEmpty(provisionRegistrationAddition.getCertificateRegExPattern()))) {
                        throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_PROVISION_REGISTRATION_ADDITION_INVALID);
                    }
                }
            }
        }
        // Check Log Level
        if (!ObjectUtils.isEmpty(deviceProfile.getLogAddition()) && !ObjectUtils.isEmpty(deviceProfile.getLogAddition().getLogLevels())) {

            Map<String, DeviceProfileLogAddition.FunctionModuleLogLevel> functionModuleLogLevelMap = deviceProfile.getLogAddition().getLogLevels();

            // Device profiles belong to products
            if (!ObjectUtils.isEmpty(deviceProfile.getProductId())) {
                Product product = this.productService.selectById(deviceProfile.getProductId());
                if (product == null || product.isDeleted()) {
                    throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, deviceProfile.getProductId());
                }
                List<FunctionModule> productFunctionModuleList = this.functionModuleService.getProductFunctionModule(deviceProfile.getProductId());
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
                if (functionModuleLogLevelMap.size() != Constants.ONE || !functionModuleLogLevelMap.containsKey(DEFAULT_FUNCTION_MODULE_IDENTIFIER)) {
                    throw new ApiException(StatusCodeConstants.DEVICE_PROFILE_LOG_LEVELS_INVALID);
                }
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
}

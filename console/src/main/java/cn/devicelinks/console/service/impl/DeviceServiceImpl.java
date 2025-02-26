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
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.DeviceAuthenticationMethod;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.*;
import cn.devicelinks.framework.common.utils.X509Utils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.repositorys.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

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
    @Autowired
    private ProductService productService;
    @Autowired
    private SysDepartmentService departmentService;
    @Autowired
    private DeviceAuthenticationService deviceAuthenticationService;
    @Autowired
    private DeviceShadowService deviceShadowService;

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
    public Device addDevice(Device device, DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition) {
        // @formatter:off
        Device storedDevice = this.repository.selectOne(
                DEVICE.DEVICE_CODE.eq(device.getDeviceCode()),
                DEVICE.PRODUCT_ID.eq(device.getProductId()),
                DEVICE.DELETED.eq(Boolean.FALSE));
        // @formatter:on
        if (storedDevice != null) {
            throw new ApiException(StatusCodeConstants.DEVICE_ALREADY_EXISTS, device.getDeviceCode());
        }
        Product product = this.productService.selectById(device.getProductId());
        if (product == null || product.isDeleted()) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, device.getProductId());
        }
        SysDepartment department = this.departmentService.selectById(device.getDepartmentId());
        if (department == null || department.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEPARTMENT_NOT_FOUND, device.getDepartmentId());
        }
        // validate authentication data
        this.validateAuthentication(authenticationMethod, authenticationAddition);

        // Save device data
        this.repository.insert(device);

        // Save authentication data
        this.deviceAuthenticationService.saveAuthentication(device, authenticationMethod, authenticationAddition);

        // init device shadow data
        this.deviceShadowService.initialShadow(device.getId());
        return device;
    }

    private void validateAuthentication(DeviceAuthenticationMethod authenticationMethod, DeviceAuthenticationAddition authenticationAddition) {
        switch (authenticationMethod) {
            case AccessToken:
                if (ObjectUtils.isEmpty(authenticationAddition.getAccessToken())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_ACCESS_TOKEN, authenticationAddition.getAccessToken());
                }
                DeviceAuthentication accessTokenAuthentication = this.deviceAuthenticationService.selectByAccessToken(authenticationAddition.getAccessToken());
                if (accessTokenAuthentication != null) {
                    throw new ApiException(StatusCodeConstants.DEVICE_ACCESS_TOKEN_ALREADY_EXISTS, authenticationAddition.getAccessToken());
                }
                break;
            case MqttBasic:
                // Validate MQTT Basic authentication
                DeviceAuthenticationAddition.MqttBasic mqttBasic = authenticationAddition.getMqttBasic();
                if (mqttBasic == null || ObjectUtils.isEmpty(mqttBasic.getUsername()) || ObjectUtils.isEmpty(mqttBasic.getPassword())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_MQTT_BASIC_AUTH, mqttBasic);
                }
                DeviceAuthentication mqttBasicAuthentication = this.deviceAuthenticationService.selectByClientId(mqttBasic.getClientId());
                if (mqttBasicAuthentication != null) {
                    throw new ApiException(StatusCodeConstants.DEVICE_MQTT_BASIC_AUTH_CLIENT_ID_ALREADY_EXISTS, mqttBasic.getClientId());
                }
                break;
            case DeviceCredential:
                DeviceAuthenticationAddition.DeviceCredential deviceCredential = authenticationAddition.getDeviceCredential();
                if (deviceCredential == null || ObjectUtils.isEmpty(deviceCredential.getDeviceKey()) || ObjectUtils.isEmpty(deviceCredential.getDeviceSecret())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_CREDENTIAL, deviceCredential);
                }
                DeviceAuthentication deviceCredentialAuthentication = this.deviceAuthenticationService.selectByDeviceKey(deviceCredential.getDeviceKey());
                if (deviceCredentialAuthentication != null) {
                    throw new ApiException(StatusCodeConstants.DEVICE_CREDENTIAL_KEY_ALREADY_EXISTS, deviceCredential);
                }
                break;
            case X509:
                if (ObjectUtils.isEmpty(authenticationAddition.getX509Pem()) || !X509Utils.isValidX509Pem(authenticationAddition.getX509Pem())) {
                    throw new ApiException(StatusCodeConstants.INVALID_DEVICE_X509_PEM, authenticationAddition.getX509Pem());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported authentication method");
        }
    }
}

/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.jdbc;

import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.NotificationType;
import cn.devicelinks.framework.common.pojos.*;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import cn.devicelinks.framework.jdbc.core.mapper.value.BasicColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.ColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.EnumColumnValueMapper;
import cn.devicelinks.framework.jdbc.core.mapper.value.JSONObjectValueMapper;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 列值映射器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ColumnValueMappers extends BasicColumnValueMapper {
    EnumColumnValueMapper USER_IDENTITY = new EnumColumnValueMapper(UserIdentity.class);
    EnumColumnValueMapper PLATFORM_TYPE = new EnumColumnValueMapper(PlatformType.class);
    EnumColumnValueMapper LOG_ACTION = new EnumColumnValueMapper(LogAction.class);
    EnumColumnValueMapper LOG_OBJECT_TYPE = new EnumColumnValueMapper(LogObjectType.class);
    EnumColumnValueMapper DEVICE_STATUS = new EnumColumnValueMapper(DeviceStatus.class);
    EnumColumnValueMapper DATA_FORMAT = new EnumColumnValueMapper(DataFormat.class);
    EnumColumnValueMapper DEVICE_AUTHENTICATION_METHOD = new EnumColumnValueMapper(DeviceAuthenticationMethod.class);
    EnumColumnValueMapper DEVICE_NETWORKING_AWAY = new EnumColumnValueMapper(DeviceNetworkingAway.class);
    EnumColumnValueMapper PRODUCT_STATUS = new EnumColumnValueMapper(ProductStatus.class);
    EnumColumnValueMapper SESSION_STATUS = new EnumColumnValueMapper(SessionStatus.class);
    EnumColumnValueMapper ATTRIBUTE_DATA_TYPE = new EnumColumnValueMapper(AttributeDataType.class);
    EnumColumnValueMapper OTA_PACKAGE_TYPE = new EnumColumnValueMapper(OtaPackageType.class);
    EnumColumnValueMapper OTA_FILE_SOURCE = new EnumColumnValueMapper(OTAFileSource.class);
    EnumColumnValueMapper SIGN_ALGORITHM = new EnumColumnValueMapper(SignAlgorithm.class);
    EnumColumnValueMapper OTA_UPGRADE_BATCH_TYPE = new EnumColumnValueMapper(OtaUpgradeBatchType.class);
    EnumColumnValueMapper OTA_UPGRADE_BATCH_STATE = new EnumColumnValueMapper(OtaUpgradeBatchState.class);
    EnumColumnValueMapper OTA_UPGRADE_BATCH_METHOD = new EnumColumnValueMapper(OtaUpgradeBatchMethod.class);
    EnumColumnValueMapper OTA_UPGRADE_BATCH_SCOPE = new EnumColumnValueMapper(OtaUpgradeBatchScope.class);
    EnumColumnValueMapper OTA_UPGRADE_PROGRESS_STATE = new EnumColumnValueMapper(OtaUpgradeProgressState.class);
    EnumColumnValueMapper OTA_UPGRADE_STRATEGY_TYPE = new EnumColumnValueMapper(OtaUpgradeStrategyType.class);
    EnumColumnValueMapper OTA_UPGRADE_STRATEGY_RETRY_INTERVAL = new EnumColumnValueMapper(OtaUpgradeStrategyRetryInterval.class);
    EnumColumnValueMapper OTA_PACKAGE_DOWNLOAD_PROTOCOL = new EnumColumnValueMapper(OtaPackageDownloadProtocol.class);
    EnumColumnValueMapper GLOBAL_SETTING_DATA_TYPE = new EnumColumnValueMapper(GlobalSettingDataType.class);
    EnumColumnValueMapper DEVICE_TYPE = new EnumColumnValueMapper(DeviceType.class);
    EnumColumnValueMapper NOTIFICATION_STATUS = new EnumColumnValueMapper(NotificationStatus.class);
    EnumColumnValueMapper NOTIFICATION_SEVERITY = new EnumColumnValueMapper(NotificationSeverity.class);
    EnumColumnValueMapper NOTIFICATION_TYPE = new EnumColumnValueMapper(NotificationType.class);
    EnumColumnValueMapper NOTIFICATION_MATCH_USER_AWAY = new EnumColumnValueMapper(NotificationMatchUserAway.class);
    EnumColumnValueMapper NOTIFICATION_PUSH_AWAY = new EnumColumnValueMapper(NotificationPushAway.class);
    EnumColumnValueMapper ACCESS_GATEWAY_PROTOCOL = new EnumColumnValueMapper(AccessGatewayProtocol.class);
    EnumColumnValueMapper NOTIFICATION_TYPE_IDENTIFIER = new EnumColumnValueMapper(NotificationTypeIdentifier.class);


    JSONObjectValueMapper ATTRIBUTE_ADDITION = new JSONObjectValueMapper(AttributeAddition.class);
    JSONObjectValueMapper DEVICE_ADDITION = new JSONObjectValueMapper(DeviceAddition.class);
    JSONObjectValueMapper DEVICE_AUTHENTICATION_ADDITION = new JSONObjectValueMapper(DeviceAuthenticationAddition.class);
    JSONObjectValueMapper NOTIFICATION_ADDITION = new JSONObjectValueMapper(NotificationAddition.class);
    JSONObjectValueMapper NOTIFICATION_RULE_ADDITION = new JSONObjectValueMapper(NotificationRuleAddition.class);
    JSONObjectValueMapper NOTIFICATION_TEMPLATE_ADDITION = new JSONObjectValueMapper(NotificationTemplateAddition.class);
    JSONObjectValueMapper OTA_ADDITION = new JSONObjectValueMapper(OtaAddition.class);
    JSONObjectValueMapper OTA_FILE_ADDITION = new JSONObjectValueMapper(OtaFileAddition.class);
    JSONObjectValueMapper OTA_UPGRADE_BATCH_ADDITION = new JSONObjectValueMapper(OtaUpgradeBatchAddition.class);
    JSONObjectValueMapper SYS_LOG_ADDITION = new JSONObjectValueMapper(SysLogAddition.class);


    // ------------------------------------Customize Mappers---------------------------------------//

    ColumnValueMapper<Map<String, Object>, String> JSON_MAP = new ColumnValueMapper<>() {
        @Override
        public String toColumn(Map<String, Object> originalValue, String columnName) {
            return !ObjectUtils.isEmpty(originalValue) ? JacksonUtils.toJsonString(originalValue) : null;
        }

        @Override
        public Map<String, Object> fromColumn(ResultSet rs, String columnName) throws SQLException {
            String columnValue = rs.getString(columnName);
            return !ObjectUtils.isEmpty(columnValue) ? JacksonUtils.parseMap(columnValue, String.class, Object.class) : null;
        }
    };

    ColumnValueMapper<List<String>, String> STRING_JOINER = new ColumnValueMapper<>() {
        @Override
        public String toColumn(List<String> originalValue, String columnName) {
            StringJoiner joiner = new StringJoiner(Constants.SEPARATOR);
            originalValue.forEach(joiner::add);
            return joiner.toString();
        }

        @Override
        public List<String> fromColumn(ResultSet rs, String columnName) throws SQLException {
            String columnValue = rs.getString(columnName);
            if (!ObjectUtils.isEmpty(columnValue)) {
                String[] StringArray = columnValue.split(Constants.SEPARATOR);
                return Arrays.stream(StringArray).collect(Collectors.toList());
            }
            return null;
        }
    };
}

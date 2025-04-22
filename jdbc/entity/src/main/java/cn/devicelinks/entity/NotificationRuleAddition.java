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

package cn.devicelinks.entity;

import cn.devicelinks.common.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通知规则附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationRuleAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private Alarm alarm;

    private DeviceLifecycle deviceLifecycle;

    private DataEntityAction dataEntityAction;

    private RuleEngineFailure ruleEngineFailure;

    /**
     * @see NotificationTypeIdentifier#Alarm
     */
    @Data
    @Accessors(chain = true)
    public static class Alarm {

        private List<NotificationSeverity> triggerSeverity;

        private List<NotificationStatus> triggerStatus;

        private AlarmType alarmType;

        private List<AlarmChain> chains;

        @Data
        @Accessors(chain = true)
        public static class AlarmChain {

            private List<String> receiverIds;

            private int afterTime;

            private TimeUnit afterTimeUnit;
        }
    }

    /**
     * @see NotificationTypeIdentifier#DeviceLifecycle
     */
    @Data
    @Accessors(chain = true)
    public static class DeviceLifecycle {

        private List<String> deviceIds;

        private List<String> deviceTags;

        private DeviceStatus deviceStatus;
    }

    /**
     * @see NotificationTypeIdentifier#DataEntityAction
     */
    @Data
    @Accessors(chain = true)
    public static class DataEntityAction {

        private List<LogObjectType> objectTypes;

        private List<EntityAction> actions;
    }

    /**
     * @see NotificationTypeIdentifier#RuleEngineFailure
     */
    @Data
    @Accessors(chain = true)
    public static class RuleEngineFailure {
        private boolean onlyFailure;
    }
}

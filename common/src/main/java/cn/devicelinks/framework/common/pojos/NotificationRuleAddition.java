package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.*;
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

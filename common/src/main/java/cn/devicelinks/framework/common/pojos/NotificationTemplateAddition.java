package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.NotificationPushAway;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 通知模版附加信息定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationTemplateAddition implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    private Web web;

    private Sms sms;

    private Email email;

    private Weixin weixin;

    /**
     * @see NotificationPushAway#Web
     */
    @Data
    @Accessors(chain = true)
    public static class Web {
        private String subject;
        private String message;
        private String buttonText;
        private String openUrl;
    }

    /**
     * @see NotificationPushAway#Sms
     */
    @Data
    @Accessors(chain = true)
    public static class Sms {
        private String message;
    }

    /**
     * @see NotificationPushAway#Email
     */
    @Data
    @Accessors(chain = true)
    public static class Email {
        private String subject;
        private String message;
    }

    /**
     * @see NotificationPushAway#Weixin
     */
    @Data
    @Accessors(chain = true)
    public static class Weixin {
        private String templateId;
        private List<String> variables;
    }
}

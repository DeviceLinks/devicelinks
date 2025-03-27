package cn.devicelinks.console.web.request;

import lombok.Data;

import java.util.Map;

/**
 * 更新设备配置文件扩展配置请求实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class UpdateDeviceProfileExtensionRequest {

    private Map<String, Object> extension;

}

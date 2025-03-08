package cn.devicelinks.framework.jdbc.model.dto;

import lombok.Data;

/**
 * 设备功能模块OTA数据传输实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class DeviceFunctionModuleOtaDTO {
    private String moduleId;
    private String moduleIdentifier;
    private String otaId;
    private String otaVersion;
}

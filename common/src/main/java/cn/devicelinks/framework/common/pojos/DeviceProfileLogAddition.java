package cn.devicelinks.framework.common.pojos;

import cn.devicelinks.framework.common.LogLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备配置文件 - 日志配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceProfileLogAddition {
    
    private List<FunctionModuleLogLevel> logLevels = new ArrayList<>();

    @Getter
    @Setter
    public static class FunctionModuleLogLevel {
        private String module;
        private LogLevel level;
    }
}

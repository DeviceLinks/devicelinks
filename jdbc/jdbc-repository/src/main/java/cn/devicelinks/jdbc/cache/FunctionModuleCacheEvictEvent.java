package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.FunctionModule;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link cn.devicelinks.entity.FunctionModule} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class FunctionModuleCacheEvictEvent {
    private String functionModuleId;
    private String identifier;
    private FunctionModule savedFunctionModule;
}

package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.SysDepartment;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link SysDepartment} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class SysDepartmentCacheEvictEvent {
    private String departmentId;
    private SysDepartment savedDepartment;
}

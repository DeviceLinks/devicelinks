package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.SysDepartment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link SysDepartment} Cache Key
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDepartmentCacheKey implements CacheKey {
    private String departmentId;

    @Override
    public void setId(String id) {
        this.departmentId = id;
    }

    @Override
    public String toString() {
        return departmentId;
    }
}

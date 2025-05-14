package cn.devicelinks.jdbc.cache;

import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.entity.SysUser;
import lombok.*;
import org.springframework.util.ObjectUtils;

/**
 * The {@link SysUser} CacheKey
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserCacheKey implements CacheKey {
    private String userId;
    private String account;

    @Override
    public void setId(String id) {
        this.userId = id;
    }

    @Override
    public String toString() {
        if (!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(account)) {
            return userId + "_" + account;
        } else if (!ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(account)) {
            return userId;
        } else {
            return account;
        }
    }
}

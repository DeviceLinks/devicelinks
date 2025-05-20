package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.SysUser;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link SysUser} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class SysUserCacheEvictEvent {
    private String userId;
    private String account;
    private SysUser savedUser;
}

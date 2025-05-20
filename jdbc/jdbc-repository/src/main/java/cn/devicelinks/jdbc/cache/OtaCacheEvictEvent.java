package cn.devicelinks.jdbc.cache;

import cn.devicelinks.entity.Ota;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link cn.devicelinks.entity.Ota} Cache Evict Event
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Builder
public class OtaCacheEvictEvent {
    private String otaId;
    private Ota savedOta;
}

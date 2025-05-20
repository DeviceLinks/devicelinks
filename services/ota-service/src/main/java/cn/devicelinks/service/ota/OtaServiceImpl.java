package cn.devicelinks.service.ota;

import cn.devicelinks.common.OtaPackageType;
import cn.devicelinks.entity.Ota;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.cache.OtaCacheEvictEvent;
import cn.devicelinks.jdbc.cache.OtaCacheKey;
import cn.devicelinks.jdbc.repository.OtaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static cn.devicelinks.jdbc.tables.TOta.OTA;

/**
 * Ota业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class OtaServiceImpl extends CacheBaseServiceImpl<Ota, String, OtaRepository, OtaCacheKey, OtaCacheEvictEvent> implements OtaService {
    public OtaServiceImpl(OtaRepository repository) {
        super(repository);
    }

    @Override
    public void handleCacheEvictEvent(OtaCacheEvictEvent event) {
        Ota savedOta = event.getSavedOta();
        if (savedOta != null) {
            cache.put(OtaCacheKey.builder().otaId(savedOta.getId()).build(), savedOta);
        } else {
            if (!ObjectUtils.isEmpty(event.getOtaId())) {
                cache.evict(OtaCacheKey.builder().otaId(event.getOtaId()).build());
            }
        }
    }

    @Override
    public Ota selectByFirmwareId(String firmwareId) {
        return cache.get(OtaCacheKey.builder().otaId(firmwareId).build(),
                () -> this.repository.selectOne(OTA.ID.eq(firmwareId),
                        OTA.TYPE.eq(OtaPackageType.Firmware),
                        OTA.DELETED.eq(Boolean.FALSE)));
    }

    @Override
    public Ota selectBySoftwareId(String softwareId) {
        return cache.get(OtaCacheKey.builder().otaId(softwareId).build(),
                () -> this.repository.selectOne(OTA.ID.eq(softwareId),
                        OTA.TYPE.eq(OtaPackageType.Software),
                        OTA.DELETED.eq(Boolean.FALSE)));
    }
}

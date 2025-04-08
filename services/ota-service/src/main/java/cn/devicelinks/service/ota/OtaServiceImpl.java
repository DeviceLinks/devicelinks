package cn.devicelinks.service.ota;

import cn.devicelinks.framework.common.OtaPackageType;
import cn.devicelinks.framework.common.pojos.Ota;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.OtaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.devicelinks.framework.jdbc.tables.TOta.OTA;

/**
 * Ota业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class OtaServiceImpl extends BaseServiceImpl<Ota, String, OtaRepository> implements OtaService {
    public OtaServiceImpl(OtaRepository repository) {
        super(repository);
    }

    @Override
    public Ota selectByFirmwareId(String firmwareId) {
        return this.repository.selectOne(OTA.ID.eq(firmwareId),
                OTA.TYPE.eq(OtaPackageType.Firmware),
                OTA.DELETED.eq(Boolean.FALSE));
    }

    @Override
    public Ota selectBySoftwareId(String softwareId) {
        return this.repository.selectOne(OTA.ID.eq(softwareId),
                OTA.TYPE.eq(OtaPackageType.Software),
                OTA.DELETED.eq(Boolean.FALSE));
    }
}

package cn.devicelinks.service.ota;

import cn.devicelinks.entity.Ota;
import cn.devicelinks.jdbc.BaseService;

/**
 * Ota业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface OtaService extends BaseService<Ota, String> {
    /**
     * 根据固件ID查询Ota信息
     *
     * @param firmwareId 固件ID {@link Ota#getId()}
     * @return 固件Ota {@link Ota}
     */
    Ota selectByFirmwareId(String firmwareId);

    /**
     * 根据软件ID查询Ota信息
     *
     * @param softwareId 软件ID {@link Ota#getId()}
     * @return 软件Ota {@link Ota}
     */
    Ota selectBySoftwareId(String softwareId);
}

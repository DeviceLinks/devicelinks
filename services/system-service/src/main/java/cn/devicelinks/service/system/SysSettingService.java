package cn.devicelinks.service.system;

import cn.devicelinks.entity.SysSetting;
import cn.devicelinks.jdbc.BaseService;

import java.util.List;

/**
 * 系统参数设置业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysSettingService extends BaseService<SysSetting, String> {
    /**
     * 查询所有启用状态的系统参数设置
     *
     * @return 系统参数对象 {@link SysSetting}
     */
    List<SysSetting> selectEnabledList();

    /**
     * 更新系统参数值
     *
     * @param settingId 系统参数值ID {@link SysSetting#getId()}
     * @param value     参数值 {@link SysSetting#getValue()}
     * @return 返回更新后的系统参数值 {@link SysSetting}
     */
    SysSetting updateSettingValue(String settingId, String value);
}

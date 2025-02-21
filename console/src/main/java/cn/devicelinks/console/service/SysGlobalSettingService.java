package cn.devicelinks.console.service;

import cn.devicelinks.framework.common.pojos.SysGlobalSetting;
import cn.devicelinks.framework.jdbc.BaseService;

import java.util.List;

/**
 * 全局参数设置业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SysGlobalSettingService extends BaseService<SysGlobalSetting, String> {
    /**
     * 查询所有启用状态的全局参数设置
     *
     * @return 全局参数对象 {@link SysGlobalSetting}
     */
    List<SysGlobalSetting> selectEnabledList();

    /**
     * 更新全局参数值
     *
     * @param settingId 全局参数值ID {@link SysGlobalSetting#getId()}
     * @param value     参数值 {@link SysGlobalSetting#getValue()}
     * @return 返回更新后的全局参数值 {@link SysGlobalSetting}
     */
    SysGlobalSetting updateGlobalSettingValue(String settingId, String value);
}

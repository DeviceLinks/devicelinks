package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.StatusCodeConstants;
import cn.devicelinks.console.service.SysGlobalSettingService;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.GlobalSettingDataType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.SysGlobalSetting;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.SysGlobalSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TSysGlobalSetting.SYS_GLOBAL_SETTING;

/**
 * 全局参数设置业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysGlobalSettingServiceImpl extends BaseServiceImpl<SysGlobalSetting, String, SysGlobalSettingRepository> implements SysGlobalSettingService {
    public SysGlobalSettingServiceImpl(SysGlobalSettingRepository repository) {
        super(repository);
    }

    @Override
    public List<SysGlobalSetting> selectEnabledList() {
        return this.repository.select(SYS_GLOBAL_SETTING.CREATE_TIME.desc(), SYS_GLOBAL_SETTING.ENABLED.eq(Boolean.TRUE));
    }

    @Override
    public SysGlobalSetting updateGlobalSettingValue(String settingId, String value) {
        // Retrieve the global setting from the database
        SysGlobalSetting globalSetting = this.selectById(settingId);

        if (globalSetting == null) {
            throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_NOT_FOUND, settingId);
        }

        // Check the data type of the global setting
        GlobalSettingDataType dataType = globalSetting.getDataType();

        // Perform validation based on the data type
        switch (dataType) {
            case DateTime:
                // Validate the value as a valid date and time
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
                dateTimeFormat.setLenient(false);
                try {
                    dateTimeFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            case Date:
                // Validate the value as a valid date
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_YYYY_MM_DD);
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            case Time:
                // Validate the value as a valid time
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_HH_MM_SS);
                    timeFormat.setLenient(false);
                    timeFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            case Bool:
                // Validate the value as a boolean (true/false)
                if (!Boolean.TRUE.toString().equalsIgnoreCase(value) && !Boolean.FALSE.toString().equalsIgnoreCase(value)) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            case Number:
                // Validate the value as a valid number
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            case Decimal:
                // Validate the value as a valid decimal number
                try {
                    new BigDecimal(value);
                } catch (NumberFormatException e) {
                    throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_MISMATCH, globalSetting.getName());
                }
                break;
            default:
                throw new ApiException(StatusCodeConstants.GLOBAL_SETTING_VALUE_TYPE_NOT_SUPPORT, globalSetting.getName(), dataType.name());
        }
        // Update the value of the global setting in the database
        globalSetting.setValue(value);
        this.update(globalSetting);
        return globalSetting;
    }
}

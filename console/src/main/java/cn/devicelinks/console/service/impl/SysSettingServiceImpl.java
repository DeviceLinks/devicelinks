package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.service.SysSettingService;
import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.SysSettingDataType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.SysSetting;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.SysSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TSysSetting.SYS_SETTING;

/**
 * 系统参数设置业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class SysSettingServiceImpl extends BaseServiceImpl<SysSetting, String, SysSettingRepository> implements SysSettingService {
    public SysSettingServiceImpl(SysSettingRepository repository) {
        super(repository);
    }

    @Override
    public List<SysSetting> selectEnabledList() {
        return this.repository.select(SYS_SETTING.CREATE_TIME.desc(), SYS_SETTING.ENABLED.eq(Boolean.TRUE));
    }

    @Override
    public SysSetting updateSettingValue(String settingId, String value) {
        // Retrieve the global setting from the database
        SysSetting setting = this.selectById(settingId);

        if (setting == null) {
            throw new ApiException(StatusCodeConstants.SYS_SETTING_NOT_FOUND, settingId);
        }

        // Check the data type of the global setting
        SysSettingDataType dataType = setting.getDataType();

        // Perform validation based on the data type
        switch (dataType) {
            case DateTime:
                // Validate the value as a valid date and time
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
                dateTimeFormat.setLenient(false);
                try {
                    dateTimeFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            case Date:
                // Validate the value as a valid date
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_YYYY_MM_DD);
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            case Time:
                // Validate the value as a valid time
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_HH_MM_SS);
                    timeFormat.setLenient(false);
                    timeFormat.parse(value);
                } catch (ParseException e) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            case Bool:
                // Validate the value as a boolean (true/false)
                if (!Boolean.TRUE.toString().equalsIgnoreCase(value) && !Boolean.FALSE.toString().equalsIgnoreCase(value)) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            case Number:
                // Validate the value as a valid number
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            case Decimal:
                // Validate the value as a valid decimal number
                try {
                    new BigDecimal(value);
                } catch (NumberFormatException e) {
                    throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_MISMATCH, setting.getName());
                }
                break;
            default:
                throw new ApiException(StatusCodeConstants.SYS_SETTING_VALUE_TYPE_NOT_SUPPORT, setting.getName(), dataType.name());
        }
        // Update the value of the global setting in the database
        setting.setValue(value);
        this.update(setting);
        return setting;
    }
}

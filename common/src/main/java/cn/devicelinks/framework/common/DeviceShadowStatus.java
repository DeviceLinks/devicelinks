package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备影子状态
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceShadowStatus {
    Consistency("数据一致", EnumShowStyle.Success),
    WaitSync("待同步", EnumShowStyle.Warning),
    InSync("同步中", EnumShowStyle.Processing),
    Error("同步失败", EnumShowStyle.Error);

    private final String description;
    private final EnumShowStyle showStyle;

    DeviceShadowStatus(String description, EnumShowStyle showStyle) {
        this.description = description;
        this.showStyle = showStyle;
    }
}

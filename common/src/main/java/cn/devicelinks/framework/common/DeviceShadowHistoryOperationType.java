package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 设备影子操作类型
 *
 * @author 恒宇少年
 * @since 1.0
 */
@ApiEnum
@Getter
public enum DeviceShadowHistoryOperationType {
    Update("更新"),
    ConflictResolve("解决冲突"),
    RollBack("回滚");
    private final String description;

    DeviceShadowHistoryOperationType(String description) {
        this.description = description;
    }
}

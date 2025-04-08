package cn.devicelinks.service.device;

import cn.devicelinks.framework.common.pojos.DeviceTag;
import cn.devicelinks.framework.jdbc.BaseService;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;

import java.util.List;

/**
 * 设备标签业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceTagService extends BaseService<DeviceTag, String> {
    /**
     * 根据检索字段查询设备标签列表
     *
     * @param searchFieldConditionList 检索字段列表
     * @return 设备标签列表 {@link DeviceTag}
     */
    List<DeviceTag> selectDeviceTagList(List<SearchFieldCondition> searchFieldConditionList);

    /**
     * 新增设备标签
     *
     * @param deviceTag 设备标签
     * @return 已添加的设备标签对象实例
     */
    DeviceTag addDeviceTag(DeviceTag deviceTag);

    /**
     * 根据ID删除设备标签
     *
     * @param tagId 设备标签ID {@link DeviceTag#getId()}
     * @return 已删除的设备标签
     */
    DeviceTag deleteDeviceTag(String tagId);
}

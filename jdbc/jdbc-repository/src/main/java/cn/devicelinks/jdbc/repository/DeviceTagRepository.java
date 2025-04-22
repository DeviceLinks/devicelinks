package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceTag;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;

import java.util.List;

/**
 * 设备标签数据接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceTagRepository extends Repository<DeviceTag, String> {
    /**
     * 根据检索字段查询设备标签列表
     *
     * @param searchFieldConditionList 检索字段列表
     * @return 设备标签列表 {@link DeviceTag}
     */
    List<DeviceTag> selectDeviceTagList(List<SearchFieldCondition> searchFieldConditionList);
}

package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.DeviceAttributeDesiredDTO;
import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * 设备期望属性数据接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeDesiredRepository extends Repository<DeviceAttributeDesired, String> {

    /**
     * 分页获取设备期望属性
     *
     * @param searchFieldConditionList 检索字段查询条件列表
     * @param pageQuery                分页查询对象
     * @param sortCondition            排序条件
     * @return 设备期望属性分页对象
     */
    PageResult<DeviceAttributeDesiredDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);
}

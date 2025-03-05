package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.core.Repository;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDesiredDTO;

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

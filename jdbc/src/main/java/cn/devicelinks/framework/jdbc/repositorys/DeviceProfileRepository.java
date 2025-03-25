package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.jdbc.core.Repository;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * 设备配置文件数据接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceProfileRepository extends Repository<DeviceProfile, String> {
    /**
     * 分页查询设备配置文件列表
     *
     * @param searchFieldConditionList 检索字段条件列表
     * @param pageQuery                分页查询
     * @param sortCondition            排序条件
     * @return {@link DeviceProfile}
     */
    PageResult<DeviceProfile> getDeviceProfileListByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);
}

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.pojos.DeviceTag;
import cn.devicelinks.framework.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.FusionCondition;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceTag.DEVICE_TAG;

/**
 * 设备标签数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceTagJdbcRepository extends JdbcRepository<DeviceTag, String> implements DeviceTagRepository {
    public DeviceTagJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_TAG, jdbcOperations);
    }

    @Override
    public List<DeviceTag> selectDeviceTagList(List<SearchFieldCondition> searchFieldConditionList) {
        Condition[] conditions = this.searchFieldConditionToConditionArray(searchFieldConditionList);
        FusionCondition fusionCondition = FusionCondition.withConditions(conditions).build();
        return this.select(fusionCondition);
    }
}

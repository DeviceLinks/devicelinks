package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.Condition;
import cn.devicelinks.jdbc.core.sql.FusionCondition;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceProfile.DEVICE_PROFILE;

/**
 * The {@link DeviceProfile} Jdbc Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
public class DeviceProfileJdbcRepository extends JdbcRepository<DeviceProfile, String> implements DeviceProfileRepository {
    public DeviceProfileJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_PROFILE, jdbcOperations);
    }

    @Override
    public PageResult<DeviceProfile> getDeviceProfileListByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        Condition[] conditions = this.searchFieldConditionToConditionArray(searchFieldConditionList);
        // @formatter:off
        FusionCondition fusionCondition = FusionCondition
                .withSort(sortCondition)
                .conditions(conditions)
                .build();
        // @formatter:on
        return this.page(fusionCondition, pageQuery);
    }
}

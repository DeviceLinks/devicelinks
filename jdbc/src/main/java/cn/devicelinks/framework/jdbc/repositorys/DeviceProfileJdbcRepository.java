package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceProfile;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.FusionCondition;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceProfile.DEVICE_PROFILE;

/**
 * The {@link DeviceProfile} Jdbc Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
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

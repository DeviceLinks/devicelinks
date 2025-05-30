package cn.devicelinks.jdbc.repository;

import cn.devicelinks.entity.DeviceProfile;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.*;
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

    @Override
    public DeviceProfile getByProvisionKey(String provisionKey) {
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(DEVICE_PROFILE.getQuerySql())
                .appendCondition(Boolean.TRUE, "json_extract(" + DEVICE_PROFILE.PROVISION_ADDITION.getName() + ", '$.provisionDeviceKey') = ?", provisionKey)
                .resultColumns(columns -> columns.addAll(DEVICE_PROFILE.getColumns()))
                .resultType(DeviceProfile.class)
                .build();
        // @formatter:on
        List<DeviceProfile> deviceProfileList = this.dynamicSelect(wrapper.dynamic(), wrapper.parameters());
        return deviceProfileList.isEmpty() ? null : deviceProfileList.getFirst();
    }
}

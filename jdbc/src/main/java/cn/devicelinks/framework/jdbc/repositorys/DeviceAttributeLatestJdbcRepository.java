package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeLatest;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Dynamic;
import cn.devicelinks.framework.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_ADDITION;
import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_DATA_TYPE;
import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeLatest.DEVICE_ATTRIBUTE_LATEST;

/**
 * 设备属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeLatestJdbcRepository extends JdbcRepository<DeviceAttributeLatest, String> implements DeviceAttributeLatestRepository {
    // @formatter:off
    private static final String SELECT_ATTRIBUTE_REPORTED_DTO_SQL = "select dal.*," +
            " a.name attribute_name," +
            " a.data_type attribute_data_type," +
            " a.addition attribute_addition," +
            " a.description attribute_description" +
            " from device_attribute_latest dal" +
            " left join attribute a on a.id = dal.attribute_id";
    // @formatter:on
    private static final Column COLUMN_ATTRIBUTE_NAME = Column.withName("attribute_name").build();
    private static final Column COLUMN_ATTRIBUTE_DATA_TYPE = Column.withName("attribute_data_type").typeMapper(ATTRIBUTE_DATA_TYPE).build();
    private static final Column COLUMN_ATTRIBUTE_ADDITION = Column.withName("attribute_addition").typeMapper(ATTRIBUTE_ADDITION).build();
    private static final Column COLUMN_ATTRIBUTE_DESCRIPTION = Column.withName("attribute_description").build();

    public DeviceAttributeLatestJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE_LATEST, jdbcOperations);
    }

    @Override
    public PageResult<DeviceAttributeLatestDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_ATTRIBUTE_REPORTED_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(DEVICE_ATTRIBUTE_LATEST.getColumns());
                    resultColumns.add(COLUMN_ATTRIBUTE_NAME);
                    resultColumns.add(COLUMN_ATTRIBUTE_DATA_TYPE);
                    resultColumns.add(COLUMN_ATTRIBUTE_ADDITION);
                    resultColumns.add(COLUMN_ATTRIBUTE_DESCRIPTION);
                })
                .appendSearchFieldCondition(DEVICE_ATTRIBUTE_LATEST, searchFieldConditionList, consumer -> consumer.tableAlias("dal"))
                .sort(sortCondition);
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(DeviceAttributeLatestDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, pageQuery, wrapper.parameters());

    }
}

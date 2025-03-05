package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Dynamic;
import cn.devicelinks.framework.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDesiredDTO;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_ADDITION;
import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_DATA_TYPE;
import static cn.devicelinks.framework.jdbc.tables.TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED;

/**
 * 设备期望属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeDesiredJdbcRepository extends JdbcRepository<DeviceAttributeDesired, String> implements DeviceAttributeDesiredRepository {
    // @formatter:off
    private static final String SELECT_ATTRIBUTE_DESIRED_DTO_SQL = "select dad.*," +
            " a.name attribute_name," +
            " a.data_type attribute_data_type," +
            " a.addition attribute_addition," +
            " a.description attribute_description" +
            " from device_attribute_desired dad" +
            " left join attribute a on a.id = dad.attribute_id";
    // @formatter:on
    private static final Column COLUMN_ATTRIBUTE_NAME = Column.withName("attribute_name").build();
    private static final Column COLUMN_ATTRIBUTE_DATA_TYPE = Column.withName("attribute_data_type").typeMapper(ATTRIBUTE_DATA_TYPE).build();
    private static final Column COLUMN_ATTRIBUTE_ADDITION = Column.withName("attribute_addition").typeMapper(ATTRIBUTE_ADDITION).build();
    private static final Column COLUMN_ATTRIBUTE_DESCRIPTION = Column.withName("attribute_description").build();

    public DeviceAttributeDesiredJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE_DESIRED, jdbcOperations);
    }

    @Override
    public PageResult<DeviceAttributeDesiredDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_ATTRIBUTE_DESIRED_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(DEVICE_ATTRIBUTE_DESIRED.getColumns());
                    resultColumns.add(COLUMN_ATTRIBUTE_NAME);
                    resultColumns.add(COLUMN_ATTRIBUTE_DATA_TYPE);
                    resultColumns.add(COLUMN_ATTRIBUTE_ADDITION);
                    resultColumns.add(COLUMN_ATTRIBUTE_DESCRIPTION);
                })
                .appendSearchFieldCondition(DEVICE_ATTRIBUTE_DESIRED, searchFieldConditionList, consumer -> consumer.tableAlias("dad"))
                .sort(sortCondition);
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(DeviceAttributeDesiredDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, pageQuery, wrapper.parameters());
    }
}

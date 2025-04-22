package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.DeviceAttributeDesiredDTO;
import cn.devicelinks.entity.DeviceAttributeDesired;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.definition.DynamicColumn;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.Dynamic;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;
import cn.devicelinks.jdbc.tables.TAttribute;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED;

/**
 * 设备期望属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
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

    public DeviceAttributeDesiredJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE_DESIRED, jdbcOperations);
    }

    @Override
    public PageResult<DeviceAttributeDesiredDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_ATTRIBUTE_DESIRED_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(DEVICE_ATTRIBUTE_DESIRED.getColumns());

                    resultColumns.add(DynamicColumn.withColumn(TAttribute.ATTRIBUTE.NAME).alias("attribute_name").build());
                    resultColumns.add(DynamicColumn.withColumn(TAttribute.ATTRIBUTE.DATA_TYPE).alias("attribute_data_type").build());
                    resultColumns.add(DynamicColumn.withColumn(TAttribute.ATTRIBUTE.ADDITION).alias("attribute_addition").build());
                    resultColumns.add(DynamicColumn.withColumn(TAttribute.ATTRIBUTE.DESCRIPTION).alias("attribute_description").build());

                })
                .appendSearchFieldCondition(DEVICE_ATTRIBUTE_DESIRED, searchFieldConditionList, consumer -> consumer.tableAlias("dad"))
                .sort(sortCondition);
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(DeviceAttributeDesiredDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, pageQuery, wrapper.parameters());
    }
}

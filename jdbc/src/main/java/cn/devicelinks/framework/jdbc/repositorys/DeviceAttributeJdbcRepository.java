package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.DeviceAttribute;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Dynamic;
import cn.devicelinks.framework.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeDTO;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeLatestDTO;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_ADDITION;
import static cn.devicelinks.framework.jdbc.ColumnValueMappers.ATTRIBUTE_DATA_TYPE;
import static cn.devicelinks.framework.jdbc.tables.TDeviceAttribute.DEVICE_ATTRIBUTE;

/**
 * 设备属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class DeviceAttributeJdbcRepository extends JdbcRepository<DeviceAttribute, String> implements DeviceAttributeRepository {
    // @formatter:off
    private static final String SELECT_ATTRIBUTE_REPORTED_DTO_SQL = "select da.*," +
            " a.name attribute_name," +
            " a.data_type attribute_data_type," +
            " a.addition attribute_addition," +
            " a.description attribute_description" +
            " from device_attribute da" +
            " left join attribute a on a.id = da.attribute_id";

    private static final String SELECT_ATTRIBUTE_LATEST_DTO_SQL = "select a.id attribute_id," +
            " a.name attribute_name," +
            " a.identifier," +
            " a.data_type attribute_data_type," +
            " a.scope," +
            " au.name unit_name," +
            " da.value last_report_value," +
            " da.value_source," +
            " da.last_update_time last_report_time," +
            " dad.desired_value last_desired_value," +
            " dad.last_update_time last_desired_time" +
            " from attribute a" +
            " left join device_attribute da on da.attribute_id = a.id" +
            " left join device_attribute_desired dad on dad.attribute_id = a.id" +
            " left join attribute_unit au on au.id = json_unquote(json_extract(a.addition, '$.unitId'))";
    // @formatter:on

    private static final Column COLUMN_ATTRIBUTE_ID = cn.devicelinks.framework.jdbc.core.definition.Column.withName("attribute_id").build();
    private static final Column COLUMN_ATTRIBUTE_NAME = Column.withName("attribute_name").build();
    private static final Column COLUMN_ATTRIBUTE_IDENTIFIER = Column.withName("identifier").build();
    private static final Column COLUMN_ATTRIBUTE_SCOPE = Column.withName("scope").typeMapper(ColumnValueMappers.ATTRIBUTE_SCOPE).build();
    private static final Column COLUMN_UNIT_NAME = Column.withName("unit_name").build();
    private static final Column COLUMN_ATTRIBUTE_VALUE_SOURCE = Column.withName("value_source").typeMapper(ColumnValueMappers.ATTRIBUTE_VALUE_SOURCE).build();
    private static final Column COLUMN_LAST_REPORT_VALUE = Column.withName("last_report_value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    private static final Column COLUMN_LAST_REPORT_TIME = Column.withName("last_report_time").localDateTimeValue().build();
    private static final Column COLUMN_LAST_DESIRED_VALUE = Column.withName("last_desired_value").typeMapper(ColumnValueMappers.JSON_OBJECT).build();
    private static final Column COLUMN_LAST_DESIRED_TIME = Column.withName("last_desired_time").localDateTimeValue().build();

    private static final Column COLUMN_ATTRIBUTE_DATA_TYPE = Column.withName("attribute_data_type").typeMapper(ATTRIBUTE_DATA_TYPE).build();
    private static final Column COLUMN_ATTRIBUTE_ADDITION = Column.withName("attribute_addition").typeMapper(ATTRIBUTE_ADDITION).build();
    private static final Column COLUMN_ATTRIBUTE_DESCRIPTION = Column.withName("attribute_description").build();

    public DeviceAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE, jdbcOperations);
    }

    @Override
    public PageResult<DeviceAttributeDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_ATTRIBUTE_REPORTED_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(DEVICE_ATTRIBUTE.getColumns());
                    resultColumns.add(COLUMN_ATTRIBUTE_NAME);
                    resultColumns.add(COLUMN_ATTRIBUTE_DATA_TYPE);
                    resultColumns.add(COLUMN_ATTRIBUTE_ADDITION);
                    resultColumns.add(COLUMN_ATTRIBUTE_DESCRIPTION);
                })
                .appendSearchFieldCondition(DEVICE_ATTRIBUTE, searchFieldConditionList, consumer -> consumer.tableAlias("da"))
                .sort(sortCondition);
        // @formatter:on

        DynamicWrapper wrapper = selectBuilder.resultType(DeviceAttributeDTO.class).build();
        Dynamic dynamic = wrapper.dynamic();
        return this.page(dynamic, pageQuery, wrapper.parameters());
    }

    @Override
    public List<DeviceAttributeLatestDTO> getLatestAttribute(String deviceId, String moduleId, String attributeName, String attributeIdentifier) {
        Assert.hasText(deviceId, "The deviceId cannot be empty");
        Assert.hasText(moduleId, "The moduleId cannot be empty");
        // @formatter:off
        DynamicWrapper wrapper = DynamicWrapper.select(SELECT_ATTRIBUTE_LATEST_DTO_SQL)
                .appendCondition(Boolean.TRUE, "da.device_id = ?", deviceId)
                .appendCondition(Boolean.TRUE, "and a.module_id = ?", moduleId)
                .appendCondition(!ObjectUtils.isEmpty(attributeName), "and a.name like ?", "%" + attributeName + "%")
                .appendCondition(!ObjectUtils.isEmpty(attributeIdentifier), "and a.identifier = ?", attributeIdentifier)
                .resultColumns(columns -> {
                    columns.add(COLUMN_ATTRIBUTE_ID);
                    columns.add(COLUMN_ATTRIBUTE_NAME);
                    columns.add(COLUMN_ATTRIBUTE_IDENTIFIER);
                    columns.add(COLUMN_ATTRIBUTE_DATA_TYPE);
                    columns.add(COLUMN_ATTRIBUTE_SCOPE);
                    columns.add(COLUMN_UNIT_NAME);
                    columns.add(COLUMN_ATTRIBUTE_VALUE_SOURCE);
                    columns.add(COLUMN_LAST_REPORT_VALUE);
                    columns.add(COLUMN_LAST_REPORT_TIME);
                    columns.add(COLUMN_LAST_DESIRED_VALUE);
                    columns.add(COLUMN_LAST_DESIRED_TIME);
                })
                .resultType(DeviceAttributeLatestDTO.class)
                .build();
        // @formatter:on
        Dynamic dynamic = wrapper.dynamic();
        return this.dynamicSelect(dynamic, wrapper.parameters());
    }
}

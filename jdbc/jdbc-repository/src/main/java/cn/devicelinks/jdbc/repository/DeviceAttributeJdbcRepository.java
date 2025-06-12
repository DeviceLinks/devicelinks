package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.common.AttributeValueSource;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.jdbc.annotation.DeviceLinksRepository;
import cn.devicelinks.jdbc.core.JdbcRepository;
import cn.devicelinks.jdbc.core.definition.DynamicColumn;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.Dynamic;
import cn.devicelinks.jdbc.core.sql.DynamicWrapper;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TAttribute.ATTRIBUTE;
import static cn.devicelinks.jdbc.tables.TAttributeUnit.ATTRIBUTE_UNIT;
import static cn.devicelinks.jdbc.tables.TDeviceAttribute.DEVICE_ATTRIBUTE;
import static cn.devicelinks.jdbc.tables.TDeviceAttributeDesired.DEVICE_ATTRIBUTE_DESIRED;

/**
 * 设备属性数据接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@DeviceLinksRepository
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

    public DeviceAttributeJdbcRepository(JdbcOperations jdbcOperations) {
        super(DEVICE_ATTRIBUTE, jdbcOperations);
    }

    @Override
    public PageResult<DeviceAttributeDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(SELECT_ATTRIBUTE_REPORTED_DTO_SQL)
                .resultColumns(resultColumns -> {
                    resultColumns.addAll(DEVICE_ATTRIBUTE.getColumns());
                    resultColumns.add(DynamicColumn.withColumn(ATTRIBUTE.NAME).alias("attribute_name").build());
                    resultColumns.add(DynamicColumn.withColumn(ATTRIBUTE.DATA_TYPE).alias("attribute_data_type").build());
                    resultColumns.add(DynamicColumn.withColumn(ATTRIBUTE.ADDITION).alias("attribute_addition").build());
                    resultColumns.add(DynamicColumn.withColumn(ATTRIBUTE.DESCRIPTION).alias("attribute_description").build());
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
                    columns.add(DynamicColumn.withColumn(ATTRIBUTE.ID).alias("attribute_id").build());
                    columns.add(DynamicColumn.withColumn(ATTRIBUTE.NAME).alias("attribute_name").build());
                    columns.add(DynamicColumn.withColumn(ATTRIBUTE.IDENTIFIER).build());
                    columns.add(DynamicColumn.withColumn(ATTRIBUTE.DATA_TYPE).alias("attribute_data_type").build());
                    columns.add(DynamicColumn.withColumn(ATTRIBUTE_UNIT.NAME).alias("unit_name").build());
                    columns.add(DynamicColumn.withColumn(DEVICE_ATTRIBUTE.VALUE_SOURCE).build());
                    columns.add(DynamicColumn.withColumn(DEVICE_ATTRIBUTE.VALUE).alias("last_report_value").build());
                    columns.add(DynamicColumn.withColumn(DEVICE_ATTRIBUTE.LAST_UPDATE_TIME).alias("last_report_time").build());
                    columns.add(DynamicColumn.withColumn(DEVICE_ATTRIBUTE_DESIRED.DESIRED_VALUE).alias("last_desired_value").build());
                    columns.add(DynamicColumn.withColumn(DEVICE_ATTRIBUTE_DESIRED.LAST_UPDATE_TIME).alias("last_desired_time").build());
                })
                .resultType(DeviceAttributeLatestDTO.class)
                .build();
        // @formatter:on
        Dynamic dynamic = wrapper.dynamic();
        return this.dynamicSelect(dynamic, wrapper.parameters());
    }

    @Override
    public List<DeviceAttribute> selectDeviceAttributes(String deviceId, AttributeValueSource valueSource, String[] identifiers) {
        // @formatter:off
        DynamicWrapper.SelectBuilder selectBuilder = DynamicWrapper.select(DEVICE_ATTRIBUTE.getQuerySql())
                .and(DEVICE_ATTRIBUTE.DEVICE_ID.eq(deviceId))
                .and(DEVICE_ATTRIBUTE.VALUE_SOURCE.eq(valueSource));
        if(!ObjectUtils.isEmpty(identifiers)) {
            selectBuilder.and(!ObjectUtils.isEmpty(identifiers),
                    DEVICE_ATTRIBUTE.IDENTIFIER.in(Arrays.stream(identifiers).map(identifier -> (Object) identifier).toList()));
        }
        DynamicWrapper wrapper = selectBuilder
                .resultColumns(columns -> columns.addAll(DEVICE_ATTRIBUTE.getColumns()))
                .resultType(DeviceAttribute.class)
                .build();
        // @formatter:on
        Dynamic dynamic = wrapper.dynamic();
        return this.dynamicSelect(dynamic, wrapper.parameters());
    }
}

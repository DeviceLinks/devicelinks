package cn.devicelinks.framework.jdbc.core.definition;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@link Table}抽象实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class TableImpl implements Table {
    private static final String PLACEHOLDER = "?";
    private static final String DELIMITER = ", ";
    private static final String COLUMN_EQ_VALUE = "%s = ?";
    private static final String INSERT_SQL_STENCIL = "insert into %s (%s) values (%s)";
    private static final String DELETE_SQL_STENCIL = "delete from %s";
    private static final String UPDATE_SQL_STENCIL = "update %s set %s";
    private static final String QUERY_SQL_STENCIL = "select %s from %s";
    private final String tableName;

    public TableImpl(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Column getPk() {
        // @formatter:off
        Optional<Column> optional =
                this.getColumns().stream()
                        .filter(Column::isPk).findFirst();
        // @formatter:on
        return optional.orElse(null);
    }

    @Override
    public boolean containsColumn(String columnName) {
        return getColumns().stream().collect(Collectors.toMap(Column::getName, v -> v)).containsKey(columnName);
    }

    @Override
    public Column getColumn(String columnName) {
        return getColumns().stream().collect(Collectors.toMap(Column::getName, v -> v)).get(columnName);
    }

    @Override
    public List<Column> getInsertableColumns() {
        // @formatter:off
        return this.getColumns()
                .stream()
                .filter(Column::isInsertable)
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public List<Column> getUpdatableColumns() {
        // @formatter:off
        return this.getColumns()
                .stream()
                .filter(Column::isUpdatable)
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public String getBaseQuerySql() {
        // @formatter:off
        return String.format(QUERY_SQL_STENCIL,
                this.getColumnSql(false, false),
                this.tableName);
        // @formatter:on
    }

    @Override
    public String getQuerySql() {
        // @formatter:off
        return String.format(QUERY_SQL_STENCIL,
                this.getColumnSql(false, false),
                this.tableName);
        // @formatter:on
    }

    @Override
    public String getInsertSql() {
        // @formatter:off
        String values = this.getInsertableColumns()
                .stream()
                .map(tableColumn -> PLACEHOLDER)
                .collect(Collectors.joining(DELIMITER));
        return String.format(INSERT_SQL_STENCIL,
                this.tableName,
                this.getColumnSql(true,false),
                values);
        // @formatter:on
    }

    @Override
    public String getUpdateSql() {
        // @formatter:off
        String updateColumnSql = this.getUpdatableColumns()
                .stream()
                .map(tableColumn -> String.format(COLUMN_EQ_VALUE, tableColumn.getName()))
                .collect(Collectors.joining(DELIMITER));
        // @formatter:on
        return String.format(UPDATE_SQL_STENCIL, this.tableName, updateColumnSql);
    }

    @Override
    public String getUpdateSql(List<Column> columnList) {
        if (!ObjectUtils.isEmpty(columnList)) {
            // @formatter:off
            String updateColumnSql = columnList.stream()
                    .map(column -> String.format(COLUMN_EQ_VALUE, column.getName()))
                    .collect(Collectors.joining(DELIMITER));
            // @formatter:on
            return String.format(UPDATE_SQL_STENCIL, this.tableName, updateColumnSql);
        }
        return null;
    }

    @Override
    public String getDeleteSql() {
        return String.format(DELETE_SQL_STENCIL, this.tableName);
    }

    private String getColumnSql(boolean filterInsertable, boolean filterUpdatable) {
        // @formatter:off
        return this.getColumns().stream()
                .filter(tableColumn -> {
                    if(filterInsertable) {
                        return tableColumn.isInsertable();
                    } else if(filterUpdatable) {
                        return tableColumn.isUpdatable();
                    }
                    return true;
                })
                .map(Column::getName).distinct()
                .collect(Collectors.joining(DELIMITER));
        // @formatter:on
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String toString() {
        return "Table(tableName=" + this.tableName + ", columnArray=" + Arrays.toString(this.getColumns().toArray(Column[]::new)) + ")";
    }
}

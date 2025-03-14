package cn.devicelinks.framework.jdbc.core.definition;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * Columns for dynamic queries
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class DynamicColumn extends Column {

    /**
     * The result column alias
     */
    private final String alias;

    private DynamicColumn(Column column, String alias) {
        super(column.getName(), column.isPk(), column.isInsertable(), column.isUpdatable(),
                column.getSqlType(), column.getColumnValueMapper(), column.getIdGenerationStrategy(), column.getDefaultValueSupplier());
        this.alias = alias;
    }

    public static DynamicColumnBuilder withColumn(Column column) {
        return new DynamicColumnBuilder(column);
    }

    public static class DynamicColumnBuilder {
        private final Column column;
        private String alias;

        public DynamicColumnBuilder(Column column) {
            this.column = column;
        }

        public DynamicColumnBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public DynamicColumn build() {
            Assert.notNull(column, "column cannot be null");
            return new DynamicColumn(column, alias);
        }
    }
}

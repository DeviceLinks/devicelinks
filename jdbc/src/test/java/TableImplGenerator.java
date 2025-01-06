import cn.devicelinks.framework.common.pojos.SysGlobalSetting;
import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.*;

/**
 * TableImpl生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TableImplGenerator {
    public static void main(String[] args) {
        generate(SysGlobalSetting.class, new HashMap<>() {
            {
                put("dataType", ".typeMapper(ColumnValueMappers.GLOBAL_SETTING_TYPE)");
                put("multivalued", ".booleanValue()");
                put("allowSelfSet", ".booleanValue()");

                put("addition", ".typeMapper(ColumnValueMappers.JSON_MAP)");
                put("enabled", ".booleanValue()");
                put("deleted", ".booleanValue()");
                put("createTime", ".localDateTimeValue()");
            }
        });
    }

    /**
     * 生成TableImpl并在控制台输出
     *
     * @param entityClass     生成的基类
     * @param columnMapperMap 列映射集合
     */
    static void generate(Class<?> entityClass, Map<String, String> columnMapperMap) {
        String className = entityClass.getSimpleName();
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
        String tableNameUpper = tableName.toUpperCase();
        System.out.println("/**\n" +
                " * The {@link " + className + "} TableImpl\n" +
                " *\n" +
                " * @author 恒宇少年\n" +
                " * @since 1.0\n" +
                " */");
        System.out.println("public class T" + className + " extends TableImpl {");
        System.out.println("\t@Serial\n" +
                "    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;");
        System.out.println("\tpublic static final T" + className + " " + tableNameUpper + " = new T" + className + "(\"" + tableName + "\");");

        System.out.println("\tprivate T" + className + "(String tableName) {\n" +
                "        super(tableName);\n" +
                "    }");

        // Generate Columns
        Field[] fields = entityClass.getDeclaredFields();
        List<String> columnUpperNameList = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            // ignore serialVersionUID
            if (field.getName().equals("serialVersionUID")) {
                return;
            }
            String fieldName = field.getName();
            String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
            String columnNameUpper = columnName.toUpperCase();
            String temp = "\tpublic final Column " + columnNameUpper + " = Column.withName(\"" + columnName + "\")";
            // id is primary key
            if (field.getName().equals("id")) {
                temp += ".primaryKey()";
            }
            // append column mapper
            if (columnMapperMap.containsKey(fieldName)) {
                temp += columnMapperMap.get(fieldName);
            }
            temp += ".build();";
            System.out.println(temp);
            columnUpperNameList.add(columnNameUpper);
        });

        StringJoiner joiner = new StringJoiner(", ");
        columnUpperNameList.forEach(joiner::add);
        System.out.println("\t@Override\n" +
                "    public List<Column> getColumns() {\n" +
                "        return List.of(" + joiner + ");\n" +
                "    }");

        System.out.println("}");
    }
}

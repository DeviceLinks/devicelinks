import cn.devicelinks.framework.common.pojos.SysUserSession;
import com.google.common.base.CaseFormat;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 * TableImpl生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TableImplGenerator {

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String OUTPUT_PATH = PROJECT_PATH + "/jdbc/src/main/java/cn/devicelinks/framework/jdbc/tables";

    public static void main(String[] args) throws Exception {
        // entity class
        Class<?> entityClass = SysUserSession.class;
        // java content
        String content = generate(entityClass, new HashMap<>() {
            {
                put("platformType", ".typeMapper(ColumnValueMappers.PLATFORM_TYPE)");
                put("status", ".typeMapper(ColumnValueMappers.SESSION_STATUS)");
                put("issuedTime",".localDateTimeValue()");
                put("expiresTime",".localDateTimeValue()");
                put("logoutTime",".localDateTimeValue()");
                put("lastActiveTime",".localDateTimeValue()");

                //put("level", ".typeMapper(ColumnValueMappers.DEVICE_NETWORKING_AWAY)");

                put("addition", ".typeMapper(ColumnValueMappers.SYS_LOG_ADDITION)");
                put("enabled", ".booleanValue()");
                put("deleted", ".booleanValue()");
                put("createTime", ".localDateTimeValue()");
            }
        });
        System.out.println(content);
        // write java content to file
        writing(content, entityClass);
    }

    /**
     * 生成TableImpl并在控制台输出
     *
     * @param entityClass     生成的基类
     * @param columnMapperMap 列映射集合
     */
    static String generate(Class<?> entityClass, Map<String, String> columnMapperMap) {
        String lineSeparator = System.lineSeparator();
        String className = entityClass.getSimpleName();
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
        String tableNameUpper = tableName.toUpperCase();
        StringBuffer buffer = new StringBuffer();
        buffer.append("package cn.devicelinks.framework.jdbc.tables;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.common.DeviceLinksVersion;").append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.jdbc.core.definition.Column;").append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.jdbc.core.definition.TableImpl;").append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.jdbc.ColumnValueMappers;").append(lineSeparator);
        buffer.append("import ").append(entityClass.getName()).append(";").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("import java.io.Serial;").append(lineSeparator);
        buffer.append("import java.util.List;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("/**" + lineSeparator +
                " * The {@link " + className + "} TableImpl" + lineSeparator +
                " *" + lineSeparator +
                " * @author 恒宇少年" + lineSeparator +
                " * @since 1.0" + lineSeparator +
                " */");
        buffer.append(lineSeparator + "public class T" + className + " extends TableImpl {" + lineSeparator);
        buffer.append("\t@Serial" + lineSeparator +
                "    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;" + lineSeparator);
        buffer.append("\tpublic static final T" + className + " " + tableNameUpper + " = new T" + className + "(\"" + tableName + "\");" + lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("\tprivate T" + className + "(String tableName) {" + lineSeparator +
                "        super(tableName);" + lineSeparator +
                "    }");
        buffer.append(lineSeparator);
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
            String temp = lineSeparator + "\tpublic final Column " + columnNameUpper + " = Column.withName(\"" + columnName + "\")";
            // id is primary key
            if (field.getName().equals("id")) {
                temp += ".primaryKey()";
            }
            // append column mapper
            if (columnMapperMap.containsKey(fieldName)) {
                temp += columnMapperMap.get(fieldName);
            }
            temp += ".build();";
            buffer.append(temp);
            columnUpperNameList.add(columnNameUpper);
        });
        StringJoiner joiner = new StringJoiner(", ");
        columnUpperNameList.forEach(joiner::add);
        buffer.append(lineSeparator);
        buffer.append(lineSeparator + "\t@Override" + lineSeparator +
                "    public List<Column> getColumns() {" + lineSeparator +
                "        return List.of(" + joiner + ");" + lineSeparator +
                "    }");

        buffer.append(lineSeparator + "}");
        return buffer.toString();
    }

    /**
     * 写入文件
     *
     * @param content     TableImpl内容
     * @param entityClass 实体
     * @throws Exception 异常
     */
    static void writing(String content, Class<?> entityClass) throws Exception {
        String className = entityClass.getSimpleName();
        String tableClassName = "T" + className;
        File file = new File(OUTPUT_PATH + "/" + tableClassName + ".java");

        if (file.exists()) {
            System.out.println(file.getPath() + ", already exists, will be rewritten.");
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            System.out.println(file.getPath() + ", content write successfully.");
        }
    }
}

package cn.devicelinks.framework.jdbc.core.definition;

import cn.devicelinks.framework.common.utils.ArrayUtils;
import cn.devicelinks.framework.common.utils.ObjectClassUtils;
import cn.devicelinks.framework.common.utils.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实体结构定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class EntityStructure {
    @Getter
    private final Table table;
    @Getter
    private final Class<?> entityClass;

    private final List<Field> fieldList;

    private final Map<String, Field> fieldMap;

    private final List<Method> methodList;

    private final Map<String, Method> methodMap;

    private final Map<Column, Field> columnFieldMapping;

    private final Map<Field, Column> fieldColumnMapping;

    public EntityStructure(Table table, Class<?> entityClass) {
        Assert.notNull(table, "table must not be null");
        Assert.notNull(entityClass, "entityClass must not be null");
        this.table = table;
        this.entityClass = entityClass;
        this.fieldList = List.of(ObjectClassUtils.getClassFields(entityClass));
        this.fieldMap = fieldList.stream().collect(Collectors.toMap(Field::getName, v -> v));
        this.methodList = List.of(ArrayUtils.concat(ObjectClassUtils.getClassGetMethod(entityClass), ObjectClassUtils.getClassSetMethod(entityClass)));
        this.methodMap = methodList.stream().collect(Collectors.toMap(Method::getName, v -> v));
        this.columnFieldMapping = new LinkedHashMap<>();
        this.fieldColumnMapping = new LinkedHashMap<>();
        for (Field field : fieldList) {
            String columnName = StringUtils.lowerCamelToLowerUnder(field.getName());
            Column column = table.getColumn(columnName);
            if (column != null) {
                columnFieldMapping.put(column, field);
                fieldColumnMapping.put(field, column);
            } else {
                log.error("在表中[{}]找不到列名[{}]，无法建立与字段[{}]的映射关系。", table.getTableName(), columnName, field.getName());
            }
        }
    }

    public String getPrimaryKeyFieldName() {
        Column primaryKey = this.getPrimaryKey();
        Field primaryKeyField = this.getFieldByColumnName(primaryKey.getName());
        return primaryKeyField.getName();
    }

    public Field getField(String fieldName) {
        Assert.hasText(fieldName, "fieldName must not be null or empty");
        return this.fieldMap.get(fieldName);
    }

    public List<Field> getFields() {
        return this.fieldList;
    }

    public Object getFieldValue(String fieldName, Object targetObject) {
        Assert.hasText(fieldName, "fieldName must not be null or empty");
        Assert.notNull(targetObject, "targetObject must not be null");
        Field field = getField(fieldName);
        if (field == null) {
            throw new IllegalArgumentException("在目标类[" + targetObject.getClass().getName() + "中找不到字段["
                    + fieldName + "]，无法获取目标字段值.");
        }
        field.setAccessible(true);
        return ReflectionUtils.getField(field, targetObject);
    }

    public Map<String, Object> getFieldValue(Object targetObject) {
        Map<String, Object> fieldValueMap = new LinkedHashMap<>();
        this.fieldList.forEach(field -> fieldValueMap.put(field.getName(), getFieldValue(field.getName(), targetObject)));
        return fieldValueMap;
    }

    public void setFieldValue(String fieldName, Object targetObject, Object value) {
        Assert.hasText(fieldName, "fieldName must not be null or empty");
        Assert.notNull(targetObject, "targetObject must not be null");
        Field field = getField(fieldName);
        if (field == null) {
            throw new IllegalArgumentException("在目标类[" + targetObject.getClass().getName() + "中找不到字段["
                    + fieldName + "]，无法设置目标字段值.");
        }
        field.setAccessible(true);
        ReflectionUtils.setField(field, targetObject, value);
    }

    public void setFieldValue(Object targetObject, Map<String, Object> fieldValueMap) {
        Assert.notNull(targetObject, "targetObject must not be null");
        Assert.notEmpty(fieldValueMap, "fieldValueMap must not be null or empty");
        this.fieldList.forEach(field -> setFieldValue(field.getName(), targetObject, fieldValueMap.get(field.getName())));
    }

    public Column getColumnByFieldName(String fieldName) {
        Assert.hasText(fieldName, "fieldName must not be null or empty");
        return this.fieldColumnMapping.get(this.getField(fieldName));
    }

    public Field getFieldByColumnName(String columnName) {
        Assert.hasText(columnName, "columnName must not be null or empty");
        return this.columnFieldMapping.get(this.getColumn(columnName));
    }

    public String getFieldNameByColumn(Column column) {
        Field field = this.getFieldByColumnName(column.getName());
        if (field == null) {
            throw new IllegalArgumentException("在表中[" + table.getTableName() + "]找不到列名[" + column.getName() + "]，无法查询与字段的映射关系。");
        }
        return field.getName();
    }

    public Method getMethod(String methodName) {
        Assert.hasText(methodName, "methodName must not be null or empty");
        return this.methodMap.get(methodName);
    }

    public List<Method> getMethods() {
        return this.methodList;
    }

    public Object getMethodResult(String methodName, Object targetObject) {
        Assert.hasText(methodName, "methodName must not be null or empty");
        Assert.notNull(targetObject, "targetObject must not be null");
        Method method = getMethod(methodName);
        if (method == null) {
            throw new IllegalArgumentException("在目标类[" + targetObject.getClass().getName() + "中找不到方法["
                    + methodName + "]，无法获取目标方法返回值.");
        }
        return ReflectionUtils.invokeMethod(method, targetObject);
    }

    public void callMethod(String methodName, Object targetObject, Object... parameters) {
        Assert.hasText(methodName, "methodName must not be null or empty");
        Assert.notNull(targetObject, "targetObject must not be null");
        Method method = getMethod(methodName);
        if (method == null) {
            throw new IllegalArgumentException("在目标类[" + targetObject.getClass().getName() + "中找不到方法["
                    + methodName + "]参数[" + Arrays.toString(parameters) + "]，无法执行目标方法.");
        }
        ReflectionUtils.invokeMethod(method, targetObject, parameters);
    }

    public Column getColumn(String columnName) {
        Assert.hasText(columnName, "columnName must not be null or empty");
        return table.getColumn(columnName);
    }

    public List<Column> getColumns() {
        return table.getColumns();
    }

    public Column getPrimaryKey() {
        return table.getPk();
    }

    public List<Column> getInsertableColumns() {
        return table.getInsertableColumns();
    }

    public List<Column> getUpdatableColumns() {
        return table.getUpdatableColumns();
    }
}

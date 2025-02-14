package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.web.*;
import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldModule;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.common.web.SearchFieldVariable;

import java.util.List;

/**
 * 属性检索字段模板定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class AttributeSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_STRING = SearchFieldOptionData.of().setLabel("字符串").setValue(AttributeDataType.STRING);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_INTEGER = SearchFieldOptionData.of().setLabel("整形").setValue(AttributeDataType.INT32);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_LONG = SearchFieldOptionData.of().setLabel("长整型").setValue(AttributeDataType.LONG64);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_FLOAT = SearchFieldOptionData.of().setLabel("单精度").setValue(AttributeDataType.FLOAT);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_DOUBLE = SearchFieldOptionData.of().setLabel("双精度").setValue(AttributeDataType.DOUBLE);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_BOOLEAN = SearchFieldOptionData.of().setLabel("布尔").setValue(AttributeDataType.BOOLEAN);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_ENUM = SearchFieldOptionData.of().setLabel("枚举").setValue(AttributeDataType.ENUM);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_DATE = SearchFieldOptionData.of().setLabel("日期").setValue(AttributeDataType.DATE);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_DATETIME = SearchFieldOptionData.of().setLabel("日期时间").setValue(AttributeDataType.DATETIME);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_TIME = SearchFieldOptionData.of().setLabel("时间").setValue(AttributeDataType.TIME);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_TIMESTAMP = SearchFieldOptionData.of().setLabel("时间戳").setValue(AttributeDataType.TIMESTAMP);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_OBJECT = SearchFieldOptionData.of().setLabel("对象").setValue(AttributeDataType.OBJECT);
    SearchFieldOptionData ATTRIBUTE_DATA_TYPE_ARRAY = SearchFieldOptionData.of().setLabel("数组").setValue(AttributeDataType.ARRAY);

    SearchField ATTRIBUTE_PRODUCT_ID = SearchField.of(SearchFieldVariable.PRODUCT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setRequired(true)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_MODULE_ID = SearchField.of(SearchFieldVariable.FUNCTION_MODULE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setRequired(true)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_PARENT_ID = SearchField.of(SearchFieldVariable.ATTRIBUTE_PARENT_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField ATTRIBUTE_IDENTIFIER = SearchField.of(SearchFieldVariable.ATTRIBUTE_IDENTIFIER)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField ATTRIBUTE_DATA_TYPE = SearchField.of(SearchFieldVariable.ATTRIBUTE_DATA_TYPE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    ATTRIBUTE_DATA_TYPE_STRING,
                    ATTRIBUTE_DATA_TYPE_INTEGER,
                    ATTRIBUTE_DATA_TYPE_LONG,
                    ATTRIBUTE_DATA_TYPE_FLOAT,
                    ATTRIBUTE_DATA_TYPE_DOUBLE,
                    ATTRIBUTE_DATA_TYPE_BOOLEAN,
                    ATTRIBUTE_DATA_TYPE_DATE,
                    ATTRIBUTE_DATA_TYPE_TIME,
                    ATTRIBUTE_DATA_TYPE_DATETIME,
                    ATTRIBUTE_DATA_TYPE_TIMESTAMP,
                    ATTRIBUTE_DATA_TYPE_ENUM,
                    ATTRIBUTE_DATA_TYPE_ARRAY,
                    ATTRIBUTE_DATA_TYPE_OBJECT
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField ATTRIBUTE_NAME = SearchField.of(SearchFieldVariable.ATTRIBUTE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Attribute;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                ATTRIBUTE_PRODUCT_ID,
                ATTRIBUTE_MODULE_ID,
                ATTRIBUTE_PARENT_ID,
                ATTRIBUTE_IDENTIFIER,
                ATTRIBUTE_DATA_TYPE,
                ATTRIBUTE_NAME
        );
    }
}

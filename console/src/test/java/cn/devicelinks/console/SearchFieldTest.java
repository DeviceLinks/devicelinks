/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.console;

import cn.devicelinks.framework.common.utils.JacksonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 检索字段单元测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SearchFieldTest {

    public static void main(String[] args) {
        List<SearchFieldTemplate> searchFieldTemplateList = new ArrayList<>();

        // ID
        searchFieldTemplateList.add(new SearchFieldTemplate()
                .setField("id")
                .setFieldText("用户ID")
                .setValueType(SearchFieldValueType.STRING)
                .setComponentType(SearchFieldComponentType.INPUT)
                .setOperators(List.of(
                        SearchFieldOperator.EQUAL,
                        SearchFieldOperator.NOT_EQUAL,
                        SearchFieldOperator.IN,
                        SearchFieldOperator.NOT_IN,
                        SearchFieldOperator.LIKE,
                        SearchFieldOperator.NOT_LIKE
                )));
        // Name
        searchFieldTemplateList.add(new SearchFieldTemplate()
                .setField("name")
                .setFieldText("用户名称")
                .setValueType(SearchFieldValueType.STRING)
                .setComponentType(SearchFieldComponentType.INPUT)
                .setOperators(List.of(
                        SearchFieldOperator.EQUAL,
                        SearchFieldOperator.NOT_EQUAL,
                        SearchFieldOperator.IN,
                        SearchFieldOperator.NOT_IN,
                        SearchFieldOperator.LIKE,
                        SearchFieldOperator.NOT_LIKE
                )));
        // Age
        searchFieldTemplateList.add(new SearchFieldTemplate()
                .setField("age")
                .setFieldText("用户年龄")
                .setValueType(SearchFieldValueType.INTEGER)
                .setComponentType(SearchFieldComponentType.INPUT)
                .setOperators(List.of(
                        SearchFieldOperator.EQUAL,
                        SearchFieldOperator.NOT_EQUAL,
                        SearchFieldOperator.GREATER_THAN,
                        SearchFieldOperator.GREATER_THAN_OR_EQUAL,
                        SearchFieldOperator.LESS_THAN,
                        SearchFieldOperator.LESS_THAN_OR_EQUAL,
                        SearchFieldOperator.IN,
                        SearchFieldOperator.NOT_IN
                )));
        // Sex
        searchFieldTemplateList.add(new SearchFieldTemplate()
                .setField("sex")
                .setFieldText("用户性别")
                .setValueType(SearchFieldValueType.STRING)
                .setComponentType(SearchFieldComponentType.SELECT)
                .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
                .setOptionStaticData(List.of(
                        new SearchFieldOptionData().setLabel("男").setValue("man"),
                        new SearchFieldOptionData().setLabel("女").setValue("woman")
                ))
                .setOperators(List.of(
                        SearchFieldOperator.EQUAL,
                        SearchFieldOperator.NOT_EQUAL,
                        SearchFieldOperator.IN,
                        SearchFieldOperator.NOT_IN
                )));

        System.out.println(JacksonUtils.objectToJson(searchFieldTemplateList));
    }

    @Data
    @Accessors(chain = true)
    static class SearchFieldOptionData {
        private String label;
        private String value;
    }

    /**
     * 检索字段模板
     */
    @Data
    @Accessors(chain = true)
    static class SearchFieldTemplate {
        private String field;
        private String fieldText;
        private SearchFieldValueType valueType;
        private SearchFieldComponentType componentType;
        private SearchFieldOptionDataSource optionDataSource;
        private String optionApiDataCode;
        private List<Object> optionStaticData;
        private List<SearchFieldOperator> operators;
    }

    enum SearchFieldValueType {
        /**
         * 字符串
         */
        STRING,
        /**
         * 整数
         */
        INTEGER,
        /**
         * 浮点数
         */
        FLOAT,
        /**
         * 布尔
         */
        BOOLEAN,
        /**
         * 日期
         */
        DATE,
        /**
         * 日期时间
         */
        DATE_TIME
    }

    enum SearchFieldOptionDataSource {
        /**
         * 接口查询
         */
        API,
        /**
         * 静态数据
         */
        STATIC
    }

    enum SearchFieldComponentType {
        /**
         * 输入框
         */
        INPUT,
        /**
         * 下拉框
         */
        SELECT
    }

    enum SearchFieldOperator {
        /**
         * 等于
         */
        EQUAL,
        /**
         * 不等于
         */
        NOT_EQUAL,
        /**
         * 大于
         */
        GREATER_THAN,
        /**
         * 大于等于
         */
        GREATER_THAN_OR_EQUAL,
        /**
         * 小于
         */
        LESS_THAN,
        /**
         * 小于等于
         */
        LESS_THAN_OR_EQUAL,
        /**
         * 包含
         */
        IN,
        /**
         * 不包含
         */
        NOT_IN,
        /**
         * 模糊匹配
         */
        LIKE,
        /**
         * 不模糊匹配
         */
        NOT_LIKE
    }
}

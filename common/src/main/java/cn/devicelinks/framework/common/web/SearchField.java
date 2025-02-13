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

package cn.devicelinks.framework.common.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 检索字段模板
 * <p>
 * 一个模版定义一个检索字段
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class SearchField {
    private String field;
    private String fieldText;
    private SearchFieldValueType valueType;
    private SearchFieldComponentType componentType;
    private SearchFieldOptionDataSource optionDataSource;
    private String optionApiDataCode;
    private List<SearchFieldOptionData> optionStaticData;
    private List<SearchFieldOperator> operators;

    public static SearchField of() {
        return new SearchField();
    }

    public static SearchField of(SearchFieldVariable variable) {
        return new SearchField().setField(variable.getField()).setFieldText(variable.getText());
    }
}

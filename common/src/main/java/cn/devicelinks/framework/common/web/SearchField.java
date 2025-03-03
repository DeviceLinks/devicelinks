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
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.framework.common.Constants.ENUM_OBJECT_LABEL_FIELD;

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
    private SearchFieldOptionDataSource optionDataSource = SearchFieldOptionDataSource.STATIC;
    private String optionApiDataCode;
    private List<SearchFieldOptionData> optionStaticData;
    private Class<? extends Enum> enumClass;
    private List<SearchFieldOperator> operators;
    private boolean required;

    public static SearchField of() {
        return new SearchField();
    }

    public static SearchField of(SearchFieldVariable variable) {
        return new SearchField().setField(variable.getField()).setFieldText(variable.getText());
    }

    public List<SearchFieldOptionData> getOptionStaticData() {
        if (SearchFieldValueType.ENUM == this.valueType && this.enumClass != null) {
            List<SearchFieldOptionData> optionData = new ArrayList<>();
            for (Enum<?> enumConstant : this.enumClass.getEnumConstants()) {
                try {
                    Field field = enumConstant.getClass().getDeclaredField(ENUM_OBJECT_LABEL_FIELD);
                    field.setAccessible(true);
                    String description = (String) field.get(enumConstant);

                    optionData.add(SearchFieldOptionData.of()
                            .setValue(enumConstant.name())
                            .setLabel(!ObjectUtils.isEmpty(description) ? description : enumConstant.toString()));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    optionData.add(SearchFieldOptionData.of()
                            .setValue(enumConstant.name())
                            .setLabel(enumConstant.toString()));
                }
            }
            return optionData;
        }
        return optionStaticData;
    }
}

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
import cn.devicelinks.framework.common.web.SearchFieldModule;
import cn.devicelinks.framework.common.web.SearchFieldTemplate;
import cn.devicelinks.framework.common.web.SearchFieldTemplates;

import java.util.List;

/**
 * 检索字段单元测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SearchFieldTest {

    public static void main(String[] args) {
        List<SearchFieldTemplate> searchFieldTemplateList = SearchFieldTemplates.MODULE_SEARCH_FIELD_TEMPLATE_MAP.get(SearchFieldModule.Log);
        System.out.println(JacksonUtils.objectToJson(searchFieldTemplateList));
    }
}

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

import cn.devicelinks.console.annotation.RunTest;
import cn.devicelinks.api.support.search.SearchFieldModuleFactory;
import cn.devicelinks.framework.common.utils.JacksonUtils;
import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 检索字段单元测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RunTest
public class SearchFieldTest {

    @Test
    public void testGetSearchFields() {
        List<SearchField> searchFieldList = SearchFieldModuleFactory.getSearchFields(SearchFieldModuleIdentifier.Product);
        System.out.println(JacksonUtils.objectToJson(searchFieldList));
    }
}

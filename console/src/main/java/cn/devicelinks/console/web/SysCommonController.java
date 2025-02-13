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

package cn.devicelinks.console.web;

import cn.devicelinks.console.model.GetSearchFieldQuery;
import cn.devicelinks.console.model.search.module.SearchFieldModuleFactory;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公共控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/common")
public class SysCommonController {
    /**
     * 获取功能模块的检索字段
     *
     * @param query 检索字段查询参数 {@link GetSearchFieldQuery}
     * @return {@link SearchField}
     */
    @GetMapping(value = "/search/field")
    public ApiResponse getSearchField(@Valid GetSearchFieldQuery query) throws ApiException {
        SearchFieldModuleIdentifier identifier = SearchFieldModuleIdentifier.valueOf(query.getModule());
        List<SearchField> searchFieldTemplateList = SearchFieldModuleFactory.getSearchFields(identifier);
        return ApiResponse.success(searchFieldTemplateList);
    }
}

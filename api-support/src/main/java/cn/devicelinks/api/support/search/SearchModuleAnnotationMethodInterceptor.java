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

package cn.devicelinks.api.support.search;

import cn.devicelinks.api.support.query.SearchFieldQuery;
import cn.devicelinks.framework.common.api.StatusCode;
import cn.devicelinks.framework.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * {@link SearchModule}注解方法拦截器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class SearchModuleAnnotationMethodInterceptor implements MethodInterceptor {
    private static final StatusCode SEARCH_MODULE_NOT_MATCH = StatusCode.build("SEARCH_MODULE_NOT_MATCH", "[检索字段功能模块]与请求接口不匹配");

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        try {
            SearchModule searchModule = AnnotationUtils.getAnnotation(invocation.getMethod(), SearchModule.class);
            SearchFieldQuery searchFieldQuery = this.getSearchFieldQueryArgument(invocation.getArguments());
            if (searchModule != null && searchFieldQuery != null) {
                if (searchModule.module() != null && !ObjectUtils.isEmpty(searchFieldQuery.getSearchFieldModule())
                        && !searchModule.module().toString().equals(searchFieldQuery.getSearchFieldModule())) {
                    throw new ApiException(SEARCH_MODULE_NOT_MATCH);
                }
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw e;
            } else {
                log.error("Calibration interface fails to match @SearchModule.", e);
            }
        }
        return invocation.proceed();
    }

    private SearchFieldQuery getSearchFieldQueryArgument(Object[] arguments) {
        Optional<Object> object = Arrays.stream(arguments)
                .filter(argument -> SearchFieldQuery.class.isAssignableFrom(argument.getClass()))
                .findFirst();
        return (SearchFieldQuery) object.orElse(null);
    }
}

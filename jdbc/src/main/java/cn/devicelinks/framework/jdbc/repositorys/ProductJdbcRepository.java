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

package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.Condition;
import cn.devicelinks.framework.jdbc.core.sql.FusionCondition;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.core.sql.SortCondition;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TProduct.PRODUCT;

/**
 * The {@link Product} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ProductJdbcRepository extends JdbcRepository<Product, String> implements ProductRepository {
    public ProductJdbcRepository(JdbcOperations jdbcOperations) {
        super(PRODUCT, jdbcOperations);
    }

    @Override
    public PageResult<Product> getProductsByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition) {
        // @formatter:off
        Condition[] conditions = searchFieldConditionToConditionArray(searchFieldConditionList);
        FusionCondition fusionCondition = FusionCondition
                .withSort(sortCondition)
                .conditions(conditions)
                .conditions(PRODUCT.DELETED.eq(Boolean.FALSE))
                .build();
        // @formatter:on
        return this.page(fusionCondition, pageQuery);
    }
}

/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.jdbc.core.definition;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库中单张表结构定义
 * <p>
 * 定义一张表的表名、数据列列表、主键，可以根据配置生成单表的查询、插入、更新、删除等基本SQL
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface Table extends Serializable {
    Column getPk();

    boolean containsColumn(String columnName);

    Column getColumn(String columnName);

    List<Column> getColumns();

    List<Column> getInsertableColumns();

    List<Column> getUpdatableColumns();

    String getBaseQuerySql();

    String getQuerySql();

    String getInsertSql();

    String getUpdateSql();

    String getUpdateSql(List<Column> columnList);

    String getDeleteSql();
}

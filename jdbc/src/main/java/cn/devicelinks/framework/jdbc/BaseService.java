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

package cn.devicelinks.framework.jdbc;

import java.io.Serializable;
import java.util.List;

/**
 * 基础业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface BaseService<T extends Serializable, PK> {
    /**
     * 新增一条数据
     *
     * @param object 等待新增的对象实例
     * @return 受影响的行数
     */
    int insert(T object);

    /**
     * 根据主键删除一条数据
     *
     * @param pk 数据主键的值
     * @return 受影响的行数
     */
    int deleteById(PK pk);

    /**
     * 更新一条数据
     *
     * @param object 等待更新的对象实例
     * @return 受影响的行数
     */
    int update(T object);

    /**
     * 根据主键查询一条数据
     *
     * @param pk 数据主键的值
     * @return 数据对象实例
     */
    T selectById(PK pk);

    /**
     * 查询全部的数据
     *
     * @return 数据对象实例
     */
    List<T> selectAll();
}

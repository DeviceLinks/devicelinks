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

package cn.devicelinks.jdbc;

import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.definition.Table;
import cn.devicelinks.jdbc.core.sql.Condition;

import java.io.Serializable;
import java.util.List;

/**
 * 基础业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class BaseServiceImpl<T extends Serializable, PK, R extends Repository<T, PK>> implements BaseService<T, PK> {
    protected R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    /**
     * 新增一条数据
     *
     * @param object 等待新增的数据对象
     * @return 影响行数
     */
    @Override
    public int insert(T object) {
        return repository.insert(object);
    }

    /**
     * 根据主键删除数据
     * <p>
     * 根据{@link Table#getPk()}主键列创建过滤条件{@link Condition}
     * 注意：表必须配置主键
     *
     * @param pk 主键的值
     * @return 删除所影响的数据行数
     */
    @Override
    public int deleteById(PK pk) {
        return repository.delete(pk);
    }

    /**
     * 根据主键更新单个对象数据
     *
     * @param object 等待更新的对象
     * @return 更新数据的行数
     */
    @Override
    public int update(T object) {
        return repository.update(object);
    }

    /**
     * 根据主键ID查询单条数据
     *
     * @param pk 主键的值
     * @return 返回的泛型类型对象实例
     */
    @Override
    public T selectById(PK pk) {
        return repository.selectOne(pk);
    }

    /**
     * 查询全部数据
     *
     * @return 查询结果对象列表
     */
    @Override
    public List<T> selectAll() {
        return repository.select();
    }
}

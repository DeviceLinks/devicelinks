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

package cn.devicelinks.framework.jdbc.core;

import cn.devicelinks.framework.jdbc.core.definition.Table;
import cn.devicelinks.framework.jdbc.core.page.PageQuery;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.*;

import java.io.Serializable;
import java.util.List;

/**
 * 数据存储库接口定义
 * <p>
 * 定义基础的操作JDBC数据存储的通用接口，具体业务数据存储库实现该接口即可拥有通用数据操作方法
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface Repository<T extends Serializable, PK> {
    /**
     * 新增一条数据
     *
     * @param object 等待新增的数据对象
     * @return 影响行数
     */
    int insert(T object);

    /**
     * 根据主键删除数据
     * <p>
     * 根据{@link Table#getPk()}主键列创建过滤条件{@link Condition}
     * 注意：表必须配置主键
     *
     * @param pk 主键的值
     * @return 删除所影响的数据行数
     */
    int delete(PK pk);

    /**
     * 根据条件{@link Condition}删除数据
     *
     * @param conditions 删除数据的过滤条件 {@link Condition}
     * @return 删除所影响的行数
     */
    int delete(Condition... conditions);

    /**
     * 根据条件分组进行删除数据
     *
     * @param conditionGroups 删除数据的过滤条件分组 {@link ConditionGroup}
     * @return 删除所影响的行数
     */
    int delete(ConditionGroup... conditionGroups);

    /**
     * 根据自定义过滤SQL删除数据
     *
     * @param filterSql             自定义过滤数据的SQL
     * @param filterConditionValues 对应SQL查询列的值 {@link ConditionValue}
     * @return 删除所影响的行数
     */
    int delete(String filterSql, ConditionValue... filterConditionValues);

    /**
     * 根据主键更新单个对象数据
     *
     * @param object 等待更新的对象
     * @return 更新数据的行数
     */
    int update(T object);

    /**
     * 根据条件更新指定列的值
     *
     * @param setConditionValueList set列与值关系实体列表
     * @param conditions            过滤更新数据的条件数组
     * @return 更新数据的行数
     */
    int update(List<ConditionValue> setConditionValueList, Condition... conditions);

    /**
     * 根据条件分组更新指定列的值
     *
     * @param setConditionValueList {@link ConditionValue} set列与值关系实体列表
     * @param conditionGroups       {@link ConditionGroup} 查询条件分组列表
     * @return 更新数据的行数
     */
    int update(List<ConditionValue> setConditionValueList, ConditionGroup... conditionGroups);

    /**
     * 根据自定义过滤条件SQL更新数据
     *
     * @param setConditionValueList {@link ConditionValue} set列与值关系实体列表
     * @param filterSql             自定义过滤SQL
     * @param filterConditionValues {@link ConditionValue} 过滤SQL列值关系实体列表
     * @return 更新数据的行数
     */
    int update(List<ConditionValue> setConditionValueList, String filterSql, ConditionValue... filterConditionValues);

    /**
     * 根据主键ID查询单条数据
     * <p>
     * 返回实体的类型使用父类的第一个泛型定义，也就是继承该类实现类所维护的数据实体
     *
     * @param pk 主键的值
     * @return 返回的泛型类型对象实例
     */
    T selectOne(PK pk);

    /**
     * 根据条件查询单条数据
     *
     * @param conditions 查询条件列表 {@link Condition}
     * @return 返回的泛型类型对象实例
     */
    T selectOne(Condition... conditions);

    /**
     * 根据条件分组查询单条数据
     *
     * @param conditionGroups 查询条件分组列表 {@link ConditionGroup}
     * @return 返回的泛型类型对象实例
     */
    T selectOne(ConditionGroup... conditionGroups);

    /**
     * 查询全部数据
     *
     * @return 查询结果对象列表
     */
    List<T> select();

    /**
     * 根据条件查询多条数据
     *
     * @param conditions 查询条件列表 {@link Condition}
     * @return 查询结果对象列表
     */
    List<T> select(Condition... conditions);

    /**
     * 根据排序条件、查询条件查询多条数据
     *
     * @param sort       排序条件 {@link SortCondition}
     * @param conditions 查询条件列表 {@link Condition}
     * @return 查询结果对象列表
     */
    List<T> select(SortCondition sort, Condition... conditions);

    /**
     * 根据条件分组查询多条数据
     *
     * @param conditionGroups 查询分组列表 {@link ConditionGroup}
     * @return 查询到的对象列表
     */
    List<T> select(ConditionGroup... conditionGroups);

    /**
     * 根据排序条件、查询条件分组查询多条数据
     *
     * @param sort            排序条件 {@link SortCondition}
     * @param conditionGroups 查询条件分组列表 {@link ConditionGroup}
     * @return 查询结果对象列表
     */
    List<T> select(SortCondition sort, ConditionGroup... conditionGroups);

    /**
     * 根据融合的条件查询多条数据
     *
     * @param condition {@link FusionCondition}
     * @return 查询结果对象列表
     */
    List<T> select(FusionCondition condition);

    /**
     * 根据融合的条件分页查询数据
     *
     * @param condition {@link FusionCondition}
     * @param pageQuery {@link PageQuery}
     * @return 分页结果对象实例 {@link PageResult}
     */
    PageResult<T> page(FusionCondition condition, PageQuery pageQuery);

    /**
     * 根据动态自定义和查询条件分页查询数据
     *
     * @param dynamic         自定义动态查询条件对象实例 {@link Dynamic}
     * @param pageQuery       {@link PageQuery}
     * @param parameterValues 对应自定义查询SQL的参数值，与参数索引一一对应
     * @param <R>             响应结果实例类型
     * @return 分页结果对象实例 {@link PageResult}
     */
    <R extends Serializable> PageResult<R> page(Dynamic dynamic, PageQuery pageQuery, Object... parameterValues);

    /**
     * 自定义查询
     *
     * @param dynamic         自定义查询对象实例 {@link Dynamic}
     * @param parameterValues 对应自定义查询SQL的参数值，与参数索引一一对应
     * @param <R>             响应结果实例类型
     * @return 响应结果实例
     */
    <R> List<R> dynamicSelect(Dynamic dynamic, Object... parameterValues);

    /**
     * 自定义修改数据，如：更新、删除
     *
     * @param dynamic         自定义查询对象实例 {@link Dynamic}
     * @param parameterValues 对应自定义修改SQL的参数值，与参数索引一一对应
     */
    int dynamicModify(Dynamic dynamic, Object... parameterValues);
}

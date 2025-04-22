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

package cn.devicelinks.common.context;


/**
 * 上下文持有者策略
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface ContextHolderStrategy<T extends Context> {
    /**
     * 获取数据权限上下文
     *
     * @return 上下文对象实例
     */
    T getContext();

    /**
     * 设置数据权限上下文
     *
     * @param context 上下文对象实例
     */
    void setContext(T context);

    /**
     * 清空数据权限上下文
     */
    void clearContext();
}

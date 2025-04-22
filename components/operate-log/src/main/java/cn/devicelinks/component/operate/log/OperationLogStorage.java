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

package cn.devicelinks.component.operate.log;

/**
 * 操作日志存储接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface OperationLogStorage {
    /**
     * 存储操作日志
     * <p>
     * 将操作日志对象实例{@link OperationLogObject}作为参数传递，由具体的实现类进行数据存在
     *
     * @param object 操作日志对象实例
     */
    void storage(OperationLogObject object);
}

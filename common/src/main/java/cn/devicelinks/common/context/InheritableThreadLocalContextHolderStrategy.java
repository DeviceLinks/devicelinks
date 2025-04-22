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
 * The {@link ContextHolderStrategy} InheritableThreadLocal Support
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class InheritableThreadLocalContextHolderStrategy<T extends Context> implements ContextHolderStrategy<T> {
    private final ThreadLocal<T> contextHolder = new InheritableThreadLocal<>();

    @Override
    public T getContext() {
        return contextHolder.get();
    }

    @Override
    public void setContext(T context) {
        contextHolder.set(context);
    }

    @Override
    public void clearContext() {
        contextHolder.remove();
    }
}

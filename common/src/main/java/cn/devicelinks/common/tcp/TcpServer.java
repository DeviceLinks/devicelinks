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

package cn.devicelinks.common.tcp;

/**
 * TCP模式运行的服务器接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface TcpServer {
    /**
     * 启动服务
     */
    void startup();

    /**
     * 停止服务
     */
    void shutdown();

    /**
     * 服务是否在运行中
     *
     * @return 服务运行中时返回"true"
     */
    boolean isRunning();

}

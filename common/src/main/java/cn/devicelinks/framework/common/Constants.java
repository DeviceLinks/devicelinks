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

package cn.devicelinks.framework.common;

import java.io.File;

/**
 * 常量定义
 *
 * @author 恒宇少年
 */
public interface Constants {
    int ZERO = 0;
    int ONE = 1;
    String EMPTY_STRING = "";
    String SPACE = " ";
    /**
     * 多个值之间的分隔符
     */
    String SEPARATOR = ",";
    /**
     * 服务名称根据IP以及Port进行格式化，如：127.0.0.1::8000
     */
    String SERVER_NAME_FORMAT_BY_IP_AND_PORT = "%s::%d";
    /**
     * 运行服务的用户根目录
     */
    String USER_DIR = System.getProperty("user.home");
    /**
     * DeviceLinks配置文件目录地址
     */
    String ON_CONNECTS_CONFIG_DIR_PATH = USER_DIR + File.separator + "devicelinks";
    /**
     * DS（Director Server）服务时所需要的配置目录地址
     */
    String VIRTUAL_SERVER_CONFIG_DIR_PATH = ON_CONNECTS_CONFIG_DIR_PATH + File.separator + "virtual-server";
    /**
     * 空字节
     */
    byte EMPTY_BYTE = 0b00000000;
    /**
     * 值类型为枚举时用于获取枚举中定义的对应"label"的字段
     */
    String ENUM_OBJECT_LABEL_FIELD = "description";
}

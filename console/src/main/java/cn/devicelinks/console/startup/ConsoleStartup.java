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

package cn.devicelinks.console.startup;

import cn.devicelinks.console.startup.listener.InitializationAdminPasswordListener;
import cn.devicelinks.common.startup.ServerStartupEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 控制台服务启动后置运行器
 *
 * @author 恒宇少年
 * @see InitializationAdminPasswordListener
 * @since 1.0
 */
@Component
public class ConsoleStartup implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    public ConsoleStartup(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        ServerStartupEvent startupEvent = new ServerStartupEvent(this);
        this.applicationContext.publishEvent(startupEvent);
    }
}

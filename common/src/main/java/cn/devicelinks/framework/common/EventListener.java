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

package cn.devicelinks.framework.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 事件监听器抽象类定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class EventListener<E extends ApplicationEvent> implements ApplicationListener<E> {
    /**
     * logger instance
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean allow(E event) {
        return true;
    }

    public abstract void processing(E event);

    @Override
    public void onApplicationEvent(E event) {
        try {
            if (allow(event)) {
                this.processing(event);
            }
        } catch (Exception e) {
            logger.error("执行事件[" + event.getClass().getSimpleName() + "]监听器逻辑遇到异常.", e);
        }
    }
}

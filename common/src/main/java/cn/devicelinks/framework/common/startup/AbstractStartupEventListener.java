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

package cn.devicelinks.framework.common.startup;

import cn.devicelinks.framework.common.ListenerOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * The {@link ServerStartupEvent} Abstract Listener
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public abstract class AbstractStartupEventListener implements SmartApplicationListener {

    public abstract void invokeListening(ServerStartupEvent event);

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ServerStartupEvent.class == eventType;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            ServerStartupEvent serverStartupEvent = (ServerStartupEvent) event;
            this.invokeListening(serverStartupEvent);
        } catch (Exception e) {
            this.hasException(e);
        }
    }

    public void hasException(Exception e) {
        log.error(e.getMessage(), e);
    }

    public ListenerOrder getListenerOrder() {
        return ListenerOrder.ONE_PRECEDENCE;
    }

    @Override
    public int getOrder() {
        ListenerOrder listenerOrder = this.getListenerOrder();
        return listenerOrder == null ? ListenerOrder.ONE_ORDER : listenerOrder.getOrder();
    }
}

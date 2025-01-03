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

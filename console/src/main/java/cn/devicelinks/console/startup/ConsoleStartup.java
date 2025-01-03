package cn.devicelinks.console.startup;

import cn.devicelinks.console.startup.listener.InitializationAdminPasswordListener;
import cn.devicelinks.framework.common.startup.ServerStartupEvent;
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

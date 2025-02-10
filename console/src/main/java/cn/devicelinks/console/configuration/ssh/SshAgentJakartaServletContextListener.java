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

package cn.devicelinks.console.configuration.ssh;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.ssh.agent.AgentConnection;
import org.minbox.framework.ssh.agent.apache.ApacheMinaSshdAgentConnection;
import org.minbox.framework.ssh.agent.config.AgentConfig;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Jakarta Servlet实现Ssh Agent连接代理Servlet Context监听器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
@WebListener
public class SshAgentJakartaServletContextListener implements ServletContextListener {
    /**
     * The ssh-agent auto config properties
     */
    private final SshAgentJakartaProperties sshAgentJakartaProperties;
    /**
     * Cache a list of AgentConnection objects
     */
    private final List<AgentConnection> connections = new ArrayList<>();

    public SshAgentJakartaServletContextListener(SshAgentJakartaProperties sshAgentJakartaProperties) {
        this.sshAgentJakartaProperties = sshAgentJakartaProperties;
    }

    /**
     * {@link ServletContext} initialized method
     * <p>
     * Create an {@link AgentConnection} instance according to each {@link AgentConfig} and perform port forwarding connection
     *
     * @param sce The {@link ServletContextEvent} instance
     * @see ApacheMinaSshdAgentConnection
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<AgentConfig> configs = sshAgentJakartaProperties.getConfigs();
        if (ObjectUtils.isEmpty(configs)) {
            log.warn("ssh-agent agent does not take effect, reason: agent information is not configured.");
            return;
        }
        configs.forEach(config -> {
            try {
                AgentConnection connection = new ApacheMinaSshdAgentConnection(config);
                this.connections.add(connection);
                connection.connect();
            } catch (Exception e) {
                log.error("Connection：{}:{}，try agent failure.", config.getServerIp(), config.getForwardTargetPort(), e);
            }
        });
    }

    /**
     * {@link ServletContext} destroy method
     * <p>
     * Disconnect all connections in the AgentConnection cache list
     *
     * @param sce The {@link ServletContextEvent} instance
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connections.forEach(AgentConnection::disconnect);
    }
}

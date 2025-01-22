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

import lombok.Data;
import org.minbox.framework.ssh.agent.config.AgentConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Ssh agent config properties
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SshAgentJakartaProperties.SSH_AGENT_PREFIX)
public class SshAgentJakartaProperties {
    /**
     * The config prefix of ssh-agent
     */
    public static final String SSH_AGENT_PREFIX = "devicelinks.ssh-agent";
    /**
     * The config collection of {@link AgentConfig}
     * <p>
     * Use this parameter to configure proxy multiple remote server port forwarding information
     */
    @NestedConfigurationProperty
    private List<AgentConfig> configs;
}

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

package cn.devicelinks.framework.common.tcp;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.*;
import io.netty.channel.kqueue.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * IO Model
 *
 * @author 恒宇少年
 * @since 1.0
 */
public enum IOModel {
    /**
     * EPoll
     * <p>
     * Recommended for Linux deployment environment
     */
    EPOLL(EpollEventLoopGroup::new, EpollServerSocketChannel.class, EpollSocketChannel.class, EpollDatagramChannel.class, Epoll.isAvailable()),
    /**
     * KQueue
     * <p>
     * Recommended for mac develop environment
     */
    KQUEUE(KQueueEventLoopGroup::new, KQueueServerSocketChannel.class, KQueueSocketChannel.class, KQueueDatagramChannel.class, KQueue.isAvailable()),
    /**
     * Non Blocking IO
     */
    NIO(NioEventLoopGroup::new, NioServerSocketChannel.class, NioSocketChannel.class, NioDatagramChannel.class, true);

    // @formatter:off
    private static final IOModel autoValue = Stream.of(EPOLL, KQUEUE, NIO)
            .filter(IOModel::isAvailable)
            .findFirst()
            .orElse(null);
    // @formatter:on

    private final Function<Integer, EventLoopGroup> eventLoopGroupCreator;
    @Getter
    private final Class<? extends ServerChannel> tcpServerChannelClass;
    @Getter
    private final Class<? extends SocketChannel> tcpChannelClass;
    @Getter
    private final Class<? extends DatagramChannel> udpChannelClass;
    private final boolean isAvailable;

    // @formatter:off
    IOModel(Function<Integer, EventLoopGroup> eventLoopGroupCreator,
            Class<? extends ServerChannel> tcpServerChannelClass,
            Class<? extends SocketChannel> tcpChannelClass,
            Class<? extends DatagramChannel> udpChannelClass,
            boolean isAvailable) {
        this.eventLoopGroupCreator = eventLoopGroupCreator;
        this.tcpServerChannelClass = tcpServerChannelClass;
        this.tcpChannelClass = tcpChannelClass;
        this.udpChannelClass = udpChannelClass;
        this.isAvailable = isAvailable;
    }
    // @formatter:on

    public EventLoopGroup eventLoopGroup(int threadCount) {
        return this.eventLoopGroupCreator.apply(threadCount);
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public static IOModel auto() {
        return IOModel.autoValue;
    }

}

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.Ordered;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * 监听顺序定义
 *
 * @author 恒宇少年
 */
@Getter
@AllArgsConstructor
public class ListenerOrder {
    public static final int ONE_ORDER = 1;
    public static final int HIGHEST_ORDER = -100000;
    public static final int LOWEST_ORDER = 100000;
    public static final ListenerOrder HIGHEST_PRECEDENCE = new ListenerOrder(HIGHEST_ORDER);
    public static final ListenerOrder ONE_PRECEDENCE = new ListenerOrder(ONE_ORDER);
    public static final ListenerOrder LOWEST_PRECEDENCE = new ListenerOrder(LOWEST_ORDER);
    /**
     * 监听顺序的值
     * 值在 {@link Ordered#LOWEST_PRECEDENCE} ~ {@link Ordered#HIGHEST_PRECEDENCE}之间
     */
    private int order;

    /**
     * 实例化一个{@link ListenerOrder}
     *
     * @param order 排序字段
     * @return {@link ListenerOrder}
     */
    public static ListenerOrder valueOf(int order) {
        return new ListenerOrder(order);
    }

    /**
     * 在当前的监听顺序上 "+" 参数值
     *
     * @param additionOrder 添加的优先级order值
     * @return 返回添加后的排序值
     */
    public int after(int additionOrder) {
        return excludeThreshold(this.order + additionOrder);
    }

    /**
     * 在当前的监听顺序上 + 1
     *
     * @return 返回添加后的排序值
     */
    public int after() {
        return this.after(ONE_ORDER);
    }

    /**
     * 在当前监听顺序上 "-" 参数值
     *
     * @param subtractionOrder 添加的优先级order值
     * @return 返回减少后的排序值
     */
    public int before(int subtractionOrder) {
        return excludeThreshold(this.order - subtractionOrder);
    }

    /**
     * 在当前的监听顺序上 - 1
     *
     * @return 返回减少后的排序值
     */
    public int before() {
        return this.before(ONE_ORDER);
    }

    /**
     * 在一系列{@link ListenerOrder}之后执行
     * <p>
     * 根据方法返回值倒序排序后，将结果 "+1" 后返回
     *
     * @param listenerOrders 监听顺序列表
     * @return 执行顺序的值
     */
    public static int afters(ListenerOrder[] listenerOrders) {
        Optional<ListenerOrder> maxListenerOrder =
                Arrays.stream(listenerOrders)
                        .sorted(Comparator.comparing(ListenerOrder::getOrder).reversed())
                        .findFirst();
        return maxListenerOrder.get().after();
    }

    /**
     * 在一系列{@link ListenerOrder} 之前执行
     * <p>
     * 根据方法返回值排序后，将结果 "-1" 后返回
     *
     * @param listenerOrders 监听顺序列表
     * @return 执行顺序的值
     */
    public static int befores(ListenerOrder[] listenerOrders) {
        Optional<ListenerOrder> maxListenerOrder =
                Arrays.stream(listenerOrders)
                        .sorted(Comparator.comparing(ListenerOrder::getOrder))
                        .findFirst();
        return maxListenerOrder.get().before();
    }

    /**
     * 排除最大最小临界值
     *
     * @param order before/after计算后的顺序值
     * @return 处理临界值后的顺序值
     */
    private static int excludeThreshold(int order) {
        if (order >= LOWEST_ORDER) {
            return LOWEST_ORDER;
        } else if (order <= HIGHEST_ORDER) {
            return HIGHEST_ORDER;
        }
        return order;
    }
}

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

package cn.devicelinks.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.function.Function;

/**
 * 枚举工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class EnumUtils {
    /**
     * 获取指定包中所有枚举类
     *
     * @param packageName    要扫描的包名
     * @param filterFunction 过滤器函数，该函数参数为枚举类型，返回true时表示该枚举类需要被添加到结果中
     * @return 枚举类集合
     */
    public static Set<Class<? extends Enum>> getEnumsInPackage(String packageName, Function<Class, Boolean> filterFunction) {
        Set<Class<? extends Enum>> enumClasses = new HashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Enum.class));

        for (var beanDefinition : scanner.findCandidateComponents(packageName)) {
            try {
                Class clazz = ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
                if (clazz.isEnum() && filterFunction.apply(clazz)) {
                    enumClasses.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return enumClasses;
    }

    /**
     * 获取枚举值列表
     *
     * @param enumClass 枚举类类型
     * @param <T>       枚举值类型
     * @return 枚举值列表
     */
    public static <T extends Enum<T>> List<T> getEnumValues(Class<T> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    /**
     * 获取指定包中所有枚举类及其值列表
     *
     * @param packageName 要扫描的包名
     * @return 不同枚举类及其值列表
     */
    public static Map<Class<? extends Enum>, List> getAllEnums(String packageName) {
        Map<Class<? extends Enum>, List> enumsMap = new HashMap<>();
        Set<Class<? extends Enum>> enumClasses = getEnumsInPackage(packageName, e -> true);

        for (Class<? extends Enum> enumClass : enumClasses) {
            enumsMap.put(enumClass, getEnumValues(enumClass));
        }
        return enumsMap;
    }
}

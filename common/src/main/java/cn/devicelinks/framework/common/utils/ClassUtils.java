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

package cn.devicelinks.framework.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 类工具
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class ClassUtils {
    public final static String RESOURCE_PATTERN = "/**/*.class";

    /**
     * 获取指定包下支持指定注解的Class列表
     *
     * @param basePackage            要扫描的包路径
     * @param supportAnnotationClass 支持的注解类型
     * @param <T>                    Class的类型
     * @param <A>                    注解的类型
     * @return 扫描到的每一个Class以及注解实例集合
     */
    public static <T, A extends Annotation> Map<Class<? extends T>, A> getAllSupportAnnotationClasses(String basePackage, Class<? extends A> supportAnnotationClass, boolean ignoreAbstract) {
        Map<Class<? extends T>, A> classAnnotationMap = new HashMap<>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                A annotation = clazz.getAnnotation(supportAnnotationClass);
                if (ignoreAbstract && !Modifier.isAbstract(clazz.getModifiers()) && annotation != null) {
                    classAnnotationMap.put((Class<? extends T>) clazz, annotation);
                } else if (!ignoreAbstract && annotation != null) {
                    classAnnotationMap.put((Class<? extends T>) clazz, annotation);
                }
            }
        } catch (Exception e) {
            log.error("获取标注指定注解Class集合失败.", e);
        }
        return classAnnotationMap;
    }
}

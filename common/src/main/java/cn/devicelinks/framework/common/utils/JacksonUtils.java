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

package cn.devicelinks.framework.common.utils;

import cn.devicelinks.framework.common.Constants;
import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JacksonUtils {
    private final static DeviceLinksJsonMapper objectMapper = new DeviceLinksJsonMapper();

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object 要序列化的对象
     * @return JSON字符串
     */
    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Constants.EMPTY_STRING;
        }
    }

    /**
     * 将JSON字符串反序列化为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz 对象类型
     * @param <T>   泛型
     * @return 反序列化后的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将JSON字符串反序列化为List对象
     *
     * @param json  JSON字符串
     * @param clazz List中元素的类型
     * @param <T>   泛型
     * @return 反序列化后的List
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将JSON字符串反序列化为List对象
     *
     * @param json   JSON字符串
     * @param kClazz Map Key的类型
     * @param vClazz Map Value的类型
     * @param <K>    Key泛型
     * @param <V>    Value泛型
     * @return 反序列化后的Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (ObjectUtils.isEmpty(json) || kClazz == null || vClazz == null) return Collections.emptyMap();
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(Map.class, kClazz, vClazz));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyMap();
        }
    }
}

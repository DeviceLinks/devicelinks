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

import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
public class JacksonUtils {
    private final static DeviceLinksJsonMapper objectMapper = new DeviceLinksJsonMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String toJsonString(Object obj) {
        if (obj == null) return null;
        ObjectMapper mapper = getInstance();
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (ObjectUtils.isEmpty(jsonStr) || clazz == null) return null;
        ObjectMapper mapper = getInstance();
        T t = null;
        try {
            t = mapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> parseList(String listJsonStr, Class<T> clazz) {
        if (ObjectUtils.isEmpty(listJsonStr) || clazz == null) return Collections.emptyList();
        ObjectMapper mapper = getInstance();
        List<T> list = Collections.emptyList();
        try {
            list = mapper.readValue(listJsonStr, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <K, V> Map<K, V> parseMap(String mapJsonStr, Class<K> kClazz, Class<V> vClazz) {
        if (ObjectUtils.isEmpty(mapJsonStr) || kClazz == null || vClazz == null) return Collections.emptyMap();
        ObjectMapper mapper = getInstance();
        Map<K, V> map = Collections.EMPTY_MAP;
        try {
            map = mapper.readValue(mapJsonStr, mapper.getTypeFactory().constructParametricType(Map.class, kClazz, vClazz));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

}

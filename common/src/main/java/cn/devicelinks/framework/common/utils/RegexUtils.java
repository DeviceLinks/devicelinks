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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.ConcurrentReferenceHashMap.ReferenceType.SOFT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexUtils {

    public static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

    private static final ConcurrentMap<String, Pattern> patternsCache = new ConcurrentReferenceHashMap<>(16, SOFT);

    public static String replace(String s, Pattern pattern, UnaryOperator<String> replacer) {
        return pattern.matcher(s).replaceAll(matchResult -> {
            return replacer.apply(matchResult.group());
        });
    }

    public static String replace(String input, String pattern, Function<MatchResult, String> replacer) {
        return patternsCache.computeIfAbsent(pattern, Pattern::compile).matcher(input).replaceAll(replacer);
    }

    public static boolean matches(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }

    public static String getMatch(String input, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            try {
                return matcher.group(group);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

}

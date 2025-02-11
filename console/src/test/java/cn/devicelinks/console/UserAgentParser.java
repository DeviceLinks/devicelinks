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

package cn.devicelinks.console;

/**
 * @author 恒宇少年
 * @since 1.0
 */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserAgentParser {

    public String getBrowser(String userAgent) {
        Map<String, String> browsers = new LinkedHashMap<>();
        browsers.put("Chrome", "Chrome");
        browsers.put("Firefox", "Firefox");
        browsers.put("Safari", "Safari");
        browsers.put("Edge", "Edg");
        browsers.put("Opera", "OPR");
        browsers.put("Internet Explorer", "MSIE|Trident");

        for (Map.Entry<String, String> entry : browsers.entrySet()) {
            if (Pattern.compile(entry.getValue()).matcher(userAgent).find()) {
                return entry.getKey();
            }
        }
        return "Unknown Browser";
    }

    public String getOS(String userAgent) {
        Map<String, String> osList = new LinkedHashMap<>();
        osList.put("Windows", "Windows NT");
        osList.put("macOS", "Macintosh");
        osList.put("Linux", "Linux");
        osList.put("Android", "Android");
        osList.put("iOS", "iPhone|iPad");

        for (Map.Entry<String, String> entry : osList.entrySet()) {
            if (Pattern.compile(entry.getValue()).matcher(userAgent).find()) {
                return entry.getKey();
            }
        }
        return "Unknown OS";
    }

    public Map<String, String> parseUserAgent(String userAgent) {
        Map<String, String> result = new HashMap<>();
        result.put("browser", getBrowser(userAgent));
        result.put("os", getOS(userAgent));
        return result;
    }

    // Example usage
    public static void main(String[] args) {
        UserAgentParser parser = new UserAgentParser();
        Map<String, String> result = parser.parseUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        //Map<String, String> result = parser.parseUserAgent("Mozilla/5.0 (iPhone; CPu iPhone 0s 16 6 like Mac 0s X)AppleWebKit/605.1.15 (KHTML, like Gecko)Version/16.6 Mobile/15E148 Safari/604.1");
        //Map<String, String> result = parser.parseUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");
        System.out.println(result);  // Output: {browser=Chrome, os=Windows}
    }
}


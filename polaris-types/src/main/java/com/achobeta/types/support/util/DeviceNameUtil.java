package com.achobeta.types.support.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 严豪哲
 * @Description: 从userAgent中获取设备名称工具类
 * @Date: 2024/11/14 23:30
 * @Version: 1.0
 */
public class DeviceNameUtil {

    public static String getDeviceName(String userAgent) {

        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }

        // 匹配浏览器名称的正则
        Pattern pattern = Pattern.compile("(?i)(?:Mozilla|Chrome|Safari|Firefox|MSIE|Trident|Edge|Opera)[/\\s]+([\\d.]+)");
        Matcher matcher = pattern.matcher(userAgent);

        if (matcher.find()) {
            // 提取浏览器名称
            String browser = matcher.group(1);
            if (userAgent.contains("Edg/")) {
                return "Edge";
            } else if (userAgent.contains("OPR/")) {
                return "Opera";
            } else if (userAgent.contains("Chrome/") && !userAgent.contains("Edg/")) {
                return "Chrome";
            } else if (userAgent.contains("Safari/") && !userAgent.contains("Chrome/") && !userAgent.contains("Edg/")) {
                return "Safari";
            } else if (userAgent.contains("Firefox/")) {
                return "Firefox";
            } else if (userAgent.contains("MSIE ") || userAgent.contains("Trident/")) {
                return "Internet Explorer";
            } else {
                return browser;
            }
        }

        return "Unknown";
    }

}

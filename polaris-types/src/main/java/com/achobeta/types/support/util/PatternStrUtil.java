package com.achobeta.types.support.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
public class PatternStrUtil {

    public static final String REPLACE_VAR_REGEX = "(AchoBeta\\s+\\d+\\.\\d+|AchoBeta\\s+Polaris)";

    public static String replaceText(String text, String prefix, String suffix) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        Pattern compile = Pattern.compile(REPLACE_VAR_REGEX);
        Matcher matcher = compile.matcher(text);

        while (matcher.find()) {
            String result = matcher.group(1);
            text = text.replace(result, prefix + result.trim() + suffix);
        }

        return text;
    }

}

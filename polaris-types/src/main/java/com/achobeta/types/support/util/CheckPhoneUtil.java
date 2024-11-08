package com.achobeta.types.support.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 严豪哲
 * @Description: 手机号验证工具类
 * @Date: 2024/11/6 0:19
 * @Version: 1.0
 */
public class CheckPhoneUtil {

    // 手机号正则
    private static final String PHONE_NUMBER_REGEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public static boolean checkPhone(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}

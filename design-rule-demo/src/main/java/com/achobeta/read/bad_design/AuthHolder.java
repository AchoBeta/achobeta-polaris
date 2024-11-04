package com.achobeta.read.bad_design;

import com.achobeta.read.UserInfo;

/**
 * @author chensongmin
 * @description
 * @create 2024/11/3
 */
public class AuthHolder {

    public static boolean authGroup(UserInfo userInfo) {
        return userInfo.isAuth();
    }

    public static boolean isInWhiteList(UserInfo userInfo) {
        return "【Java萌新】半糖同学".equals(userInfo.getName());
    }

}

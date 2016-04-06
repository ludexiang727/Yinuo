package com.yinuo.utils;

/**
 * Created by ludexiang on 2016/4/6.
 */
public class StringUtils {

    public static boolean isEmpty(String content) {
        if (content == null || "".equals(content) || content.length() == 0) {
            return true;
        }
        return false;
    }
}

package com.yinuo.utils;

/**
 * Created by ludexiang on 2016/4/6.
 */
public class StringUtils {

    public static boolean isEmpty(String content) {
        if (content == null || "".equals(content) || content.length() == 0
                || "null".equalsIgnoreCase(content) || "{}".equalsIgnoreCase(content)) {
            return true;
        }
        return false;
    }

    public static String[] split(String banners, String split) {
        if (StringUtils.isEmpty(banners)) {
            return new String[0];
        }

        return banners.split(split);
    }
}

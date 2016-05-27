package com.yinuo.net.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class NetParseUtils {

    /** default return "" */
    public static String getString(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optString(key);
        }
        return "";
    }

    /** default return false */
    public static boolean getBoolean(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optBoolean(key);
        }
        return false;
    }

    /** default return -1 */
    public static int getInt(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optInt(key);
        }
        return -1;
    }

    public static double getDouble(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optDouble(key);
        }
        return 0.00;
    }

    /** default return null */
    public static JSONArray getArray(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optJSONArray(key);
        }
        return null;
    }

    public static JSONObject getObject(String key, JSONObject obj) {
        if (obj.has(key)) {
            return obj.optJSONObject(key);
        }
        return null;
    }
}

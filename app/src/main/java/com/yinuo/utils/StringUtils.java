package com.yinuo.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.yinuo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ludexiang on 2016/4/6.
 */
public class StringUtils {

    public static final int INVEST_WECHAT_TIME = 5 * 60 * 1000;

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

    /** string sub base on { } and change color begin */
    public static SpannableString richString(String text) {
        List<Corporation> list = new ArrayList<Corporation>();
        SpannableString span = new SpannableString(text);
        if (text.contains("{") && text.contains("}")) {
            Pattern pattern = Pattern.compile("\\{.+?\\}");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                Corporation corporation = new Corporation();
                corporation.x = matcher.start();
                corporation.y = matcher.end();
                list.add(corporation);
            }
//            字符串截取
            for (int i = 0; i < list.size(); i++) {
                StringBuffer buffer = new StringBuffer();
                int start = list.get(i).x;
                int end = list.get(i).y;
                buffer.append(text.substring(0, start - (i * 2)));
                buffer.append(text.substring(start + 1 - (i * 2), end - 1 - (i * 2)));
                buffer.append(text.substring(end - (i * 2), text.length()));
                text = buffer.toString();
            }
//          - (i * 2) 是因为去掉了{}两字符
            span = new SpannableString(text);
            for (int i = 0; i < list.size(); i++) {
                ForegroundColorSpan spannable = new ForegroundColorSpan(Color.parseColor("#ff4081"));
                span.setSpan(spannable, list.get(i).x - (i * 2), list.get(i).y - (i * 2) - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            Pattern pattern = Pattern.compile("\\d+分钟");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                ForegroundColorSpan spannable = new ForegroundColorSpan(Color.parseColor("#ff4081"));
                span.setSpan(spannable, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return span;
    }
    private static class Corporation {
        private int x;
        private int y;
    }

    /** string sub base on { } and change color end */

    public static String formatTime(String time, String pattern) {
        try {
            Date date = stringToDate(time, null);
            return dateToString(date, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** date类型转换为String类型
     *  formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *  data Date类型的时间
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**long类型转换为String类型
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     */
    public static String longToString(long currentTime, String formatType) {
        Date date = null; // long类型转成Date类型
        try {
            date = longToDate(currentTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * strTime的时间格式必须要与formatType的时间格式相同
     */
    private static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        if (formatType == null) {
            formatType = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = formatter.parse(strTime);
        return date;
    }

    /***
     * long转换为Date类型
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * string类型转换为long类型
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     */
    public static long stringToLong(String strTime, String formatType) {
        try {
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }
        } catch (ParseException e) {

        }
        return 0;
    }

    /** date类型转换为long类型
     * date要转换的date类型的时间
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    private static String getWeekData(int index) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, index);
        int dw = c.get(Calendar.DAY_OF_WEEK);
        if (dw == 1) {
            return "星期日";
        } else if (dw == 2) {
            return "星期一";
        } else if (dw == 3) {
            return "星期二";
        } else if (dw == 4) {
            return "星期三";
        } else if (dw == 5) {
            return "星期四";
        } else if (dw == 6) {
            return "星期五";
        } else if (dw == 7) {
            return "星期六";
        }
        return "";
    }

    public static String investWechatTime(long lastShowTime, long currentTime) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        long current = System.currentTimeMillis();

        Date lastShowDate = new Date(lastShowTime);
        calendar.setTime(lastShowDate);
        int lastShowYear = calendar.get(Calendar.YEAR);
        int lastShowMonth = calendar.get(Calendar.MONTH) + 1;
        int lastShowDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (year == lastShowYear) {
            if (currentTime - lastShowTime >= INVEST_WECHAT_TIME) {
                Date date = new Date(currentTime);
                int dateYear = date.getYear();
                int dateMonth = date.getMonth();
                int dateDay = date.getDay();
            } else {
                return longToString(lastShowTime, "MM-dd HH:mm");
            }
        } else {
            return longToString(lastShowTime, "yyyy-MM-dd HH:mm");
        }
        return "";
    }

    // 获得汉语拼音首字母
    public static String getAlpha(Context context, String str) {
        String[] letter = context.getResources().getStringArray(R.array.city_choose_page_letter);
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return letter[0];
        } else if (str.equals("1")) {
            return letter[1];
        } else if (str.equals("2")) {
            return letter[2];
        } else if (str.equals("3")) {
            return letter[3];
        } else {
            return "#";
        }
    }
}

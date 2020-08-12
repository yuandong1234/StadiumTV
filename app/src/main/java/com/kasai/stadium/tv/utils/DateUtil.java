package com.kasai.stadium.tv.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 时间格式转换
     *
     * @param oldFormat
     * @param newFormat
     * @param time
     * @return
     */
    public static String changeFormatToString(String oldFormat, String newFormat, String time) {
        if (TextUtils.isEmpty(time)) {
            return time;
        }
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, Locale.ENGLISH);
            Date date = oldDateFormat.parse(time);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static int[] getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new int[]{hour, minute, second};
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(new Date());
    }
}

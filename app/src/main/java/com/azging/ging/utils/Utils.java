package com.azging.ging.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 数据库存储    文件存储   Sp      内容提供者
 *
 *
 * false 表示不是第一次运行
 * true 表示第一运行
 */
public class Utils {
    public static boolean isFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        boolean run = sp.getBoolean("run", true);
        if (run) {
            sp.edit().putBoolean("run", false).commit();
        }
        return run;
    }

//    public static String toDateStringFromIso(String sdate) {
//        if ("null".equals(sdate) || "NULL".equals(sdate) || "".equals(sdate) || sdate == null) {
//            return "";
//        }
//        Date d1 = null;
//        try {
//            d1 = dateFormaterIsodate.get().parse(sdate);
//            sharecalendar.setTime(d1);
//            sharecalendar.set(Calendar.HOUR_OF_DAY,sharecalendar.get(Calendar.HOUR_OF_DAY)+8);
//            d1=sharecalendar.getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//        return dateFormater4.get().format(d1);
//    }
}

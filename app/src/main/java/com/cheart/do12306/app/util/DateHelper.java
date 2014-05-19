package com.cheart.do12306.app.util;

import android.util.Log;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.view.ShowQueryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cheart on 2014/5/19.
 */


public class DateHelper {
    public static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static Calendar setNewCalendar(int year, int month, int day) {
        Calendar c = Calendar.getInstance();

        switch (month) {
            case Calendar.JANUARY:
                c.set(year, Calendar.JANUARY, day);
                break;
            case Calendar.FEBRUARY:
                c.set(year, Calendar.FEBRUARY, day);
                break;
            case Calendar.APRIL:
                c.set(year, Calendar.APRIL, day);
                break;
            case Calendar.MARCH:
                c.set(year, Calendar.MARCH, day);
                break;
            case Calendar.MAY:
                c.set(year, Calendar.MAY, day);
                break;
            case Calendar.JUNE:
                c.set(year, Calendar.JUNE, day);
                break;
            case Calendar.JULY:
                c.set(year, Calendar.JULY, day);
                break;
            case Calendar.AUGUST:
                c.set(year, Calendar.AUGUST, day);
                break;
            case Calendar.SEPTEMBER:
                c.set(year, Calendar.SEPTEMBER, day);
                break;
            case Calendar.OCTOBER:
                c.set(year, Calendar.OCTOBER, day);
                break;
            case Calendar.NOVEMBER:
                c.set(year, Calendar.NOVEMBER, day);
                break;
            case Calendar.DECEMBER:
                c.set(year, Calendar.DECEMBER, day);
                break;

        }

        return c;
    }

    public static boolean isDateBprder(String str){
        System.out.println(str);
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date nowDate = null;
        Date minDate = null;
        Date maxDate = null;
        try {
            nowDate = sdf.parse(parserDate(str));
            minDate = sdf.parse(parserDate(ShowQueryResult.DATE_CAN_BUY_ARRAY[0]));
            maxDate = sdf.parse(parserDate(ShowQueryResult.DATE_CAN_BUY_ARRAY[ShowQueryResult.
                    DATE_CAN_BUY_ARRAY.length - 1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(nowDate.toString());
        System.out.println(minDate.toString());
        System.out.println(maxDate.toString());

        if (nowDate.before(minDate) || nowDate.after(maxDate)){
            result = true;
        } else {
            result = false;
        }


        return result;
    }

    public static String nextDate(String str) {
        String result = "";
        String[] arr = str.split("-");
        StringBuffer sb = new StringBuffer();
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        Calendar calendar = Calendar.getInstance();
        calendar = DateHelper.setNewCalendar(year, month, day);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayCountOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day >= dayCountOfMonth) {
            month++;
            day = 1;
            calendar = setNewCalendar(year, month, day);
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;


        } else {
            day++;
            calendar = setNewCalendar(year, month - 1, day);
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }
        sb.append(year + "年" + (month < 10 ? "0" + month : month) + "月" + (day < 10 ? "0" + day : day) + "日" + "(" +
                weekDays[week] + ")" + ",");
        result = sb.toString();
        return result;
    }



    public static String preDate(String str) {
        String result = "";
        String[] arr = str.split("-");
        StringBuffer sb = new StringBuffer();
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        Calendar calendar = Calendar.getInstance();
        calendar = DateHelper.setNewCalendar(year, month, day);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayMinOfMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int dayMaxOdMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day <= dayMinOfMonth) {
            month--;
            day = dayMaxOdMonth;
            calendar = setNewCalendar(year, month, day);
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;


        } else {
            day--;
            calendar = setNewCalendar(year, month - 1, day);
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }
        sb.append(year + "年" + (month < 10 ? "0" + month : month) + "月" + (day < 10 ? "0" + day : day) + "日" + "(" +
                weekDays[week] + ")" + ",");
        result = sb.toString();
        return result;
    }

    public static String parserDate(String str) {
        String result = "";
        String[] arr1 = str.split("年");
        StringBuffer sb = new StringBuffer();
        sb.append(arr1[0] + "-");
        String[] arr2 = arr1[1].split("月");
        sb.append(arr2[0] + "-");
        String[] arr3 = arr2[1].split("日");
        sb.append(arr3[0]);
        result = sb.toString();
        return result;
    }
}

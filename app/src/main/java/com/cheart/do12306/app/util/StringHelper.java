package com.cheart.do12306.app.util;

/**
 * Created by cheart on 4/25/2014.
 */


public class StringHelper {

    public static boolean isEmptyString(String str) {
        if (null == str || str.equals("") || str.length() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static String formAtSecretStr(String str) {
        if (null == str || str.equals("") || str.length() == 0) {
            throw new NullPointerException("secret String is null");
        } else {
            return str.replaceAll("%2B", "+");
        }

    }

}


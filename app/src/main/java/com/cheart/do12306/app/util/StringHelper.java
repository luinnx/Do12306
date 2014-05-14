package com.cheart.do12306.app.util;

import android.util.Log;

import java.util.Iterator;
import java.util.Map;

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
            return str.replaceAll("%2B", "+").replaceAll("%2F", "/").replaceAll("%3D", "=");
        }

    }

    public static String parserGetUrl(String URL,Map<String, String> params){
        String result = "";
        StringBuffer sb = new StringBuffer();

        for (Iterator<String> it = params.keySet().iterator(); it.hasNext();){
            String key = it.next();
            sb.append(key + "=" + params.get(key) + "&");
        }
        sb.deleteCharAt(sb.length() - 1);
        result = sb.toString();
        result = URL + "?" + result;
        System.out.println(result);
        return result;


    }


}


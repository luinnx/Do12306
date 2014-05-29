package com.cheart.do12306.app.client;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.cheart.do12306.app.domain.Passenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 5/28/2014.
 */
public class CommunalData {



    public static boolean isLogin = false;
    public static Map<String, String> STATION_MAP;
    public static String[] STATION_ARRAY;
    public static String CAN_BUY_DATE = "";
    public static String CAN_BUY_DATE_SIMPLE = "";
    public static List<Passenger> PASSENGER_LIST;
    public static Map<String, Integer> PASSENGER_MAP;



    static{

        STATION_MAP = new HashMap<String, String>();
        STATION_ARRAY = new String[]{};
    }


    public static List<Passenger> getPASSENGER_LIST() {
        return PASSENGER_LIST;
    }

    public static void setPASSENGER_LIST(List<Passenger> PASSENGER_LIST) {
        CommunalData.PASSENGER_LIST = PASSENGER_LIST;
    }

    public static Map<String, Integer> getPASSENGER_MAP() {
        return PASSENGER_MAP;
    }

    public static void setPASSENGER_MAP(Map<String, Integer> PASSENGER_MAP) {
        CommunalData.PASSENGER_MAP = PASSENGER_MAP;
    }

    public static String getCAN_BUY_DATE() {
        return CAN_BUY_DATE;
    }

    public static void setCAN_BUY_DATE(String CAN_BUY_DATE) {
        CommunalData.CAN_BUY_DATE = CAN_BUY_DATE;
    }

    public static String getCAN_BUY_DATE_SIMPLE() {
        return CAN_BUY_DATE_SIMPLE;
    }

    public static void setCAN_BUY_DATE_SIMPLE(String CAN_BUY_DATE_SIMPLE) {
        CommunalData.CAN_BUY_DATE_SIMPLE = CAN_BUY_DATE_SIMPLE;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        CommunalData.isLogin = isLogin;
    }

    public static Map<String, String> getSTATION_MAP() {
        return STATION_MAP;
    }

    public static void setSTATION_MAP(Map<String, String> STATION_MAP) {
        CommunalData.STATION_MAP = STATION_MAP;
    }

    public static String[] getSTATION_ARRAY() {
        return STATION_ARRAY;
    }

    public static void setSTATION_ARRAY(String[] STATION_ARRAY) {
        CommunalData.STATION_ARRAY = STATION_ARRAY;
    }

    public static void loadAllStation(Context context) {

        AssetManager am = context.getAssets();
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer sb_name = null;
        try {
            in = am.open("stations");
            br = new BufferedReader(new InputStreamReader(in));
            sb_name = new StringBuffer();
            String stationsStr = br.readLine();
            String[] stationsArray = stationsStr.split("@");

            for (int i = 0; i < stationsArray.length; i++) {
                if (i != 0 && i != stationsArray.length) {

                    String[] stationArray = stationsArray[i].split("\\|");
                    String chineseName = "";
                    String code = "";
                    for (int j = 0; j < stationArray.length; j++) {
                        if (j == 1) {
                            chineseName = stationArray[1];
                        } else if (j == 2) {
                            code = stationArray[2];
                        }
                    }
                    sb_name.append(chineseName + ",");
                    STATION_MAP.put(chineseName, code);

                } else {

                }
            }

            STATION_ARRAY = sb_name.toString().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

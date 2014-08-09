package com.cheart.do12306.app.client;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.SpannableString;
import android.util.Log;

import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.Passenger;
import com.cheart.do12306.app.view.SelectSeatType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheart on 5/28/2014.
 */
public class CommunalData {


    public static boolean isLogin = false;
    public static Map<String, String> STATION_MAP;
    public static String[] STATION_ARRAY;
    public static String[] DO_TICKET_ALL_TRAIN_CODE;
    public static String[] ALL_SEAT_TYPE = new String[]{
            "二等座",
            "一等座",
            "特等座",
            "硬座",
            "软座",
            "硬卧",
            "软卧",
            "高级软卧",
            "商务座"
    };

    public static String CAN_BUY_DATE = "";
    public static String CAN_BUY_DATE_SIMPLE = "";
    public static List<Passenger> PASSENGER_LIST;
    public static Map<String, Integer> PASSENGER_MAP;
    public static Map<String, String> SEAT_TYPE_MAP;
    public static Map<String, String> SEAT_TYPE_CODE_MAP;
    public static Map<String, String> TICKET_TYPE;

    public static Set<String> SELECTED_TRAIN_CODE_SET;
    public static Set<String> SELECTED_PASSENGER_SET;
    public static Set<String> SELECTED_SEAT_TYPE_SET;

    public static List<BaseData> DO_TICKET_BASEDATA_LIST;

    public static Map<String,Map<String,String>> DO_TICKET_RESULT_QUERY_MAP;


    public static List<Passenger> p = new ArrayList<Passenger>();
    public static List<BaseData> b = new ArrayList<BaseData>();


    public static String DO_TICKET_LOG = "Strat Do It";

    public static String from = "北京";
    public static String to = "长春";
    public static String date = "2014-08-31";

    public static String c1 = "D29";
    public static String c2 = "D73";
    public static String t = "二等座";
    static {

        STATION_MAP = new HashMap<String, String>();
        STATION_ARRAY = new String[]{};
        PASSENGER_LIST = new ArrayList<Passenger>();
        PASSENGER_MAP = new HashMap<String, Integer>();
        SEAT_TYPE_MAP = new HashMap<String, String>();
        SEAT_TYPE_CODE_MAP = new HashMap<String, String>();
        TICKET_TYPE = new HashMap<String, String>();

        SELECTED_PASSENGER_SET = new HashSet<String>();
        SELECTED_SEAT_TYPE_SET = new HashSet<String>();
        SELECTED_TRAIN_CODE_SET = new HashSet<String>();

        DO_TICKET_BASEDATA_LIST = new ArrayList<BaseData>();

        DO_TICKET_RESULT_QUERY_MAP = new HashMap<String, Map<String, String>>();

        SEAT_TYPE_MAP = new HashMap<String, String>();
        SEAT_TYPE_MAP.put("二等座", "O");
        SEAT_TYPE_MAP.put("一等座", "M");
        SEAT_TYPE_MAP.put("特等座", "P");
        SEAT_TYPE_MAP.put("硬座", "1");
        SEAT_TYPE_MAP.put("软座", "2");
        SEAT_TYPE_MAP.put("硬卧", "3");
        SEAT_TYPE_MAP.put("软卧", "4");
        SEAT_TYPE_MAP.put("高级软卧", "6");
        SEAT_TYPE_MAP.put("商务座", "9");

        SEAT_TYPE_CODE_MAP.put("二等座", "O");
        SEAT_TYPE_CODE_MAP.put("一等座", "M");
        SEAT_TYPE_CODE_MAP.put("特等座", "P");
        SEAT_TYPE_CODE_MAP.put("硬座", "A1");
        SEAT_TYPE_CODE_MAP.put("软座", "A2");
        SEAT_TYPE_CODE_MAP.put("硬卧", "A3");
        SEAT_TYPE_CODE_MAP.put("软卧", "A4");
        SEAT_TYPE_CODE_MAP.put("高级软卧", "A6");
        SEAT_TYPE_CODE_MAP.put("商务座", "A9");

        TICKET_TYPE.put("成人", "1");
        TICKET_TYPE.put("孩票", "2");
        TICKET_TYPE.put("学生", "3");
        TICKET_TYPE.put("伤残军人票", "4");
    }


    public static Map<String, String> getSEAT_TYPE_MAP() {
        return SEAT_TYPE_MAP;
    }

    public static void setSEAT_TYPE_MAP(Map<String, String> SEAT_TYPE_MAP) {
        CommunalData.SEAT_TYPE_MAP = SEAT_TYPE_MAP;
    }

    public static Map<String, String> getSEAT_TYPE_CODE_MAP() {
        return SEAT_TYPE_CODE_MAP;
    }

    public static void setSEAT_TYPE_CODE_MAP(Map<String, String> SEAT_TYPE_CODE_MAP) {
        CommunalData.SEAT_TYPE_CODE_MAP = SEAT_TYPE_CODE_MAP;
    }

    public static Map<String, String> getTICKET_TYPE() {
        return TICKET_TYPE;
    }

    public static void setTICKET_TYPE(Map<String, String> TICKET_TYPE) {
        CommunalData.TICKET_TYPE = TICKET_TYPE;
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

    public static String[] parserAllTrainCodeBaseData(List<BaseData> baseDatas){

        StringBuffer sb = new StringBuffer();
        for (BaseData baseData : baseDatas){
            sb.append(baseData.getQueryLeftNewDTO().getStation_train_code() + ",");

        }
        return sb.toString().split(",");

    }


}

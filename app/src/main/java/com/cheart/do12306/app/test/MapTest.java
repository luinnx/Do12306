package com.cheart.do12306.app.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cheart on 5/15/2014.
 */
public class MapTest {

    public static void main(String[] args){

        Map<String, String> m1 = new HashMap<String, String>();
        Map<String, String> m2 = new LinkedHashMap<String, String>();

        m1.put("from_station","heb");
        m2.put("from_station","heb");
        m1.put("to_station", "bj");
        m2.put("to_station", "bj");
        m1.put("date", "15");
        m2.put("date", "15");

        System.out.println(m1.keySet());
        System.out.println(m2.keySet());



    }
}

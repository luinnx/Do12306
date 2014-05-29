package com.cheart.do12306.app.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.DoTicketDateAdapter;
import com.cheart.do12306.app.adapter.DoTicketPassenegrAdapter;
import com.cheart.do12306.app.client.CommunalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoTicket extends ActionBarActivity {


    private static final String TAG = "DoTicket";
    private ListView lv_date;
    private ListView lv_trainCode;
    private ListView lv_passenger;


    public static final String DATE_LIST = "date_list";
    public static final String PASSENGER_LIST = "passenger_list";
    public static final String DATE0 = "date0";
    public static final String DATE1 = "date1";
    public static final String DATE2 = "date2";
    public static final String DATE3 = "date3";
    public static final String PASSENGER0 = "passenger0";
    public static final String PASSENGER1 = "passenger1";
    public static final String PASSENGER2 = "passenger2";
    public static final String PASSENGER3 = "passenger3";
    public static final String[] DATES = new String[]{
            DATE0, DATE1, DATE2, DATE3
    };
    public static final String[] PASSENGERS = new String[]{
            PASSENGER0,PASSENGER1,PASSENGER2,PASSENGER3
    };


    public static List<HashMap<String, String>> CAN_BUY_DATE_LIST;
    public static List<HashMap<String, String>> PASSENGER_SELECT_LIST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (!CommunalData.isIsLogin()){
            Intent intent = new Intent(DoTicket.this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_do_ticket);


        init();
    }


    private void init() {
        CAN_BUY_DATE_LIST = new ArrayList<HashMap<String, String>>();
        PASSENGER_SELECT_LIST = new ArrayList<HashMap<String, String>>();
        initCanBuyDateList();
        initPassengerList();
        initView();
    }


    private void initCanBuyDateList() {
        String[] arr = CommunalData.getCAN_BUY_DATE_SIMPLE().split(",");
        HashMap<String, String> map;
        StringBuffer sb = new StringBuffer();
        int flag = 0;
        Log.v(TAG, "DATE LEMGTH" + arr.length);
        if (arr.length % 4 == 0) {
            for (int i = 0; i < arr.length; i++) {


                sb.append(arr[i].split("-")[1].toString() + "-" + arr[i].split("-")[2].toString() + ",");
                flag++;

                if (flag > 3) {
                    Log.v(TAG, "INDEX" + "-" + i);
                    map = new HashMap<String, String>();
                    map.put(DATE_LIST, sb.toString());
                    CAN_BUY_DATE_LIST.add(map);
                    sb.delete(0, sb.length());
                    flag = 0;
                }

            }
        } else {
            for (int i = 0; i < arr.length; i++) {


                sb.append(arr[i].split("-")[1].toString() + "-" + arr[i].split("-")[2].toString() + ",");
                if (i == arr.length - 1) {
                    for (int j = 0; j < (4 - arr.length % 4); j++) {
                        sb.append("null" + ",");
                    }
                    map = new HashMap<String, String>();
                    map.put(DATE_LIST, sb.toString());
                    CAN_BUY_DATE_LIST.add(map);
                }
                flag++;

                if (flag > 3) {
                    map = new HashMap<String, String>();
                    map.put(DATE_LIST, sb.toString());
                    CAN_BUY_DATE_LIST.add(map);
                    sb.delete(0, sb.length());
                    flag = 0;
                }

            }
        }


        Log.v(TAG, CAN_BUY_DATE_LIST + "");
    }


    private void initPassengerList(){

        String[] arr = CommunalData.getPASSENGER_MAP().keySet().toArray(new String[]{});
        Log.v(TAG, "passenger array" + arr);
        HashMap<String, String> map;
        StringBuffer sb = new StringBuffer();
        int flag = 0;
        Log.v(TAG, "DATE LEMGTH" + arr.length);
        if (arr.length % 4 == 0) {
            for (int i = 0; i < arr.length; i++) {


                sb.append(arr[i] + ",");
                flag++;

                if (flag > 3) {
                    Log.v(TAG, "INDEX" + "-" + i);
                    map = new HashMap<String, String>();
                    map.put(PASSENGER_LIST, sb.toString());
                    PASSENGER_SELECT_LIST.add(map);
                    sb.delete(0, sb.length());
                    flag = 0;
                }

            }
        } else {
            for (int i = 0; i < arr.length; i++) {


                sb.append(arr[i] + ",");
                if (i == arr.length - 1) {
                    for (int j = 0; j < (4 - arr.length % 4); j++) {
                        sb.append("null" + ",");
                    }
                    map = new HashMap<String, String>();
                    map.put(PASSENGER_LIST, sb.toString());
                    PASSENGER_SELECT_LIST.add(map);
                }
                flag++;

                if (flag > 3) {
                    map = new HashMap<String, String>();
                    map.put(PASSENGER_LIST, sb.toString());
                    PASSENGER_SELECT_LIST.add(map);
                    sb.delete(0, sb.length());
                    flag = 0;
                }

            }
        }


        Log.v(TAG, PASSENGER_SELECT_LIST + "");
    }

    private void initView() {
        lv_date = (ListView) findViewById(R.id.lv_do_ticket_date);
        lv_trainCode = (ListView) findViewById(R.id.lv_do_train_code);
        lv_passenger = (ListView) findViewById(R.id.lv_do_ticket_passenger);

        lv_date.setAdapter(new DoTicketDateAdapter(
                DoTicket.this,
                CAN_BUY_DATE_LIST,
                R.layout.activity_do_ticket_date_item,
                new String[]{},
                new int[]{}
        ));

        lv_passenger.setAdapter(new DoTicketPassenegrAdapter(
                DoTicket.this,
                PASSENGER_SELECT_LIST,
                R.layout.activity_do_ticket_passenger_item,
                new String[]{},
                new int[]{}
        ));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.do_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

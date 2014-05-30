package com.cheart.do12306.app.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.domain.BaseAutoQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DoTicket extends ActionBarActivity {


    private static final String TAG = "DoTicket";
    private BaseAutoQuery baq;
    private ListView lv_date;
    private ListView lv_trainCode;
    private ListView lv_passenger;
    private ListView lv_seat_type;
    private Button bt_submit;



    public static final String DATE_LIST = "date_list";
    public static final String SEAT_TYPE_LIST = "teat_type";
    public static final String PASSENGER_LIST = "passenger_list";
    public static final String DATE0 = "date0";
    public static final String DATE1 = "date1";
    public static final String DATE2 = "date2";
    public static final String DATE3 = "date3";
    public static final String SEAT_TYPE0 = "seat_type0";
    public static final String SEAT_TYPE1 = "seat_type1";
    public static final String SEAT_TYPE2 = "seat_type2";
    public static final String SEAT_TYPE3 = "seat_type3";
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
    public static final String[] SEAT_TYPES = new String[]{
            SEAT_TYPE0, SEAT_TYPE1, SEAT_TYPE2, SEAT_TYPE3
    };



    public static List<HashMap<String, String>> CAN_BUY_DATE_LIST;
    public static List<HashMap<String, String>> PASSENGER_SELECT_LIST;
    public static List<HashMap<String, String>> SEAT_TYPE_SELECT_LIST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.v(TAG, "is login" + CommunalData.isIsLogin());
        if (!CommunalData.isIsLogin()){
            Intent intent = new Intent(DoTicket.this, MainActivity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_do_ticket);
            init();

        }


    }


    private void init() {
        CAN_BUY_DATE_LIST = new ArrayList<HashMap<String, String>>();
        PASSENGER_SELECT_LIST = new ArrayList<HashMap<String, String>>();
        SEAT_TYPE_SELECT_LIST = new ArrayList<HashMap<String, String>>();
        baq = new BaseAutoQuery();
        baq.setDate(new HashSet<String>());
        baq.setTrainCode(new HashSet<String>());
        baq.setPassenger(new HashSet<String>());
        baq.setSeatType(new HashSet<String>());


        initCanBuyDateList();
        initPassengerList();
        initSeatTypeList();
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


    private void initSeatTypeList(){

        String[] arr = CommunalData.getSEAT_TYPE_MAP().keySet().toArray(new String[]{});
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
                    map.put(SEAT_TYPE_LIST, sb.toString());
                    SEAT_TYPE_SELECT_LIST.add(map);
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
                    map.put(SEAT_TYPE_LIST, sb.toString());
                    SEAT_TYPE_SELECT_LIST.add(map);
                }
                flag++;


                if (flag > 3) {
                    map = new HashMap<String, String>();
                    map.put(SEAT_TYPE_LIST, sb.toString());
                    SEAT_TYPE_SELECT_LIST.add(map);
                    sb.delete(0, sb.length());
                    flag = 0;
                }

            }
        }


        Log.v(TAG, SEAT_TYPE_SELECT_LIST + "");
    }



    private void initView() {
        lv_date = (ListView) findViewById(R.id.lv_do_ticket_date);
        lv_trainCode = (ListView) findViewById(R.id.lv_do_train_code);
        lv_passenger = (ListView) findViewById(R.id.lv_do_ticket_passenger);
        lv_seat_type = (ListView) findViewById(R.id.lv_do_ticket_seatType);
        bt_submit = (Button) findViewById(R.id.bt_do_ticket_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoTicket.this, TestDoQuery.class);
                intent.putExtra("base_auto_query", baq);
                startActivity(intent);
            }
        });

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

        lv_seat_type.setAdapter(new DoTicketSeatTypeAdapter(
                DoTicket.this,
                SEAT_TYPE_SELECT_LIST,
                R.layout.activity_do_ticket_seat_type_item,
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


    public class DoTicketDateAdapter extends SimpleAdapter {

        private static final String TAG = "DoTicketDateAdapter";
        Context context;
        List<HashMap<String, String>> data;
        private DateItemListener dateClickListener = new DateItemListener();
        public DoTicketDateAdapter(Context context,
                                   List<HashMap<String, String>> data,
                                   int resource,
                                   String[] from,
                                   int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.data = data;

        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ViewHolder {
            TextView tv_date0;
            TextView tv_date1;
            TextView tv_date2;
            TextView tv_date3;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.activity_do_ticket_date_item, null);

            ViewHolder holder = null;
            if(holder == null){
                holder = new ViewHolder();
                holder.tv_date0 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date0);
                holder.tv_date1 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date1);
                holder.tv_date2 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date2);
                holder.tv_date3 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date3);
                holder.tv_date0.setOnClickListener(dateClickListener);
                holder.tv_date1.setOnClickListener(dateClickListener);
                holder.tv_date2.setOnClickListener(dateClickListener);
                holder.tv_date3.setOnClickListener(dateClickListener);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String[] arr = data.get(position).get(DoTicket.DATE_LIST).split(",");
            holder.tv_date0.setText(null != arr[0] && !arr[0].equals("null") ? arr[0] : "");
            holder.tv_date1.setText(null != arr[1] && !arr[1].equals("null") ? arr[1] : "");
            holder.tv_date2.setText(null != arr[2] && !arr[2].equals("null") ? arr[2] : "");
            holder.tv_date3.setText(null != arr[3] && !arr[3].equals("null") ? arr[3] : "");
            convertView.setTag(holder);

            return convertView;
        }

        private class DateItemListener implements View.OnClickListener {



            @Override
            public void onClick(View view) {


                CheckBox c = (CheckBox) view;
                if(c.isChecked()){
                    baq.getDate().add(c.getText().toString());
                } else{
                    baq.getDate().remove(c.getText());
                }

                Log.v(TAG, "item" + c.getText() + " " + "isChecked" + c.isChecked());

                Log.v(TAG, "now date is :" + baq.getDate());

            }
        }


    }


    public class DoTicketPassenegrAdapter extends SimpleAdapter {

        private static final String TAG = "DoTicketPassenegrAdapter";
        Context context;
        List<HashMap<String, String>> data;
        private PassengerItemListener passengerClickLitener = new PassengerItemListener();
        public DoTicketPassenegrAdapter(Context context,
                                        List<HashMap<String, String>> data,
                                        int resource,
                                        String[] from,
                                        int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.data = data;

        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ViewHolder {
            CheckBox ck_passenger0;
            CheckBox ck_passenger1;
            CheckBox ck_passenger2;
            CheckBox ck_passenger3;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.activity_do_ticket_passenger_item, null);

            ViewHolder holder = null;
            if(holder == null){
                holder = new ViewHolder();
                holder.ck_passenger0 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger0);
                holder.ck_passenger1 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger1);
                holder.ck_passenger2 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger2);
                holder.ck_passenger3 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger3);
                holder.ck_passenger0.setOnClickListener(passengerClickLitener);
                holder.ck_passenger1.setOnClickListener(passengerClickLitener);
                holder.ck_passenger2.setOnClickListener(passengerClickLitener);
                holder.ck_passenger3.setOnClickListener(passengerClickLitener);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String[] arr = data.get(position).get(DoTicket.PASSENGER_LIST).split(",");
            holder.ck_passenger0.setText(null != arr[0] && !arr[0].equals("null") ? arr[0] : "");
            holder.ck_passenger1.setText(null != arr[1] && !arr[1].equals("null") ? arr[1] : "");
            holder.ck_passenger2.setText(null != arr[2] && !arr[2].equals("null") ? arr[2] : "");
            holder.ck_passenger3.setText(null != arr[3] && !arr[3].equals("null") ? arr[3] : "");

            convertView.setTag(holder);

            return convertView;
        }

        private class PassengerItemListener implements View.OnClickListener{


            @Override
            public void onClick(View view) {
                CheckBox c = (CheckBox) view;
                if(c.isChecked()){
                    baq.getPassenger().add(c.getText().toString());
                } else{
                    baq.getPassenger().remove(c.getText());
                }

                Log.v(TAG, "item" + c.getText() + " " + "isChecked" + c.isChecked());

                Log.v(TAG, "now passenger is :" + baq.getPassenger());



            }
        }
    }

    public class DoTicketSeatTypeAdapter extends SimpleAdapter {

        private static final String TAG = "DoTicketSeatTypeAdapter";
        Context context;
        List<HashMap<String, String>> data;
        private SeatTypeItemListener passengerClickLitener = new SeatTypeItemListener();
        public DoTicketSeatTypeAdapter(Context context,
                                        List<HashMap<String, String>> data,
                                        int resource,
                                        String[] from,
                                        int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.data = data;

        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ViewHolder {
            CheckBox ck_seat0;
            CheckBox ck_seat1;
            CheckBox ck_seat2;
            CheckBox ck_seat3;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.activity_do_ticket_seat_type_item, null);

            ViewHolder holder = null;
            if(holder == null){
                holder = new ViewHolder();
                holder.ck_seat0 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_seat0);
                holder.ck_seat1 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_seat1);
                holder.ck_seat2 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_seat2);
                holder.ck_seat3 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_seat3);
                holder.ck_seat0.setOnClickListener(passengerClickLitener);
                holder.ck_seat1.setOnClickListener(passengerClickLitener);
                holder.ck_seat2.setOnClickListener(passengerClickLitener);
                holder.ck_seat3.setOnClickListener(passengerClickLitener);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String[] arr = data.get(position).get(DoTicket.SEAT_TYPE_LIST).split(",");
            holder.ck_seat0.setText(null != arr[0] && !arr[0].equals("null") ? arr[0] : "");
            holder.ck_seat1.setText(null != arr[1] && !arr[1].equals("null") ? arr[1] : "");
            holder.ck_seat2.setText(null != arr[2] && !arr[2].equals("null") ? arr[2] : "");
            holder.ck_seat3.setText(null != arr[3] && !arr[3].equals("null") ? arr[3] : "");

            convertView.setTag(holder);

            return convertView;
        }

        private class SeatTypeItemListener implements View.OnClickListener{


            @Override
            public void onClick(View view) {
                CheckBox c = (CheckBox) view;
                if(c.isChecked()){
                    baq.getSeatType().add(c.getText().toString());
                } else{
                    baq.getSeatType().remove(c.getText());
                }

                Log.v(TAG, "item" + c.getText() + " " + "isChecked" + c.isChecked());

                Log.v(TAG, "now seat is :" + baq.getSeatType());



            }
        }
    }




}

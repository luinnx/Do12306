package com.cheart.do12306.app.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.SelectPassengerSetDoAdapter;
import com.cheart.do12306.app.adapter.SelectSeatTypeAdapter;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.domain.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectPassengerSetDo extends ActionBarActivity {


    private static final String TAG = "SelectPassengerSetDo";
    private ListView lv_;
    private List<Map<String, String>> passengerList;



    public static Set<String> SELECTED_PASSENGER_SET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_passenger_set_do);

        init();

        setResult(2);


    }

    private void init() {
        passengerList = new ArrayList<Map<String, String>>();
        SELECTED_PASSENGER_SET = new HashSet<String>();
        initPassengerListForAdapter(CommunalData.PASSENGER_LIST);
        initView();
    }

    private void initView() {

        lv_ = (ListView) findViewById(R.id.lv_select_passenger);
        lv_.setAdapter(new SelectPassengerSetDoAdapter(

                this,
                passengerList,
                R.layout.activity_select_passenger_set_do_item,
                null,
                null

        ));
    }

    private void initPassengerListForAdapter(List<Passenger> passengers){

        for (Passenger p : passengers){
            Map<String, String> map = new HashMap<String, String>();
            map.put("passenger", p.getPassenger_name());
            passengerList.add(map);
        }
        Log.v(TAG, "passenger size:" + passengerList.size());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_seat_type, menu);
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

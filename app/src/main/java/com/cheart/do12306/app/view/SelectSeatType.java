package com.cheart.do12306.app.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.SelectSeatTypeAdapter;
import com.cheart.do12306.app.client.CommunalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectSeatType extends ActionBarActivity {


    private ListView lv_;
    private List<Map<String, String>> seatTypeList;


    public static Set<String> SELECTED_SEAT_TYPE_SET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat_type);

        init();

        setResult(1);


    }

    private void init() {
        seatTypeList = new ArrayList<Map<String, String>>();
        SELECTED_SEAT_TYPE_SET = new HashSet<String>();
        initSeatTypeListForAdapter(CommunalData.ALL_SEAT_TYPE);
        initView();
    }

    private void initView() {

        lv_ = (ListView) findViewById(R.id.lv_select_seat_type);
        lv_.setAdapter(new SelectSeatTypeAdapter(

                this,
                seatTypeList,
                R.layout.activity_select_seat_type_item,
                null,
                null

        ));
    }

    private void initSeatTypeListForAdapter(String... seatTpes){

        for (String s : seatTpes){
            Map<String, String> map = new HashMap<String, String>();
            map.put("seat_type", s);
            seatTypeList.add(map);
        }
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

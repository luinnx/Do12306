package com.cheart.do12306.app.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.SelectTrainCodeAdapter;
import com.cheart.do12306.app.client.CommunalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectTrainCode extends ActionBarActivity {


    private ListView lv_;
    private List<Map<String, String>> trainCodeList;


    public static Set<String> SELECTED_TRAIN_CODE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train_code);

        init();

        setResult(3);


    }

    private void init() {
        trainCodeList = new ArrayList<Map<String, String>>();
        SELECTED_TRAIN_CODE = new HashSet<String>();
        initTrainCodeListForAdapter(CommunalData.DO_TICKET_ALL_TRAIN_CODE);
        initView();
    }

    private void initView() {

        lv_ = (ListView) findViewById(R.id.lv_select_train_code);
        lv_.setAdapter(new SelectTrainCodeAdapter(

                this,
                trainCodeList,
                R.layout.activity_do_ticket_train_code_item,
                null,
                null

        ));
    }

    private void initTrainCodeListForAdapter(String... train_codes){

        for (String s : train_codes){
            Map<String, String> map = new HashMap<String, String>();
            map.put("train_code", s);
            trainCodeList.add(map);
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

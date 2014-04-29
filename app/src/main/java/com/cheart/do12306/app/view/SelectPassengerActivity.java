package com.cheart.do12306.app.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.SelectPassengerAdapter;
import com.cheart.do12306.app.domain.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SelectPassengerActivity extends ActionBarActivity {

    public static List<Map<String, String>> PASSENGER_LIST;
    public static final String ID = "id";
    public static final String PASSENGER_NAME = "passenger_name";
    public static final String PASSENEGR_TYPE_NAME = "passenger_type_name";
    public static final String PASSENGER_ID_NO = "passenger_id_no";

    public static final String[] FROM = new String[]{
            ID,
            PASSENGER_NAME,
            PASSENGER_ID_NO
    };
    public static final int[] TO = new int[]{
            R.id.tv_selectPassenger_id,
            R.id.tv_selectPassenger_name,
            R.id.tv_selectPassenger_cardValue,
    };


    private ListView lv_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_passenger);
        init();


    }


    public void init() {
        PASSENGER_LIST = new ArrayList<Map<String, String>>();
        int id = 0;
        for (Iterator<Passenger> it = MainActivity.PASSENGERS.iterator(); it.hasNext(); ) {
            Passenger p = it.next();
            Map<String, String> m = new HashMap<String, String>();
            m.put(ID, (++id + ""));
            m.put(PASSENGER_NAME, p.getPassenger_name());
            m.put(PASSENEGR_TYPE_NAME, p.getPassenger_type_name());
            m.put(PASSENGER_ID_NO, p.getPassenger_id_no());
            PASSENGER_LIST.add(m);
        }

        initView();

    }

    public void initView() {
        lv_ = (ListView) findViewById(R.id.lv_selectPassenger);
        lv_.setAdapter(new SelectPassengerAdapter(
                this,
                PASSENGER_LIST,
                R.layout.select_passenger_item,
                FROM,
                TO
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_passenger, menu);
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

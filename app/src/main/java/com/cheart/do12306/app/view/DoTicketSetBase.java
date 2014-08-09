package com.cheart.do12306.app.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.task.QueryTicketTask;
import com.cheart.do12306.app.util.DateHelper;

public class DoTicketSetBase extends ActionBarActivity {


    private AutoCompleteTextView aet_from;
    private AutoCompleteTextView aet_to;
    private Spinner sp_date;
    private Button bt_submit;
    public static final String TAG = "DoTicketSetBase";
    public static String SELECTED_DATE = "";
    public static String SELECT_DATE_PARSERED;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_ticket_set_base);
    
        init();
    }

    private void init() {
        CommunalData.loadAllStation(this);
        initView();
    }

    private void initView() {

        aet_from = (AutoCompleteTextView) findViewById(R.id.et_query_from_do_ticket);
        aet_to = (AutoCompleteTextView) findViewById(R.id.et_query_to_do_ticket);
        sp_date = (Spinner) findViewById(R.id.sp_query_date_do_ticket);
        bt_submit = (Button) findViewById(R.id.bt_query_submit_do_ticket);

        sp_date.setAdapter(new ArrayAdapter<String>(
                DoTicketSetBase.this,
                R.layout.activity_query_date_item,
                CommunalData.CAN_BUY_DATE.split(",")
        ));
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_DATE = MainActivity.CAN_BUY_DATE.split(",")[i];
                Log.v(TAG, "SELECTED_DATE" + SELECTED_DATE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        aet_from.setAdapter(new ArrayAdapter<String>(
                DoTicketSetBase.this,
                android.R.layout.simple_dropdown_item_1line,
                CommunalData.getSTATION_ARRAY()
        ));
        aet_to.setAdapter(new ArrayAdapter<String>(
                DoTicketSetBase.this,
                android.R.layout.simple_dropdown_item_1line,
                CommunalData.getSTATION_ARRAY()
        ));


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SELECT_DATE_PARSERED = DateHelper.parserDate(SELECTED_DATE);
                Log.v(TAG,"Select date" + SELECT_DATE_PARSERED);
                new QueryTicketTask(DoTicketSetBase.this,handler).execute(new String[]{
                        CommunalData.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
                        CommunalData.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
                        SELECT_DATE_PARSERED,
                        QueryTicketTask.SIMPLE_FLAG_QUERY
                });


            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.do_ticket_set_base, menu);
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

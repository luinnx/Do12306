package com.cheart.do12306.app.view;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.ResultQueryAdapter;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.domain.Passenger;
import com.cheart.do12306.app.task.QueryTicketTask;
import com.cheart.do12306.app.task.SubmitOrderTask;
import com.cheart.do12306.app.util.DateHelper;

import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowQueryResult extends Activity {


    private static final String TAG = "ShowQueryResult";
    public static List<BaseQueryLeft> TICKET_LIST;
    public static List<BaseData> TICKET_BASEDATA_LIST;
    public static Map<String, Integer> TICKET_MAP;
    public static BaseData b1 = new BaseData();
    private static List<Passenger> p;
    public static String[] DATE_CAN_BUY_ARRAY;
    public static String TICKET_NUM;
    public static String DATE = "";
    private ListView list = null;
    private TextView tv_date;
    private Button bt_pre;
    private Button bt_next;
    private Spinner sp_date;
    public static boolean UPDATED = false;
    public static boolean CAN = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query_result);

        new SubmitOrderTask(this).execute(new String[0x0]);
//        while (!CAN){
//            if (!b1.getQueryLeftNewDTO().getYw_num().equals("无")){
//                CAN = true;
//                new SubmitOrderTask(ShowQueryResult.this).execute(new String[0x0]);
//            } else {
//                CAN = false;
//                Log.v(TAG, "" + b1.getQueryLeftNewDTO().getYw_num());
//                new QueryTicketTask(ShowQueryResult.this).execute(new String[]{
//                        QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
//                        QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
//                        QueryActivity.SELECT_DATE_PARSERED
//                });
//            }
//        }

//        new QueryTicketTask(ShowQueryResult.this).execute(new String[]{
//                QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
//                QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
//                QueryActivity.SELECT_DATE_PARSERED
//        });





        //init();
    }


    public void init() {

        Log.v(TAG,"!!!"+b1.getSecretStr() + b1.getQueryLeftNewDTO().getStation_train_code());
        DATE = QueryActivity.SELECT_DATE_PARSERED;
        initView();
    }

    public void initView() {
        list = (ListView) findViewById(R.id.lv_showQueryResult);
        bt_next = (Button) findViewById(R.id.bt_query_result_next);
        bt_pre = (Button) findViewById(R.id.bt_query_result_pre);
        sp_date = (Spinner) findViewById(R.id.sp_query_result_date);

        sp_date.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.activity_query_date_item,
                MainActivity.CAN_BUY_DATE_SIMPLE.split(",")
        ));
        DATE_CAN_BUY_ARRAY = MainActivity.CAN_BUY_DATE.split(",");
        for (int i = 0; i < DATE_CAN_BUY_ARRAY.length; i++) {
            if (DATE_CAN_BUY_ARRAY[i].equals(QueryActivity.SELECTED_DATE)) {
                sp_date.setSelection(i);
                break;
            }
        }

        Log.v(TAG, "SET ADAPTER");
//        Log.v(TAG, QueryActivity.QUERY_RESULT_LIST.toString());
        list.setAdapter(new ResultQueryAdapter(
                this,
                QueryActivity.QUERY_RESULT_LIST,
                R.layout.activity_resultqueryactivity_item,
                ResultQueryActivityListActivity.FROM,
                ResultQueryActivityListActivity.TO
        ));


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowQueryResult.this, ShowTicketDetail.class);
                Log.v(TAG, "TN" + QueryActivity.QUERY_RESULT_LIST.get(i).
                        get("ticket_num"));
                intent.putExtra("ticket_num", QueryActivity.QUERY_RESULT_LIST.get(i).
                        get("ticket_num"));
                intent.putExtra("ticket_info", ShowQueryResult.TICKET_LIST.get(i));
                intent.putExtra("ticket_info_baseData", ShowQueryResult.TICKET_BASEDATA_LIST.get(i));
                startActivity(intent);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DateHelper.isDateBprder(DateHelper.nextDate(DATE))) {
                    Toast.makeText(ShowQueryResult.this, DATE + "不在车票预售日期内!", Toast.LENGTH_LONG).show();
                } else {
                    DATE = DateHelper.parserDate(DateHelper.nextDate(DATE));
                    QueryActivity.SELECT_DATE_PARSERED = DATE;
                    Log.v(TAG, "ADD" + DATE);
                    new QueryTicketTask(ShowQueryResult.this,handler).execute(new String[]{
                            QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
                            QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
                            DATE/*SELECT_DATE_PARSERED*/,
                            "update"

                    });
                    new UpdateQueryTask(ShowQueryResult.this).execute();
                }

            }
        });

        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DateHelper.isDateBprder(DateHelper.preDate(DATE))) {
                    Toast.makeText(ShowQueryResult.this, DATE + "不在车票预售日期内!", Toast.LENGTH_LONG).show();
                } else {
                    DATE = DateHelper.parserDate(DateHelper.preDate(DATE));
                    QueryActivity.SELECT_DATE_PARSERED = DATE;
                    Log.v(TAG, "ADD" + DATE);
                    new QueryTicketTask(ShowQueryResult.this,handler).execute(new String[]{
                            QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
                            QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
                            DATE/*SELECT_DATE_PARSERED*/,
                            "update"

                    });
                    new UpdateQueryTask(ShowQueryResult.this).execute();
                }
            }
        });

        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DATE = DateHelper.parserDate(DATE_CAN_BUY_ARRAY[i]);
                QueryActivity.SELECT_DATE_PARSERED = DATE;
                new QueryTicketTask(ShowQueryResult.this,handler).execute(new String[]{
                        QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
                        QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
                        DATE/*SELECT_DATE_PARSERED*/,
                        "update"

                });
                new UpdateQueryTask(ShowQueryResult.this).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_query_result, menu);
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

    class UpdateQueryTask extends AsyncTask<String, Integer, String> {

        Context context;

        UpdateQueryTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            while (!UPDATED) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            list.setAdapter(new ResultQueryAdapter(
                    ShowQueryResult.this,
                    QueryActivity.QUERY_RESULT_LIST,
                    R.layout.activity_resultqueryactivity_item,
                    ResultQueryActivityListActivity.FROM,
                    ResultQueryActivityListActivity.TO
            ));
            super.onPostExecute(s);
        }
    }
}

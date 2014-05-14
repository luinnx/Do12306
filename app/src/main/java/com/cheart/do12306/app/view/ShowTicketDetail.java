package com.cheart.do12306.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.util.StringHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShowTicketDetail extends ActionBarActivity {

    private static final String TAG = "ShowTicketDetail";
    public static final String TICKET_PRICE = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice";
    public static final String QUERY_PASS_BY = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo";
    public static final String TEST = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=03000K145200&from_station_no=07&to_station_no=08&seat_types=1413&train_date=2014-05-16";


    private BaseQueryLeft TICKET_INFO;
    public static String CAN_TICKET_TYPE;

    public static BaseData SUBMIT_BASEDATA;

    private TextView tv_stationTrainCode;
    private TextView tv_startTime;
    private TextView tv_arriveTime;
    private TextView tv_lishi;
    private TextView tv_yzNum;
    private TextView tv_rzNum;
    private TextView tv_ywNum;
    private TextView tv_rwNum;
    private TextView tv_gwNum;
    private TextView tv_zyNum;
    private TextView tv_zeNum;
    private TextView tv_ztNum;
    private TextView tv_swzNum;
    private TextView tv_wzNum;
    private Button bt_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket_detail);
        Intent intent = getIntent();
        TICKET_INFO = (BaseQueryLeft) intent.getSerializableExtra("ticket_info");
        SUBMIT_BASEDATA = (BaseData) intent.getSerializableExtra("ticket_info_baseData");
        Log.v(TAG, TICKET_INFO.getStation_train_code());
        init();



    }

    public void initCanTicketType(BaseQueryLeft baseQueryLeft) {
        StringBuffer sb = new StringBuffer();
        if (!baseQueryLeft.getYz_num().equals("无") && !baseQueryLeft.getYz_num().equals("--")){
            sb.append("硬座" + ",");
        } else if (!baseQueryLeft.getRz_num().equals("无") && !baseQueryLeft.getRz_num().equals("--")){
            sb.append("软座" + ",");
        } else if (!baseQueryLeft.getYw_num().equals("无") && !baseQueryLeft.getYw_num().equals("--")){
            sb.append("硬卧" + ",");
        } else if (!baseQueryLeft.getRw_num().equals("无") && !baseQueryLeft.getRw_num().equals("--")){
            sb.append("软卧" + ",");
        } else if (!baseQueryLeft.getGr_num().equals("无") && !baseQueryLeft.getGr_num().equals("--")){
            sb.append("高级软卧" + ",");
        } else if (!baseQueryLeft.getZy_num().equals("无") && !baseQueryLeft.getZy_num().equals("--")){
            sb.append("一等座" + ",");
        } else if (!baseQueryLeft.getZe_num().equals("无") && !baseQueryLeft.getZe_num().equals("--")){
            sb.append("二等座" + ",");
        } else if (!baseQueryLeft.getTz_num().equals("无") && !baseQueryLeft.getTz_num().equals("--")){
            sb.append("特等座" + ",");
        } else if (!baseQueryLeft.getSwz_num().equals("无") && !baseQueryLeft.getSwz_num().equals("--")){
            sb.append("商务座" + ",");
        }
        CAN_TICKET_TYPE = sb.toString();
    }

    public void init() {
        initView();
        new ShowTicketDetailTask(ShowTicketDetail.this).execute("");

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTicketDetail.this, SubmitOrderActivity.class);
                intent.putExtra("ticket_info", TICKET_INFO);
                startActivity(intent);
                //new SubmitOrderTask(ShowTicketDetail.this).execute();
            }
        });
    }

    public void initView() {

        bt_submit = (Button) findViewById(R.id.lv_showTicketDetail_submit);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_ticket_detail, menu);
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

    private class ShowTicketDetailTask extends AsyncTask<String, Integer, String>{
        String urlTicketPrice = "";
        String urlQueryPassBy = "";
        private Context context;

        private ShowTicketDetailTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            init();
        }

        protected void init(){
            initView();
           // initURL();
        }
        protected String initURL(String URL,Map<String, String> params){
            String result = "";
            StringBuffer sb = new StringBuffer();
            String[] paramsKey = (String[]) params.keySet().toArray();
            for (String str : paramsKey){
                sb.append(str + "=" + params.get(str) + "&");
            }
            sb.deleteCharAt(sb.length() - 1);
            result = sb.toString();

            return result;


        }
        protected void initView(){

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            new Thread(new NetThreadTicketDetail()).start();
            return null;
        }

        private class NetThreadTicketDetail implements Runnable{

            @Override
            public void run() {
                Map<String,String> paramsTicketPrice = new HashMap<String, String>();
                paramsTicketPrice.put("train_no", TICKET_INFO.getTrain_no());
                paramsTicketPrice.put("from_station_no", TICKET_INFO.getFrom_station_no());
                paramsTicketPrice.put("to_station_no", TICKET_INFO.getTo_station_no());
                paramsTicketPrice.put("seat_types",TICKET_INFO.getSeat_types());
                paramsTicketPrice.put("train_date", QueryActivity.SELECT_DATE_PARSERED);
                String resultTicketPrice = MainActivity.core.getRequest(
                        context,
                        StringHelper.parserGetUrl(TICKET_PRICE,paramsTicketPrice),
                        null,
                        HttpsHeader.tiketSearch(),
                        null,
                        false
                );

                Log.v(TAG, "resultTicketPrice" + resultTicketPrice);
                Map<String,String> paramsQueryPassBy = new HashMap<String, String>();
                paramsQueryPassBy.put("train_no", TICKET_INFO.getTrain_no());
                paramsQueryPassBy.put("from_station_telecode", TICKET_INFO.getStation_train_code());
                paramsQueryPassBy.put("depart_date", QueryActivity.SELECT_DATE_PARSERED);
                String resultQueRPassBy = MainActivity.core.getRequest(
                        context,
                        StringHelper.parserGetUrl(TICKET_PRICE,paramsQueryPassBy),
                        null,
                        HttpsHeader.tiketSearch(),
                        null,
                        false
                );
                Log.v(TAG, "resultQueryPassBy" + resultQueRPassBy);

            }
        }
    }
}

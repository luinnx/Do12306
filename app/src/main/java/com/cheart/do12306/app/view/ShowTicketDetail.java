package com.cheart.do12306.app.view;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.ShowTicketDetailPassAdapter;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.domain.PassStation;
import com.cheart.do12306.app.task.SubmitOrderTask;
import com.cheart.do12306.app.util.StringHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowTicketDetail extends ActionBarActivity {

    private static final String TAG = "ShowTicketDetail";
    public static final String TICKET_PRICE = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice";
    public static final String QUERY_PASS_BY = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo";
    public static final String TEST = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=03000K145200&from_station_no=07&to_station_no=08&seat_types=1413&train_date=2014-05-16";


    private BaseQueryLeft TICKET_INFO;
    public static String CAN_TICKET_TYPE;

    public static BaseData SUBMIT_BASEDATA;
    public static List<PassStation> PASS_STATION_LIST;
    public static String PRICE_STRING;
    public static String TICKET_NUM_PRICE_STRING;
    private boolean success = false;
    private TextView tv_fromStationName;
    private TextView tv_toStationName;
    private TextView tv_stationTrainCode;
    private TextView tv_date;
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
    private ListView lv_pass;
    private TextView tv_t0;
    private TextView tv_t1;
    private TextView tv_t2;
    private TextView tv_t3;
    private TextView tv_p0;
    private TextView tv_p1;
    private TextView tv_p2;
    private TextView tv_p3;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket_detail);
        pd = new ProgressDialog(this);
        PASS_STATION_LIST = new ArrayList<PassStation>();
        PRICE_STRING = "";
        Intent intent = getIntent();
        TICKET_INFO = (BaseQueryLeft) intent.getSerializableExtra("ticket_info");
        SUBMIT_BASEDATA = (BaseData) intent.getSerializableExtra("ticket_info_baseData");
        TICKET_NUM_PRICE_STRING = intent.getStringExtra("ticket_num");
        Log.v(TAG, TICKET_INFO.getStation_train_code());
        init();


    }

    public void initCanTicketType(BaseQueryLeft baseQueryLeft) {
        StringBuffer sb = new StringBuffer();
        if (!baseQueryLeft.getYz_num().equals("无") && !baseQueryLeft.getYz_num().equals("--")) {
            sb.append("硬座" + ",");
        } else if (!baseQueryLeft.getRz_num().equals("无") && !baseQueryLeft.getRz_num().
                equals("--")) {
            sb.append("软座" + ",");
        } else if (!baseQueryLeft.getYw_num().equals("无") && !baseQueryLeft.getYw_num().
                equals("--")) {
            sb.append("硬卧" + ",");
        } else if (!baseQueryLeft.getRw_num().equals("无") && !baseQueryLeft.getRw_num().
                equals("--")) {
            sb.append("软卧" + ",");
        } else if (!baseQueryLeft.getGr_num().equals("无") && !baseQueryLeft.getGr_num().
                equals("--")) {
            sb.append("高级软卧" + ",");
        } else if (!baseQueryLeft.getZy_num().equals("无") && !baseQueryLeft.getZy_num().
                equals("--")) {
            sb.append("一等座" + ",");
        } else if (!baseQueryLeft.getZe_num().equals("无") && !baseQueryLeft.getZe_num().
                equals("--")) {
            sb.append("二等座" + ",");
        } else if (!baseQueryLeft.getTz_num().equals("无") && !baseQueryLeft.getTz_num().
                equals("--")) {
            sb.append("特等座" + ",");
        } else if (!baseQueryLeft.getSwz_num().equals("无") && !baseQueryLeft.getSwz_num().
                equals("--")) {
            sb.append("商务座" + ",");
        }
        CAN_TICKET_TYPE = sb.toString();
    }

    public void init() {
        pd.show();
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
        tv_t0 = (TextView) findViewById(R.id.tv_showTicketDetail_t0);
        tv_t1 = (TextView) findViewById(R.id.tv_showTicketDetail_t1);
        tv_t2 = (TextView) findViewById(R.id.tv_showTicketDetail_t2);
        tv_t3 = (TextView) findViewById(R.id.tv_showTicketDetail_t3);
        tv_p0 = (TextView) findViewById(R.id.tv_showTicketDetail_p0);
        tv_p1 = (TextView) findViewById(R.id.tv_showTicketDetail_p1);
        tv_p2 = (TextView) findViewById(R.id.tv_showTicketDetail_p2);
        tv_p3 = (TextView) findViewById(R.id.tv_showTicketDetail_p3);
        bt_submit = (Button) findViewById(R.id.lv_showTicketDetail_submit);
        lv_pass = (ListView) findViewById(R.id.lv_showTicketDetail_pass);
        tv_fromStationName = (TextView) findViewById(R.id.tv_showTicketDetail_fromStationName);
        tv_toStationName = (TextView) findViewById(R.id.tv_showTicketDetail_toStationName);
        tv_startTime = (TextView) findViewById(R.id.tv_showTicketDetail_startTime);
        tv_arriveTime = (TextView) findViewById(R.id.tv_showTicketDetail_arriveTime);
        tv_lishi = (TextView) findViewById(R.id.tv_showTicketDetail_lishi);
        tv_stationTrainCode = (TextView) findViewById(R.id.tv_showTicketDetail_stationTrainCode);
        tv_date = (TextView) findViewById(R.id.tv_showTicketDetail_date);
        tv_fromStationName.setText(TICKET_INFO.getFrom_station_name());
        tv_toStationName.setText(TICKET_INFO.getTo_station_name());
        tv_startTime.setText(TICKET_INFO.getStart_time());
        tv_arriveTime.setText(TICKET_INFO.getArrive_time());
        tv_lishi.setText( parserLishi(TICKET_INFO.getLishi()));
        tv_stationTrainCode.setText(TICKET_INFO.getStation_train_code() + "(" + TICKET_INFO.
                getTrain_class_name() + ")");
        tv_date.setText(QueryActivity.SELECT_DATE_PARSERED);





    }

    private String parserLishi(String str){
        String result = "";
        StringBuffer sb = new StringBuffer();
        String[] arr = str.split(":");
        if (Integer.parseInt(arr[0]) < 10){
            sb.append(Integer.parseInt(arr[0]) + "小时" + arr[1] + "分钟");
        } else {
            sb.append(arr[0] + "小时" + arr[1] + "分钟");
        }
        result = sb.toString();
        Log.v(TAG, result);
        return result;
    }

    private void updateViewPrice() {

        String[] ticketNum = TICKET_NUM_PRICE_STRING.split(",");
        Log.v(TAG,"LENGTH" + ticketNum.length + "  " + TICKET_NUM_PRICE_STRING);
        for (int i = 0; i < ticketNum.length; i++) {
            if (tv_t0.getText().equals("")) {
                String[] arr = ticketNum[i].split(">");

                tv_t0.setText(arr[0] + ":" + arr[1]);
                tv_p0.setText(arr[2]);
                if (arr[1].equals("无")) {
                    tv_t0.setTextColor(getResources().getColor(R.color.query_result_w));
                }
            } else if (tv_t1.getText().equals("")) {
                String[] arr = ticketNum[i].split(">");
                tv_t1.setText(arr[0] + ":" + arr[1]);
                tv_p1.setText(arr[2]);
                if (arr[1].equals("无")) {
                    tv_t1.setTextColor(getResources().getColor(R.color.query_result_w));
                }
            } else if (tv_t2.getText().equals("")) {
                String[] arr = ticketNum[i].split(">");
                tv_t2.setText(arr[0] + ":" + arr[1]);
                tv_p2.setText(arr[2]);
                if (arr[1].equals("无")) {
                    tv_t2.setTextColor(getResources().getColor(R.color.query_result_w));
                }
            } else if (tv_t3.getText().equals("")) {
                String[] arr = ticketNum[i].split(">");
                tv_t3.setText(arr[0] + ":" + arr[1]);
                tv_p3.setText(arr[2]);
                if (arr[1].equals("无")) {
                    tv_t3.setTextColor(getResources().getColor(R.color.query_result_w));
                }
            }


        }

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

    private class ShowTicketDetailTask extends AsyncTask<String, Integer, String> {
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

        protected void init() {
            initView();
            // initURL();
        }

        protected String initURL(String URL, Map<String, String> params) {
            String result = "";
            StringBuffer sb = new StringBuffer();
            String[] paramsKey = (String[]) params.keySet().toArray();
            for (String str : paramsKey) {
                sb.append(str + "=" + params.get(str) + "&");
            }
            sb.deleteCharAt(sb.length() - 1);
            result = sb.toString();

            return result;


        }

        protected void initView() {

        }

        @Override
        protected void onPostExecute(String s) {

            Log.v(TAG, "TEST" + PASS_STATION_LIST.get(2).getStation_name());
            lv_pass.setAdapter(new ShowTicketDetailPassAdapter(
                    ShowTicketDetail.this,
                    null,
                    PASS_STATION_LIST,
                    R.layout.show_ticket_detail_item,
                    null,
                    null
            ));
            Log.v(TAG, "Set pass Adapter!");
            initTicketNumPrice();
            updateViewPrice();
            pd.dismiss();







        }

        protected void initTicketNumPrice(){
            StringBuffer sb = new StringBuffer();
            String[] arr1 = TICKET_NUM_PRICE_STRING.split(",");
            String[] arr2 = PRICE_STRING.split(",");


            Log.v(TAG, TICKET_NUM_PRICE_STRING);
            Log.v(TAG, PRICE_STRING);
            for (int i = 0; i < arr1.length; i++){
                for (int j = 0; j < arr2.length; j++){
                    if (CommunalData.getSEAT_TYPE_CODE_MAP().get(arr1[i].split(">")[0]).equals(arr2[j].
                            split(">")[0])){
                        Log.v(TAG, "INIT SUCCESS");

                        sb.append(arr1[i] + ">" + arr2[j].split(">")[1] + ",");

                    }

                };

            }
            Log.v(TAG, "P" + sb.toString());
            TICKET_NUM_PRICE_STRING = sb.toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            new Thread(new NetThreadTicketDetail()).start();

            while (!success) {

            }
            return null;
        }

        private class NetThreadTicketDetail implements Runnable {

            @Override
            public void run() {
                Map<String, String> paramsTicketPrice = new LinkedHashMap<String, String>();
                paramsTicketPrice.put("train_no", TICKET_INFO.getTrain_no());
                paramsTicketPrice.put("from_station_no", TICKET_INFO.getFrom_station_no());
                paramsTicketPrice.put("to_station_no", TICKET_INFO.getTo_station_no());
                paramsTicketPrice.put("seat_types", TICKET_INFO.getSeat_types());
                paramsTicketPrice.put("train_date", QueryActivity.SELECT_DATE_PARSERED);
                String resultTicketPrice = MainActivity.core.getRequest(
                        context,
                        StringHelper.parserGetUrl(TICKET_PRICE, paramsTicketPrice),
                        null,
                        HttpsHeader.tiketSearch(),
                        null,
                        false
                );

                Log.v(TAG, "resultTicketPrice" + resultTicketPrice);
                PRICE_STRING = parserPrice(resultTicketPrice);
                Log.v(TAG, PRICE_STRING);
                Map<String, String> paramsQueryPassBy = new LinkedHashMap<String, String>();
                paramsQueryPassBy.put("train_no", TICKET_INFO.getTrain_no());
                paramsQueryPassBy.put("from_station_telecode", TICKET_INFO.
                        getFrom_station_telecode());
                paramsQueryPassBy.put("to_station_telecode", TICKET_INFO.getTo_station_telecode());
                paramsQueryPassBy.put("depart_date", QueryActivity.SELECT_DATE_PARSERED);
                String resultQueRPassBy = MainActivity.core.getRequest(
                        context,
                        StringHelper.parserGetUrl(QUERY_PASS_BY, paramsQueryPassBy),
                        null,
                        HttpsHeader.tiketSearch(),
                        null,
                        false
                );
                Log.v(TAG, "resultQueryPassBy" + resultQueRPassBy);
                parserPass(resultQueRPassBy);
                success = true;

            }
        }

        public void parserPass(String strJson) {

            JsonArray datas = new JsonParser().parse(strJson).getAsJsonObject().get("data").
                    getAsJsonObject().get("data").getAsJsonArray();
            Log.v(TAG, "JSON DATAS SIZE IS :" + datas.size());
            List<PassStation> passStationResults = new Gson().fromJson(
                    datas,
                    new TypeToken<List<PassStation>>() {
                    }.getType()
            );

            PASS_STATION_LIST = passStationResults;

            for (Iterator<PassStation> it = passStationResults.iterator(); it.hasNext(); ) {
                Log.v(TAG, "stationName on:" + it.next().getStation_name());
            }


        }

        public String parserPrice(String strJson) {
            StringBuffer sb = new StringBuffer();
            String result = "";
            JsonObject obj = new JsonParser().parse(strJson).getAsJsonObject().get("data").
                    getAsJsonObject();
            for (Iterator<Map.Entry<String, JsonElement>> it = obj.entrySet().iterator(); it.
                    hasNext(); ) {
                Map.Entry<String, JsonElement> elementEntry = it.next();
                if (!elementEntry.getValue().toString().equals("[]")){
                    sb.append(elementEntry.getKey() + ">" + elementEntry.getValue().toString().
                            split("\"")[1] + ",");
                }

            }
            result = sb.toString();
            return result;

        }
    }


}

package com.cheart.do12306.app.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseAutoQuery;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestDoQuery extends ActionBarActivity {

    private static final String TAG = "TestDoQuery";
    private TextView tv_result;
    private BaseAutoQuery baq;
    public static List<BaseData> resultBaseDateList;
    public static List<Map<String, String>> resultTicketList;

    public static boolean isCanBuy = false;
    public static boolean isQueryFinished = false;
    private String nowQueryDate = "";



    List<String> byTrainCode = new ArrayList<String>();
    Set<String> bySeatType = new HashSet<String>();
    Set<String> byDate = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_do_query);
        baq = (BaseAutoQuery) getIntent().getSerializableExtra("base_auto_query");
        init();


        Log.v(TAG, "TestDoQuery");

        while(!isCanBuy){
            Log.v(TAG, "dates:" + byDate);
            for (String date : byDate){
                nowQueryDate = date;
                isQueryFinished = false;
                Log.v(TAG, "query date is:" + nowQueryDate);
                new QueryTicketTask(TestDoQuery.this).execute(new String[]{

                        CommunalData.getSTATION_MAP().get("北京"/*aet_from.getText().toString()*/),
                        CommunalData.getSTATION_MAP().get("上海"/*aet_to.getText().toString()*/),
                        "2014-" + nowQueryDate

                });

                Log.v(TAG, "start while");

                while (!isQueryFinished){

                }

                Log.v(TAG, "end while");

            }

       }

    }

    protected void init() {
        resultBaseDateList = new ArrayList<BaseData>();
        resultTicketList = new ArrayList<Map<String, String>>();
        //test
        byTrainCode.add("G101");
        byTrainCode.add("G103");
        byTrainCode.add("G1");
        bySeatType = baq.getSeatType();
        byDate = baq.getDate();
        initView();
    }

    protected void initView() {
        tv_result = (TextView) findViewById(R.id.tv_testDo_result);


    }

    protected String filterTrainBySeatType(List<Map<String, String>> resultQuery) {
        Log.v(TAG, "FILTER TICKET!");
        StringBuffer sb = new StringBuffer();
        StringBuffer item = new StringBuffer();


        Iterator<Map<String, String>> it = resultQuery.iterator();

        while (it.hasNext()) {
            boolean isMatch = false;
            Map<String, String> m = it.next();
            if (byTrainCode.contains(m.get(QueryTicketTask.STATION_TRAIN_CODE))) {
                item.append(m.get(QueryTicketTask.STATION_TRAIN_CODE) + ">" + nowQueryDate);
                String[] arr = m.get(QueryTicketTask.TICKET_NUM).split(",");
                for (String s : arr) {
                    String num = s.split(">")[1];
                    String seatType = s.split(">")[0];
                    if (bySeatType.contains(seatType)) {
                        if (null != num && !num.equals("") && !num.equals("无")) {
                            isMatch = true;
                            item.append(">" + seatType);
                        }
                    }

                }
                if(isMatch){
                    sb.append(item.toString() + ",");
                    isMatch = false;
                }
                item.delete(0,item.length());
            }

        }
        return sb.toString();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_do_query, menu);
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

    public class QueryTicketTask extends AsyncTask<String, Integer, List<Map<String, String>>> {

        private static final String TAG = "QueryTicketTask";
        private List<Map<String, String>> result;
        List<BaseQueryLeft> baseQueryLefts;
        List<BaseData> baseDatas;
        public static final String STATION_TRAIN_CODE = "station_train_code";
        public static final String START_TIME = "start_time";
        public static final String ARRIVE_TIME = "arrive_time";
        public static final String FROM_STATION_NAME = "from_station_name";
        public static final String TO_STATION_NAME = "to_station_name";
        public static final String YZ_NUM = "yz_num";
        public static final String RZ_NUM = "rz_num";
        public static final String YW_NUM = "yw_num";
        public static final String RW_NUM = "rw_num";
        public static final String GR_NUM = "gr_num";
        public static final String ZY_NUM = "zy_num";
        public static final String ZE_NUM = "ze_num";
        public static final String TD_NUM = "td_num";
        public static final String SWZ_NUM = "swz_num";
        public static final String TICKET_NUM = "ticket_num";
        public static final String WP_NUM = "wp_num";
        public static final String DC_NUM = "dc_num";
        public static final String TRAIN_CLASS = "train_class_name";
        public static final String TRAIN_CLASS_NAME = "train_class_name";
        private ProgressDialog pd;

        Context context;

        public QueryTicketTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Map<String, String>> doInBackground(String... strings) {

            return query(strings);

        }

        protected List<Map<String, String>> query(String... strings) {

            Log.v(TAG, "QUERY" + strings[0] + strings[1] + strings[2]);


            String ticketQueryUrl = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="
                    + strings[2]
                    + "&leftTicketDTO.from_station="
                    + strings[0]
                    + "&leftTicketDTO.to_station="
                    + strings[1] + "&purpose_codes=ADULT";
            String resultTicketQuery = MainActivity.core.getRequest(context, ticketQueryUrl,
                    null, HttpsHeader.tiketSearch(), null, false);
            Log.v(TAG, resultTicketQuery);
            return parserResultQueryItemFromQueryResult(resultTicketQuery);

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.show();
            result = new ArrayList<Map<String, String>>();
            baseQueryLefts = new ArrayList<BaseQueryLeft>();
            baseDatas = new ArrayList<BaseData>();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> resultList) {
            Log.v(TAG, "R" + resultList.size());
            resultTicketList = resultList;
            resultBaseDateList = baseDatas;
            isQueryFinished = true;
            Log.v(TAG, "ISFINISH" + isQueryFinished);
            String result = filterTrainBySeatType(resultList);
            if (null != result && !result.equals("")){
                isCanBuy = true;
                Log.v(TAG, "parser result is:" + filterTrainBySeatType(resultList));

            }




        }


        protected List<Map<String, String>> parserResultQueryItemFromQueryResult(String str) {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            JsonArray datas = new JsonParser().parse(str).getAsJsonObject()
                    .get("data").getAsJsonArray();
            List<BaseData> baseQueryResults = new Gson().fromJson(
                    datas,
                    new TypeToken<List<BaseData>>() {
                    }.getType()
            );

            for (Iterator<BaseData> it = baseQueryResults.iterator(); it.hasNext(); ) {
                BaseData b = it.next();
                StringBuffer sb = new StringBuffer();
                baseDatas.add(b);
                BaseQueryLeft bql = b.getQueryLeftNewDTO();
                Map<String, String> m = new HashMap<String, String>();

                if (!bql.getYz_num().equals("--")) {
                    sb.append("硬座" + ">" + bql.getYz_num() + ",");
                }
                if (!bql.getRz_num().equals("--")) {
                    sb.append("软座" + ">" + bql.getRz_num() + ",");
                }
                if (!bql.getYw_num().equals("--")) {
                    sb.append("硬卧" + ">" + bql.getYw_num() + ",");
                }

                if (!bql.getRw_num().equals("--")) {
                    sb.append("软卧" + ">" + bql.getRw_num() + ",");
                }

                if (!bql.getGr_num().equals("--")) {
                    sb.append("高级软卧" + ">" + bql.getGr_num() + ",");
                }

                if (!bql.getZy_num().equals("--")) {
                    sb.append("一等座" + ">" + bql.getZy_num() + ",");
                }

                if (!bql.getZe_num().equals("--")) {
                    sb.append("二等座" + ">" + bql.getZe_num() + ",");
                }

                if (!bql.getTz_num().equals("--")) {
                    sb.append("特等座" + ">" + bql.getTz_num() + ",");
                }

                if (!bql.getSwz_num().equals("--")) {
                    sb.append("商务座" + ">" + bql.getSwz_num() + ",");
                }

                m.put(TICKET_NUM, sb.toString());
                m.put(STATION_TRAIN_CODE, bql.getStation_train_code());
                m.put(START_TIME, bql.getStart_time());
                m.put(ARRIVE_TIME, bql.getArrive_time());
                m.put(FROM_STATION_NAME, bql.getFrom_station_name());
                m.put(TO_STATION_NAME, bql.getTo_station_name());

                m.put(TRAIN_CLASS, bql.getTrain_class_name());
                m.put(TRAIN_CLASS_NAME, bql.getTrain_class_name());
                result.add(m);
                baseQueryLefts.add(bql);
            }

            Log.v(TAG, "SIZE" + result.size());

            return result;
        }
    }

}

package com.cheart.do12306.app.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.view.QueryActivity;
import com.cheart.do12306.app.view.ShowQueryResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/25/2014.
 */
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

    public static boolean update = false;

    Context context;

    public QueryTicketTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Map<String, String>> doInBackground(String... strings) {

        return query(strings);

    }

    protected List<Map<String, String>> query(String... strings){
        ShowQueryResult.DATE = strings[2];

        Log.v(TAG, "QUERY" + strings[0] + strings[1] + strings[2]);
        if (strings.length >= 4){
            if (strings[3].equals("update")){
                update = true;
            }
        } else {
            update = false;
        }


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
        QueryActivity.QUERY_RESULT_LIST = resultList;
        ShowQueryResult.TICKET_LIST = baseQueryLefts;
        ShowQueryResult.TICKET_BASEDATA_LIST = baseDatas;
        initTicketMap(baseDatas);
        Intent intent = new Intent(context, ShowQueryResult.class);
        if(update){
            Log.v(TAG, "FOR UPDATE");
            ShowQueryResult.UPDATED = true;
            pd.dismiss();
        } else {
            Log.v(TAG, "FOR START ACTIVITY");
            context.startActivity(intent);
            pd.dismiss();
        }


    }



    protected void initTicketMap(List<BaseData> baseDataList) {
        ShowQueryResult.TICKET_MAP = new HashMap<String, Integer>();
        for (int i = 0; i < baseDataList.size(); i++){
            ShowQueryResult.TICKET_MAP.put(baseDataList.get(i).getQueryLeftNewDTO().
                    getStation_train_code(),i);
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

        for (Iterator<BaseData> it = baseQueryResults.iterator(); it.hasNext();){
            BaseData b = it.next();
            StringBuffer sb = new StringBuffer();
            baseDatas.add(b);
            BaseQueryLeft bql = b.getQueryLeftNewDTO();
            Map<String, String> m = new HashMap<String, String>();

            if (!bql.getYz_num().equals("--")){
                sb.append("硬座" + ">" + bql.getYz_num() + ",");
            }
            if (!bql.getRz_num().equals("--")){
                sb.append("软座" + ">" + bql.getRz_num() + ",");
            }
            if (!bql.getYw_num().equals("--")){
                sb.append("硬卧" + ">" + bql.getYw_num() + ",");
            }

            if (!bql.getRw_num().equals("--")){
                sb.append("软卧" + ">" + bql.getRw_num() + ",");
            }

            if (!bql.getGr_num().equals("--")){
                sb.append("高级软卧" + ">" + bql.getGr_num() + ",");
            }

            if (!bql.getZy_num().equals("--")){
                sb.append("一等座" + ">" + bql.getZy_num() + ",");
            }

            if (!bql.getZe_num().equals("--")){
                sb.append("二等座" + ">" + bql.getZe_num() + ",");
            }

            if (!bql.getTz_num().equals("--")){
                sb.append("特等座" + ">" + bql.getTz_num() + ",");
            }

            if (!bql.getSwz_num().equals("--")){
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
            Log.v(TAG, m.size() + "ms");
            result.add(m);
            baseQueryLefts.add(bql);
        }

        Log.v(TAG, "SIZE" + result.size());

        return result;
    }
}

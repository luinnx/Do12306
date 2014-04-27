package com.cheart.do12306.app.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.domain.BaseQueryResult;
import com.cheart.do12306.app.domain.ResultQueryListItem;
import com.cheart.do12306.app.view.QueryActivity;
import com.cheart.do12306.app.view.ShowQueryResult;
import com.cheart.do12306.app.view.ShowTicketDetail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public static final String YZ_NUM = "yz_num";
    public static final String RZ_NUM = "rz_num";
    public static final String YW_NUM = "yw_num";
    public static final String RW_NUM = "rw_num";
    public static final String ZY_NUM = "zy_num";
    public static final String ZE_NUM = "ze_num";
    public static final String SWZ_NUM = "swz_num";
    public static final String WP_NUM = "wp_num";
    public static final String DC_NUM = "dc_num";

    Context context;

    public QueryTicketTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Map<String, String>> doInBackground(String... strings) {
        String ticketQueryUrl = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="
                + "2014-05-01"//strings[2]
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
        Intent intent = new Intent(context, ShowQueryResult.class);
        context.startActivity(intent);

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
            baseDatas.add(b);
            BaseQueryLeft bql = b.getQueryLeftNewDTO();
            Map<String, String> m = new HashMap<String, String>();
            m.put(STATION_TRAIN_CODE, bql.getStation_train_code());
            m.put(START_TIME, bql.getStart_time());
            m.put(ARRIVE_TIME, bql.getArrive_time());
            m.put(YZ_NUM, bql.getYz_num());
            m.put(RZ_NUM, bql.getRz_num());
            m.put(YW_NUM, bql.getYw_num());
            m.put(RW_NUM, bql.getRw_num());
            m.put(ZY_NUM, bql.getZy_num());
            m.put(ZE_NUM, bql.getZe_num());
            m.put(SWZ_NUM, bql.getSwz_num());
            m.put(WP_NUM, bql.getYw_num());
            Log.v(TAG, m.size() + "ms");
            result.add(m);
            baseQueryLefts.add(bql);
        }

        Log.v(TAG, "SIZE" + result.size());

        return result;
    }
}

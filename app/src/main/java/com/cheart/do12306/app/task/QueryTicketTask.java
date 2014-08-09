package com.cheart.do12306.app.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.view.DoTicket;
import com.cheart.do12306.app.view.DoTicketSetBase;
import com.cheart.do12306.app.view.DoTicketSetDo;
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
    private static boolean CAN = false;
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
    public static final String SIMPLE_FLAG_QUERY = "simple";
    public static final String UPDATE_FLAG_QUERY = "update";
    public static final String DO_FLAG_QUERY = "do";
    public static final String NORMAL_FLAG_QUERY = "normal";
    private ProgressDialog pd;
    public static long queryCount = 0;

    public static boolean isUpdateQuery = false;
    public static boolean isSimpleQuery = false;
    public static boolean isNormalQuery = false;
    public static boolean isDoQuery = false;

    Context context;
    Handler handler;

    public QueryTicketTask(Context context,Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected List<Map<String, String>> doInBackground(String... strings) {

        for (String s : strings) {
            Log.v(TAG, "args " + s);
        }
        if (strings.length < 4){
            try {
                throw new Exception("query args is error!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            if(strings[3].equals(SIMPLE_FLAG_QUERY)){
                isSimpleQuery = true;
                simpleQuery(strings);
            } else if (strings[3].equals(UPDATE_FLAG_QUERY)){
                isUpdateQuery = true;
                updateQuery(strings);
            } else if (strings[3].equals(DO_FLAG_QUERY)){
                isDoQuery = true;
                doQuery(strings);
            } else if (strings[3].equals(NORMAL_FLAG_QUERY)){
                isNormalQuery = true;
                normalQuery(strings);
            } else {
                isNormalQuery = false;
                isUpdateQuery = false;
                isDoQuery = false;
                isSimpleQuery = false;
            }


        }



        return result;

    }

    private List<Map<String, String>> doQuery(String...strings) {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        boolean isFirst = true;

        while (!CAN) {

            if (isFirst) {
                result = query(strings);
                isFirst = false;
            } else {
                Log.v(TAG, "ELSE");
                for (String train_code : CommunalData.SELECTED_TRAIN_CODE_SET){
                    Log.v(TAG, "while====trainCode" + train_code);
                    Map<String, String> ticket_info = CommunalData.DO_TICKET_RESULT_QUERY_MAP.get(train_code);
                    Log.v(TAG, "while====ticket_info" + ticket_info);
                    if (null != ticket_info && ticket_info.size() > 0){

                        for (String seat_type : CommunalData.SELECTED_SEAT_TYPE_SET){
                            Log.v(TAG, "while====seat_type" + seat_type);
                            Log.v(TAG, "while====seatType" + train_code);
                            String info = ticket_info.get(seat_type);
                            String log = "QUERY" + " ---- " + train_code + " ----  " + seat_type + " ---- " + info;
                            Message msg = handler.obtainMessage();
                            msg.obj = log;
                            handler.sendMessage(msg);
                            Log.v(TAG, log);
                            if(
                                        null != info &&
                                        !info.equals("") &&
                                        !info.equals("--") &&
                                        !info.equals("*") &&
                                        !info.equals("无")
                                    ){
                                        String logSuccessful = "CAN BUY" + " ---- " + train_code + " ----  " + seat_type + " ---- " + info;
                                        Log.v(TAG, logSuccessful);
                                        msg.obj = logSuccessful;
                                        handler.sendMessage(msg);


                                        CAN = true;

                                        //  new SubmitOrderTask(context).execute(new String[0x0]);
                                        return result;
                            }

                        }
                    }
                }

                if (!CAN){
                    result = query(strings);
                }
//
//                if (!ShowQueryResult.b1.getQueryLeftNewDTO().getZe_num().equals("无") &&
//                        !ShowQueryResult.b1.getQueryLeftNewDTO().getZe_num().equals("*") &&
//                        !ShowQueryResult.b1.getQueryLeftNewDTO().getZe_num().equals("-") &&
//                        !ShowQueryResult.b1.getSecretStr().equals("")
//                        ) {
//
//                    CAN = true;
//
//                } else {
//                    CAN = false;
////                    Toast.makeText(context,"" + ShowQueryResult.b1.getQueryLeftNewDTO().getYw_num(),Toast.LENGTH_LONG).show();
//                    BaseData b = ShowQueryResult.b1;
//                    Log.v(TAG, "date" + b.getQueryLeftNewDTO().getStart_train_date());
//                    Log.v(TAG, "strainCode" + b.getQueryLeftNewDTO().getStation_train_code());
//                    Log.v(TAG, "secretStr" + b.getSecretStr());
//                    Log.v(TAG, CommunalData.t + b.getQueryLeftNewDTO().getZe_num());
//                    //    Log.v(TAG, "" + ShowQueryResult.b1.getQueryLeftNewDTO().getStation_train_code() + "--->" + ShowQueryResult.b1.getQueryLeftNewDTO().getYw_num());
//                    result = query(strings);
////                doInBackground(new String[]{
////                        QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
////                        QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
////                        QueryActivity.SELECT_DATE_PARSERED});
////                    new QueryTicketTask(context).execute(new String[]{
////                            QueryActivity.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
////                            QueryActivity.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
////                            QueryActivity.SELECT_DATE_PARSERED
////                    });
//                }
            }

        }

        return result;

    }


    private List<Map<String, String>> normalQuery(String...strings) {
        return query(strings);

    }


    private List<Map<String, String>> updateQuery(String...strings) {

        return query(strings);

    }

    protected List<Map<String, String>> simpleQuery(String... strings){
        return query(strings);
    }

    protected List<Map<String, String>> query(String... strings) {
        ShowQueryResult.DATE = strings[2];

        Log.v(TAG, "QUERY" + strings[0] + "--" + strings[1] + "--" + strings[2] + "--" + strings[3]);




        String ticketQueryUrl = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="
                + CommunalData.date
                + "&leftTicketDTO.from_station="
                + strings[0]
                + "&leftTicketDTO.to_station="
                + strings[1] + "&purpose_codes=0X00";
        String resultTicketQuery = MainActivity.core.getRequest(context, ticketQueryUrl,
                null, HttpsHeader.tiketSearch(), null, false);
        Log.v(TAG, resultTicketQuery);
        queryCount++;
        Log.v(TAG, "QueryCount" + queryCount);
        return parserResultQueryItemFromQueryResult(resultTicketQuery);

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        //  pd.show();
        result = new ArrayList<Map<String, String>>();
        baseQueryLefts = new ArrayList<BaseQueryLeft>();
        baseDatas = new ArrayList<BaseData>();

//        ShowQueryResult.b1 = baseDatas.get(17);

        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> resultList) {
     //   Log.v(TAG, "R" + resultList.size());
        QueryActivity.QUERY_RESULT_LIST = resultList;
        ShowQueryResult.TICKET_LIST = baseQueryLefts;
        ShowQueryResult.TICKET_BASEDATA_LIST = baseDatas;
        initTicketMap(baseDatas);

        if (isUpdateQuery) {
            Log.v(TAG, "FOR UPDATE");
            ShowQueryResult.UPDATED = true;
            pd.dismiss();
        } else if (isNormalQuery) {
    //        Log.v(TAG, "FOR START ACTIVITY");


            //    context.startActivity(intent);
            pd.dismiss();
        } else if (isDoQuery){

            SubmitOrderTask task = new SubmitOrderTask(context);
            task.execute();

        } else if (isSimpleQuery){

            CommunalData.DO_TICKET_ALL_TRAIN_CODE = CommunalData.parserAllTrainCodeBaseData(baseDatas);
            Intent intent = new Intent(context, DoTicketSetDo.class);
            context.startActivity(intent);

        }


    }


    protected void initTicketMap(List<BaseData> baseDataList) {
        ShowQueryResult.TICKET_MAP = new HashMap<String, Integer>();
        for (int i = 0; i < baseDataList.size(); i++) {
            ShowQueryResult.TICKET_MAP.put(baseDataList.get(i).getQueryLeftNewDTO().
                    getStation_train_code(), i);
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
        CommunalData.DO_TICKET_BASEDATA_LIST = new ArrayList<BaseData>();
        Map<String,Map<String, String>> do_ticket_result_map = new HashMap<String, Map<String, String>>();
        for (Iterator<BaseData> it = baseQueryResults.iterator(); it.hasNext(); ) {
            BaseData b = it.next();
            StringBuffer sb = new StringBuffer();
            baseDatas.add(b);
            BaseQueryLeft bql = b.getQueryLeftNewDTO();
            Map<String,String> m2 = new HashMap<String, String>();

            for (String s : CommunalData.SELECTED_TRAIN_CODE_SET){
                if (bql.getStation_train_code().equals(s)){
                    CommunalData.DO_TICKET_BASEDATA_LIST.add(b);
                }
            }
            Log.v(TAG,"BASEDATA LIST SIZE " + CommunalData.DO_TICKET_BASEDATA_LIST.size());
            if (bql.getStation_train_code().equals(CommunalData.c1) || bql.getStation_train_code().equals(CommunalData.c2)) {
                ShowQueryResult.b1 = b;
            }
            Map<String, String> m = new HashMap<String, String>();


            if (!bql.getYz_num().equals("--")) {
                sb.append("硬座" + ">" + bql.getYz_num() + ",");
                m2.put("硬座","" + bql.getYz_num());
            }
            if (!bql.getRz_num().equals("--")) {
                sb.append("软座" + ">" + bql.getRz_num() + ",");
                m2.put("软座","" + bql.getRz_num());
            }
            if (!bql.getYw_num().equals("--")) {
                sb.append("硬卧" + ">" + bql.getYw_num() + ",");
                m2.put("硬卧","" + bql.getYw_num());
            }

            if (!bql.getRw_num().equals("--")) {
                sb.append("软卧" + ">" + bql.getRw_num() + ",");
                m2.put("软卧","" + bql.getRw_num());
            }

            if (!bql.getGr_num().equals("--")) {
                sb.append("高级软卧" + ">" + bql.getGr_num() + ",");
                m2.put("高级软卧","" + bql.getGr_num());
            }

            if (!bql.getZy_num().equals("--")) {
                sb.append("一等座" + ">" + bql.getZy_num() + ",");
                m2.put("一等座","" + bql.getZy_num());
            }

            if (!bql.getZe_num().equals("--")) {
                sb.append("二等座" + ">" + bql.getZe_num() + ",");
                m2.put("二等座","" + bql.getZe_num());
            }

            if (!bql.getTz_num().equals("--")) {
                sb.append("特等座" + ">" + bql.getTz_num() + ",");
                m2.put("特等座","" + bql.getTz_num());
            }

            if (!bql.getSwz_num().equals("--")) {
                sb.append("商务座" + ">" + bql.getSwz_num() + ",");
                m2.put("商务座","" + bql.getSwz_num());
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
//            Log.v(TAG, "CODE" + bql.getStation_train_code());
            do_ticket_result_map.put(bql.getStation_train_code(),m2);
        }

        Log.v(TAG, "DO_TICKET_RESULT_MAP SIZE: " + do_ticket_result_map.size());
        CommunalData.DO_TICKET_RESULT_QUERY_MAP = do_ticket_result_map;
        Log.v(TAG, "DO_TICKET_RESULT_MAP: " + CommunalData.DO_TICKET_RESULT_QUERY_MAP);
        Log.v(TAG, "DO_TICKET_RESULT_MAP KEY SET: " + do_ticket_result_map.keySet());

    //    Log.v(TAG, "SIZE" + result.size());

        return result;
    }
}

package com.cheart.do12306.app.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.core.ClientCore;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.Passenger;
import com.cheart.do12306.app.domain.SubmitData;
import com.cheart.do12306.app.domain.TicketOrder;
import com.cheart.do12306.app.util.StringHelper;
import com.cheart.do12306.app.view.QueryActivity;
import com.cheart.do12306.app.view.ShowQueryResult;
import com.cheart.do12306.app.view.ShowTicketDetail;
import com.cheart.do12306.app.view.SubmitOrderActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.logging.LogRecord;

/**
 * Created by cheart on 4/27/2014.
 */
public class SubmitOrderTask extends AsyncTask<String, Integer, String> {

    public static final String autoSubmitOrderRequestUrl = "https://kyfw.12306.cn/otn/confirmPassenger/autoSubmitOrderRequest";
    public static final String checkUser = "https://kyfw.12306.cn/otn/login/checkUser";
    public static final String initDc = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
    public static final String getQueueCountAsyc = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCountAsync";
    public static final String getRandomCode = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand";
    public static final String checkRandomCodeAsync = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
    public static final String confirmSingleForQueueAsys = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueueAsys";
    public static final String queryOrderWaitTime = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?tourFlag=dc&_json_att=";
    private static final String TAG = "SubmitOrderTask";

    public static final int WHAT_GET_RANDOM_CODE = 1;
    public static boolean toSubmit = false;
    public static boolean toCheck = false;
    public static boolean randomCodeIsTrue = false;
    public static String orderId = "null";
    Context context;
    LayoutInflater li;
    View dialogView;
    public Handler handler;
    private EditText et_randomCode;
    private ImageView iv_randomCode;
    private Button bt_refush;
    private Button bt_ok;
    private Button bt_concel;

    private BaseData submitRequest;
    private SubmitData submitData;
    public static List<SubmitData> submitDataList = new ArrayList<SubmitData>();
    public static List<Passenger> passengerList = new ArrayList<Passenger>();
    private Passenger submitPassenger;
    private String key_check_isChange;
    private String result;
    private String randomCodeSubmitOrder;
    private Dialog dialogShowRandomCode;
    private ProgressDialog pd;
    private TicketOrder to = null;

    public SubmitOrderTask(Context context) {
        this.context = context;
    }

    protected void init() {
        initView();
        handler = new android.os.Handler() {


            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT_GET_RANDOM_CODE) {
                    Bitmap b = (Bitmap) msg.obj;
                    iv_randomCode.setImageBitmap(b);
                }

            }
        };
        bt_refush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrder();
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                randomCodeSubmitOrder = et_randomCode.getText().toString();
                toSubmit = true;
                toCheck = true;
                Log.v(TAG, "SET SUBMIT IS TRUE");

            }
        });

        bt_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowRandomCode.dismiss();
            }
        });


    }

    protected void initView() {
        li = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pd = new ProgressDialog(context);
        dialogView = li.inflate(R.layout.submit_order_random_code_dialog, null);
        dialogShowRandomCode = new AlertDialog.Builder(context).
                setTitle("验证码").setView(dialogView).create();
        dialogShowRandomCode.show();
        et_randomCode = (EditText) dialogView.findViewById(R.id.et_submit_randomCode);
        iv_randomCode = (ImageView) dialogView.findViewById(R.id.iv_submit_randomCode);
        bt_refush = (Button) dialogView.findViewById(R.id.bt_submit_refush);
        bt_ok = (Button) dialogView.findViewById(R.id.bt_submit_ok);
        bt_concel = (Button) dialogView.findViewById(R.id.bt_submit_cancel);
    }

    @Override
    protected void onPreExecute() {
        init();
        passengerList = CommunalData.p;
        to = new TicketOrder();
        to.setBaseData(ShowQueryResult.b1);
        to.setBaseQueryLeft(ShowQueryResult.b1.getQueryLeftNewDTO());
        to.setPassenger(CommunalData.p.get(0));
        to.setSeatType("硬卧");
        Log.v(TAG, "serae" + to.getBaseData().getSecretStr());
        Log.v(TAG, "1" + to.getBaseData().getButtonTextInfo());
        Log.v(TAG,"date" + to.getBaseQueryLeft().getStart_train_date());
        Log.v(TAG, "2" + to.getBaseQueryLeft().getStation_train_code());
        Log.v(TAG,"3" + to.getPassenger().getPassenger_name());
//        to.setBaseData();


//        for (Iterator<String> it = SubmitOrderActivity.TICKET_INFO_MAP.keySet().iterator();
//             it.hasNext();){
//            to = (TicketOrder) SubmitOrderActivity.TICKET_INFO_MAP.get(it.next());
//            Log.v(TAG , "finally submit" + to.getName() + to.getSeatType());
//        }


        submitData = new SubmitData();
        submitRequest = new BaseData();
        submitRequest = to.getBaseData();
        randomCodeSubmitOrder = "";
        submitPassenger = to.getPassenger();

        initSubmitData();

    }

    @Override
    protected void onPostExecute(String s) {
        pd.dismiss();
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {

       /* while (toSubmit){

            submitOrder();

            toSubmit = false;
            break;
        }*/

        submitOrder();


        return null;
    }


    public boolean submitOrder() {

        SubmitData submitData = new SubmitData();
        boolean result = false;
        new Thread(new SubmitOrderThread()).start();
        return result;
    }

    private String queryOrderWaitTime() {
        String result = "";
        result = MainActivity.core
                .getRequest(context, queryOrderWaitTime, null, HttpsHeader.checkOrder(),
                        null, false);

        Log.v(TAG, "queryOrderWaitTime" + result);

        return result;
    }

    private String confirmSingleForQueueAsync() {
        String result = "";

        Map<String, String> paramsConfirmSingleForQueueAsys = new HashMap<String, String>();
        paramsConfirmSingleForQueueAsys.put("passengerTicketStr",
                getPassengerTicketStr());
        paramsConfirmSingleForQueueAsys.put("oldPassengerStr",
                getOldPassengerStr());
        paramsConfirmSingleForQueueAsys.put("randCode", randomCodeSubmitOrder);
        paramsConfirmSingleForQueueAsys.put("purpose_codes", "0X00");//!!!!
        paramsConfirmSingleForQueueAsys.put("key_check_isChange",
                key_check_isChange);
        paramsConfirmSingleForQueueAsys.put("leftTicketStr",
                submitRequest.getQueryLeftNewDTO().getYp_info());
        paramsConfirmSingleForQueueAsys.put("train_location",
                submitRequest.getQueryLeftNewDTO().getLocation_code());
        paramsConfirmSingleForQueueAsys.put("_json_att", "");
        result = MainActivity.core.postRequest(context,
                confirmSingleForQueueAsys, paramsConfirmSingleForQueueAsys,
                HttpsHeader.tiketInit(), null, false, false);
        return result;
    }

    private String autoSubmitOrderRequest() {
        String result = "";
        Map<String, String> paramsAutoSubmitOrderRequest = new HashMap<String, String>();
        String secretStr = "";
        secretStr = StringHelper.formAtSecretStr(submitRequest.getSecretStr());
        System.out.println(secretStr);
        System.out.println(submitRequest.getQueryLeftNewDTO()
                .getStation_train_code());
        paramsAutoSubmitOrderRequest.put("secretStr", secretStr);
        paramsAutoSubmitOrderRequest.put("train_date", to.getDate());
        paramsAutoSubmitOrderRequest.put("tour_flag", "dc");
        paramsAutoSubmitOrderRequest.put("purpose_codes", "0X00");////
        Log.v(TAG, "from" + submitRequest.getQueryLeftNewDTO().getFrom_station_name());
        paramsAutoSubmitOrderRequest.put("query_from_station_name",
                submitRequest.getQueryLeftNewDTO().getFrom_station_name());//
        Log.v(TAG, "to" + submitRequest.getQueryLeftNewDTO().getTo_station_name());
        paramsAutoSubmitOrderRequest.put("query_to_station_name", submitRequest
                .getQueryLeftNewDTO().getTo_station_name());//
        paramsAutoSubmitOrderRequest.put("cancel_flag", "2");
        paramsAutoSubmitOrderRequest.put("bed_level_order_num",
                "000000000000000000000000000000");
        Log.v(TAG, "" + getPassengerTicketStr());
        paramsAutoSubmitOrderRequest.put("passengerTicketStr",
                getPassengerTicketStr());//

        Log.v(TAG, "222" + getOldPassengerStr());
        paramsAutoSubmitOrderRequest.put("oldPassengerStr",
                getOldPassengerStr());
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("JSESSIONID", ClientCore.JSESSIONID);
        cookies.put("BIGipServerotn", ClientCore.BIGipServerotn);
        result = MainActivity.core.postRequest(context, autoSubmitOrderRequestUrl,
                paramsAutoSubmitOrderRequest, HttpsHeader.submitOrder(), cookies,
                false, false);
        key_check_isChange = parserOrderParamsFromAutoSubmit(result).get("key_check_isChange");

        return result;
    }

    public String getPassengerTicketStr() {

        String result = "";
        String interval = ",";
        StringBuffer sb = new StringBuffer();
        int c = 0;
        for (int i = 0; i < submitDataList.size(); i++){
            SubmitData submitData = submitDataList.get(i);

            c++;
            sb.append(
                  //  CommunalData.getSEAT_TYPE_MAP().get(to.getSeatType())
                    CommunalData.SEAT_TYPE_MAP.get(CommunalData.t) + interval + submitData.getConstStrig() + interval
                            + "3" + interval
                            + submitData.getName() + interval
                            + submitData.getPassengerIdTypeCode() + interval
                            + submitData.getPassengerIdNo() + interval
                            + submitData.getMobileNo() + interval + submitData.getSave() + "_"
            );
        }
        Log.v(TAG, "CI" + c);
        sb.delete(sb.length() - 1,sb.length());
        result = sb.toString();
       // Log.v(TAG, "TICKET_STR" + result);
      //  Log.v(TAG, "pass!!!----" + result);

        return result;
    }

    public String getOldPassengerStr() {
        String result = "";
        String interval = ",";
        StringBuffer sb = new StringBuffer();
        int c = 0;
        Log.v(TAG, submitDataList.size() + "======");
        for (int i = 0; i < submitDataList.size(); i++){
            SubmitData submitData = submitDataList.get(i);
            c++;
            sb.append(

                    submitData.getName() + interval + submitData.getPassengerIdTypeCode()
                            + interval + submitData.getPassengerIdNo() + interval
                            + submitData.getPassengerType() + "_"
            );
        }
        Log.v(TAG, "OCI" + c);
        result = sb.toString();

     //   Log.v(TAG, "ord!!!----" + result);

        return result;
    }

    public void initSubmitData() {

        Log.v(TAG, "intiDataSize" + passengerList.size());

        for (int i = 0; i < passengerList.size(); i++){
           SubmitData s = new SubmitData();
            Passenger p = passengerList.get(i);
            s.setSeatTypeCode(CommunalData.getSEAT_TYPE_MAP().get(to.getSeatType()));// seat type
            s.setTicketTypeCode(CommunalData.getTICKET_TYPE().get(to.getTicketType()));// ticket type
            s.setName(p.getPassenger_name());
            s.setPassengerIdTypeCode(p
                    .getPassenger_id_type_code());
            s.setPassengerIdNo(p.getPassenger_id_no());
            s.setMobileNo(p.getPhone_no());
            s.setPassengerType(p.getPassenger_type());
            s.setSave("N");

            submitDataList.add(s);
        }

        Log.v(TAG,"sssize" + submitDataList.size());



        submitData.setSeatTypeCode(CommunalData.getSEAT_TYPE_MAP().get(to.getSeatType()));// seat type
        submitData.setTicketTypeCode(CommunalData.getTICKET_TYPE().get(to.getTicketType()));// ticket type
        submitData.setName(submitPassenger.getPassenger_name());
        submitData.setPassengerIdTypeCode(submitPassenger
                .getPassenger_id_type_code());
        submitData.setPassengerIdNo(submitPassenger.getPassenger_id_no());
        submitData.setMobileNo(submitPassenger.getPhone_no());
        submitData.setPassengerType(submitPassenger.getPassenger_type());
        submitData.setSave("N");

    }

    private Map<String, String> parserOrderParamsFromAutoSubmit(String result) {
        Map<String, String> resultMap = new HashMap<String, String>();
        System.out.println("autoResult" + result);
        JsonObject object = new JsonParser().parse(result).getAsJsonObject();
        String parserStr = object.get("data").getAsJsonObject().get("result")
                .getAsString();

        System.out.println(parserStr);
        String[] paramsArray = parserStr.split("#");
        System.out.println(paramsArray);
        resultMap.put("location_code", paramsArray[0]);
        resultMap.put("key_check_isChange", paramsArray[1]);
        resultMap.put("leftTicketStr", paramsArray[2]);
        resultMap.put("seatType", paramsArray[3]);
        return resultMap;
    }

    private String parderOrderIdFromWaitTime(String str) {
        String result = "";
        JsonObject obj = new JsonParser().parse(str).getAsJsonObject().get("data").getAsJsonObject();
        Log.v(TAG, "dataobjtoString" + obj.get("orderId").toString());

        if(obj.get("orderId").toString().equals("null") || obj.get("orderId").equals("")){
            result = "null";
        } else{
            result = obj.get("orderId").getAsString();
        }



       // result = obj.get("orderId") == null || obj.get("orderId").equals("") ? "null" : obj.get("orderId").getAsString();

        return result;
    }

    public String getQueueCountAsync() {
        String result = "";
        Map<String, String> paramsGetQueueCountAsync = new HashMap<String, String>();
        paramsGetQueueCountAsync.put("train_date",
                "Fri Apr 27 19:17:19 UTC+0800 2014");
        paramsGetQueueCountAsync.put("train_no",
                submitRequest.getQueryLeftNewDTO().getTrain_no());
        paramsGetQueueCountAsync.put("stationTrainCode",
                submitRequest.getQueryLeftNewDTO().getStation_train_code());
        paramsGetQueueCountAsync.put("seatType", "1");
        paramsGetQueueCountAsync.put("fromStationTelecode",
                submitRequest.getQueryLeftNewDTO().getFrom_station_name());
        paramsGetQueueCountAsync.put("toStationTelecode",
                submitRequest.getQueryLeftNewDTO().getTo_station_name());
        paramsGetQueueCountAsync.put("leftTicket",
                submitRequest.getQueryLeftNewDTO().getYp_info());
        paramsGetQueueCountAsync.put("purpose_codes", "ADULT");
        paramsGetQueueCountAsync.put("_json_att", "");
        result = MainActivity.core.postRequest(context,
                getQueueCountAsyc, paramsGetQueueCountAsync,
                HttpsHeader.submitOrder(), null, false, false);


        return result;
    }

    public String getSubmitRadomCode() {
        String result = "";

        new Thread(new GetRandomCodeThread()).start();

        return result;
    }


    class GetRandomCodeThread implements Runnable {

        @Override
        public void run() {

            Bitmap bitmap = BitmapFactory.decodeStream(MainActivity.core.getPicInputStream(
                    context,
                    MainActivity.core.getHttpGet(
                            getRandomCode, null
                    )
            ));

            Message msg = handler.obtainMessage();
            msg.obj = bitmap;
            msg.what = WHAT_GET_RANDOM_CODE;
            handler.sendMessage(msg);
        }
    }


    class SubmitOrderThread implements Runnable {

        @Override
        public void run() {


            Log.v(TAG, "autoSubmitOrderRequest" + autoSubmitOrderRequest());
            Log.v(TAG, "getQueueCountAsync" + getQueueCountAsync());
            String resultCheckRandom = "";
            resultCheckRandom = new JsonParser().parse(checkSubmitRandomCode()).
                    getAsJsonObject().get("data").getAsString();
            if (resultCheckRandom.equals("N")) {
                randomCodeIsTrue = false;
                toCheck = false;
                pd.dismiss();
            } else if (resultCheckRandom.equals("Y")){
                randomCodeIsTrue = true;
            }
            while (!randomCodeIsTrue) {
                run();
            }

            dialogShowRandomCode.dismiss();
            Log.v(TAG, "confirmSingleForQueueAsync" + confirmSingleForQueueAsync());
            orderId = parderOrderIdFromWaitTime(queryOrderWaitTime());
            while (orderId.equals("null")) {
                orderId = parderOrderIdFromWaitTime(queryOrderWaitTime());
            }
            Log.v(TAG, "maybe success!");
            pd.dismiss();

        }
    }

    public String checkSubmitRandomCode() {
        String result = "";
        Bitmap bitmap = BitmapFactory.decodeStream(MainActivity.core.getPicInputStream(
                context,
                MainActivity.core.getHttpGet(
                        getRandomCode, null
                )
        ));

        Message msg = handler.obtainMessage();
        msg.obj = bitmap;
        msg.what = WHAT_GET_RANDOM_CODE;
        handler.sendMessage(msg);
        while (!toCheck) {


        }

        Map<String, String> paramseCheckRandomAsync = new HashMap<String, String>();
        Log.v(TAG, "fianl randomCode" + randomCodeSubmitOrder);
        paramseCheckRandomAsync.put("randCode", randomCodeSubmitOrder);
        paramseCheckRandomAsync.put("rand", "sjrand");
        paramseCheckRandomAsync.put("_json_att", "");

        result = MainActivity.core.postRequest(context,
                checkRandomCodeAsync, paramseCheckRandomAsync,
                HttpsHeader.postCheckCode(false), null, false, false);
        Log.v(TAG, "getRandomCodeSubmitOrder and check" + result);



        return result;
    }
}

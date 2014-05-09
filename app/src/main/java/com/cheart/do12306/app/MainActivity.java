package com.cheart.do12306.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheart.do12306.app.core.ClientCore;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.Passenger;
import com.cheart.do12306.app.view.QueryActivity;
import com.cheart.do12306.app.view.SubmitOrderActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    public static ClientCore core = null;
    public static final String INDEX = "https://kyfw.12306.cn/otn/";
    public static final String GET_RANDOM_NEW = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand";
    public static final String POST_CHECK_RANDOM_CODE = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
    public static final String POST_LOGIN_ASYNC_SUGGEST = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";
    public static final String USER_LOGIN = "https://kyfw.12306.cn/otn/login/userLogin";
    public static List<Passenger> PASSENGERS = null;
    public static Map<String, Integer> PASSENGERS_MAP = null;

    public static Map<String, String> SEAT_TYPE_MAP;
    public static Map<String, String> TICKET_TYPE;


    public static final int WHAT_GET_RANDOM_CODE = 1;
    private static final int WHAT_LOGIN = 2;
    private static final int ARG_1_RANDOM_CODE_ERROR = 0;
    private static final int ARG_1_RANDOM_PASSWORD_ERROR = 1;
    public EditText et_userName;
    public EditText et_password;

    public Button bt_login;
    public Handler handler;

    private static boolean success = false;
    private static boolean isAliveLoginThread = false;

    private LayoutInflater li;
    private Bitmap bitmapRandomCode;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        core = new ClientCore();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        PASSENGERS_MAP = new HashMap<String, Integer>();
        initView();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT_GET_RANDOM_CODE) {
                    bitmapRandomCode = (Bitmap) msg.obj;
                    Log.v(TAG, bitmapRandomCode + "");

                }

            }
        };
        TICKET_TYPE = new HashMap<String, String>();
        SEAT_TYPE_MAP = new HashMap<String, String>();
        SEAT_TYPE_MAP.put("二等座", "O");
        SEAT_TYPE_MAP.put("一等座", "M");
        SEAT_TYPE_MAP.put("特等座", "P");
        SEAT_TYPE_MAP.put("硬座","1");
        SEAT_TYPE_MAP.put("软座","2");
        SEAT_TYPE_MAP.put("硬卧","3");
        SEAT_TYPE_MAP.put("软卧","4");
        SEAT_TYPE_MAP.put("高级软卧","6");
        SEAT_TYPE_MAP.put("商务座","9");


        TICKET_TYPE.put("成人", "1");
        TICKET_TYPE.put("孩票", "2");
        TICKET_TYPE.put("学生", "3");
        TICKET_TYPE.put("伤残军人票", "4");


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "click");
                new LoginTask(MainActivity.this).execute(et_userName.getText().toString(),
                        et_password.getText().toString());

            }
        });
     /*   bt_refush_randomCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new GetRandomCodeThread()).start();
            }
        });*/

       /* handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT_GET_RANDOM_CODE) {
                    bitmapRandomCode = (Bitmap) msg.obj;
                    iv_randomCode.setImageBitmap(bitmapRandomCode);
                    Log.v(TAG, bitmapRandomCode + "");

                }

            }
        };*/

    }

    public void initView() {
        li = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        et_userName = (EditText) findViewById(R.id.et_main_username);
        et_password = (EditText) findViewById(R.id.et_main_password);
        bt_login = (Button) findViewById(R.id.bt_main_login);


    }

    public class LoginTask extends AsyncTask<String, Integer, String> {

        public static final String TAG = "LoginTask";
        private Context context;
        private ProgressDialog pd;
        public Handler handler;
        private AlertDialog dialogLogin;

        private View dialogView;
        public EditText et_randomCode;
        public ImageView iv_randomCode;
        public Button bt_refush;
        public Button bt_ok;
        public Button bt_cancel;
        public Listener listener;
        public boolean toLogin = false;
        public boolean finishedLogin = false;
        private String userName;
        private String password;

        public LoginTask() {

        }

        public LoginTask(Context context) {
            this.context = context;
        }

        protected void init() {
            listener = new Listener();
            initView();

            new Thread(new GetRandomCodeThread()).start();
        }

        protected void initView() {
            dialogView = li.inflate(R.layout.activity_main_dialog_random_code, null);
            iv_randomCode = (ImageView) dialogView.findViewById(R.id.iv_main_dialog_randomCode);
            et_randomCode = (EditText) dialogView.findViewById(R.id.et_main_dialog_randomCode);
            bt_refush = (Button) dialogView.findViewById(R.id.bt_main_dialog_refush);
            bt_ok = (Button) dialogView.findViewById(R.id.bt_main_dialog_ok);
            bt_cancel = (Button) dialogView.findViewById(R.id.bt_main_dialog_cancel);
            bt_refush.setOnClickListener(listener);
            bt_ok.setOnClickListener(listener);
            bt_cancel.setOnClickListener(listener);

        }

        @Override
        protected void onPreExecute() {
            init();

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    switch (msg.what) {
                        case WHAT_GET_RANDOM_CODE:
                            Bitmap b = (Bitmap) msg.obj;
                            iv_randomCode.setImageBitmap(b);
                            break;
                        case WHAT_LOGIN:
                            // CHECK ERROR !!!
                            if (msg.arg1 == ARG_1_RANDOM_CODE_ERROR) {
                                Log.v(TAG, "CHECK RANDOM_CODE ERROR:");
                                pd.dismiss();
                                Toast.makeText(context, "random code is error!", Toast.LENGTH_LONG);
                              //  doInBackground(userName, password);
                            } else if (msg.arg1 == ARG_1_RANDOM_PASSWORD_ERROR) {
                                Log.v(TAG, "PASSWORD ERROR:");
                                pd.dismiss();
                                Toast.makeText(context, "password is error!", Toast.LENGTH_LONG);

                            }
                    }

                }
            };
            pd = new ProgressDialog(context);
            pd.show();


            dialogLogin = new AlertDialog.Builder(context).setView(dialogView).create();
            Log.v(TAG, "IV" + iv_randomCode);
            dialogLogin.setInverseBackgroundForced(false);
            dialogLogin.show();


            pd.dismiss();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v(TAG, "finally ok!!");

        }

        @Override
        protected String doInBackground(String... strings) {
            userName = strings[0];
            password = strings[1];
            int num = 0;
            Log.v(TAG, "Do InBackground!");

            while (!success) {


                while (!toLogin) {

                }

                login(strings[0], strings[1]);

                while (!finishedLogin) {

                }


                pd.dismiss();
                Log.v(TAG, "result for login" + success);
                if (!success) {
                    Message msg = handler.obtainMessage();
                    msg.what = WHAT_LOGIN;
                    msg.arg1 = ARG_1_RANDOM_CODE_ERROR;
                    handler.sendMessage(msg);
                    new Thread(new GetRandomCodeThread()).start();
                }

            }

            return null;
        }

        public void login(String userName, String password) {
            LoginThread lt = new LoginThread(userName, password, et_randomCode.getText().
                    toString());
            Thread t = new Thread(lt);
            t.start();

            while (t.isAlive()) {

            }
            Log.v(TAG, "DEAD!!!!!!!!!");
            finishedLogin = true;


        }

        class Listener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_main_dialog_ok:
                        Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                        context.startActivity(intent);
                        pd.show();
                        toLogin = true;
                        imm.hideSoftInputFromWindow(et_randomCode.getWindowToken(),0);
                        break;
                    case R.id.bt_main_dialog_cancel:
                        dialogLogin.dismiss();
                        break;
                    case R.id.bt_main_dialog_refush:
                        new Thread(new GetRandomCodeThread()).start();
                        break;
                }
            }
        }

        class LoginThread implements Runnable {
            private static final String TAG = "LoginThread";
            private static final int RETURN_LOGIN_SUCCESS = 0;
            private static final int RETURN_LOGIN_RANDOM_CODE_ERROR = 1;
            private static final int RETURN_LOGIN_PASSWORD_ERROR = 2;
            String userName;
            String password;
            String randomCode;

            private volatile boolean stopRequest = false;
            private Thread runThread;

            LoginThread(String userName, String password, String randomCode) {
                this.userName = "jhai2391liu";//userName;
                this.password = "aiing1391liujh";//password;
                this.randomCode = randomCode;
            }


            @Override
            public void run() {
                boolean interrupted = false;
                runThread = Thread.currentThread();


                switch (login()) {
                    case RETURN_LOGIN_RANDOM_CODE_ERROR:

                        success = false;

                        /*while (!interrupted){
                            interrupted = Thread.interrupted();
                            Log.v(TAG, "" + interrupted);

                        }*/
                        stopThread();
                        break;


                    case RETURN_LOGIN_PASSWORD_ERROR:

                        success = false;
                        stopThread();

                        break;
                    case RETURN_LOGIN_SUCCESS:
                        Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                }
            }

            private void stopThread() {
                stopRequest = true;
                if (runThread != null) {
                    runThread.interrupt();
                }
            }

            public int login() {
                toLogin = false;
                Log.v(TAG, "randomCode" + randomCode);
                // check random code
                Map<String, String> paramsCheckRandCode = new HashMap<String, String>();
                paramsCheckRandCode.put("rand", "sjrand");
                paramsCheckRandCode.put("randCode", randomCode);
                String resultCheckRandomCode = core.postRequest(
                        context, POST_CHECK_RANDOM_CODE,
                        paramsCheckRandCode, HttpsHeader.postCheckCode(true), null,
                        false, false);
                Log.v(TAG, "check rand code" + resultCheckRandomCode);
                if (new JsonParser().parse(resultCheckRandomCode).getAsJsonObject().get("data").
                        getAsString().equals("N")) {

                    Message msg = handler.obtainMessage();
                    msg.what = WHAT_LOGIN;
                    msg.arg1 = ARG_1_RANDOM_CODE_ERROR;
                    handler.sendMessage(msg);

                    return RETURN_LOGIN_RANDOM_CODE_ERROR;

                }

                Map<String, String> paramsLoginAsynSuggest = new HashMap<String, String>();
                paramsLoginAsynSuggest.put("loginUserDTO.user_name", userName);
                paramsLoginAsynSuggest.put("userDTO.password", password);
                paramsLoginAsynSuggest.put("randCode", randomCode);
                String resultLoginAsynSuggest = core.postRequest(
                        context, POST_LOGIN_ASYNC_SUGGEST,
                        paramsLoginAsynSuggest, HttpsHeader.loginInitHearder(), null,
                        false, false);
                Log.v(TAG, "login asyn sugbn6kgest" + resultLoginAsynSuggest);

                JsonObject obj = new JsonParser().parse(resultLoginAsynSuggest).getAsJsonObject().
                        get("data").getAsJsonObject();
                Log.v(TAG, "OBJ is" + obj);
                Log.v(TAG, "new json obj is:" + new JsonObject());
                Log.v(TAG, "new json obj toString is" + obj.toString());
                if (obj.toString().equals("{}")){
                    Log.v(TAG, "accpet null!!!");
                    return RETURN_LOGIN_PASSWORD_ERROR;
                } else {
                    if (new JsonParser().parse(resultLoginAsynSuggest).getAsJsonObject().get("data").
                            getAsJsonObject().get("loginCheck").getAsString().equals("Y")) {

                        success = true;
                    } else {

                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_LOGIN;
                        msg.arg1 = ARG_1_RANDOM_PASSWORD_ERROR;
                        handler.sendMessage(msg);
                      //  new Thread(new GetRandomCodeThread()).start();
                        return RETURN_LOGIN_PASSWORD_ERROR;
                    }
                }


                Map<String, String> paramsUserLogin = new HashMap<String, String>();
                paramsUserLogin.put("_json_att", "");
                String resultUserLogin = core.postRequest(
                        context, USER_LOGIN, paramsUserLogin,
                        HttpsHeader.login(), null, false, false);
                getPassenger();
                finishedLogin = true;

                return RETURN_LOGIN_SUCCESS;
            }

            public void getPassenger() {

                String getPassengerDTDs = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
                String resultGetPassengerDTDs = MainActivity.core.postRequest(context,
                        getPassengerDTDs,
                        new HashMap<String, String>(), HttpsHeader.initDc(),
                        null,
                        true,
                        false);
                System.out.println("resultGetPassengerDTDs" + resultGetPassengerDTDs);
                JsonObject object = new JsonParser().parse(resultGetPassengerDTDs)
                        .getAsJsonObject();
                JsonArray objects = object.get("data").getAsJsonObject()
                        .get("normal_passengers").getAsJsonArray();
                PASSENGERS = new Gson().fromJson(objects,
                        new TypeToken<List<Passenger>>() {
                        }.getType()
                );
                initPassengerMap(PASSENGERS);
                Log.v(TAG, "PASSENGER" + PASSENGERS.get(1).getPassenger_name());


            }

            public void initPassengerMap(List<Passenger> list) {

                for (int i = 0; i < list.size(); i++){
                    PASSENGERS_MAP.put(list.get(i).getPassenger_name(), i);
                }

            }


        }

        class GetRandomCodeThread implements Runnable {

            @Override
            public void run() {
                if (core.isNullCookies(core)) {
                    core.getCookie(MainActivity.this, INDEX, null, null);
                }
                Log.v(TAG, "JSESSIONID" + core.JSESSIONID);
                Log.v(TAG, "BIGipServerotn" + core.BIGipServerotn);
                Bitmap bitmap = BitmapFactory.decodeStream(core.getPicInputStream(
                        context,
                        core.getHttpGet(
                                GET_RANDOM_NEW, null
                        )
                ));
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = WHAT_GET_RANDOM_CODE;
                handler.sendMessage(msg);
            }
        }


    }
}

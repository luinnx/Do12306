package com.cheart.do12306.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cheart.do12306.app.core.ClientCore;
import com.cheart.do12306.app.core.HttpsHeader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    ClientCore core = null;
    public static final String INDEX = "https://kyfw.12306.cn/otn/";
    public static final String GET_RANDOM_NEW = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand";
    public static final String POST_CHECK_RANDOM_CODE = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
    public static final String POST_LOGIN_ASYNC_SUGGEST = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";
    public static final String USER_LOGIN = "https://kyfw.12306.cn/otn/login/userLogin";




    public static final int WHAT_GET_RANDOM_CODE = 1;
    public EditText et_userName;
    public EditText et_password;
    public EditText et_randomCode;
    public ImageView iv_randomCode;
    public Button bt_login;
    public Button bt_refush_randomCode;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        core = new ClientCore();
        initView();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "click");
                login(et_userName.getText().toString(),
                        et_password.getText().toString(),
                        et_randomCode.getText().toString()
                );
            }
        });
        bt_refush_randomCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new GetRandomCodeThread()).start();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT_GET_RANDOM_CODE) {
                    Bitmap b = (Bitmap) msg.obj;
                    iv_randomCode.setImageBitmap(b);
                }

            }
        };

        new Thread(new GetRandomCodeThread()).start();
    }

    public void initView() {
        et_userName = (EditText) findViewById(R.id.et_main_username);
        et_password = (EditText) findViewById(R.id.et_main_password);
        et_randomCode = (EditText) findViewById(R.id.et_main_random_code);
        iv_randomCode = (ImageView) findViewById(R.id.iv_main_randomCode);
        bt_login = (Button) findViewById(R.id.bt_main_login);
        bt_refush_randomCode = (Button) findViewById(R.id.bt_main_refush_randomCode);

    }

    public void login(String randomCode, String userName, String password) {
        LoginThread t = new LoginThread(randomCode, userName, password);
        new Thread(t).start();
    }

    class LoginThread implements Runnable{
        String userName;
        String password;
        String randomCode;
        LoginThread(String userName, String password, String randomCode) {
            this.userName = userName;
            this.password = password;
            this.randomCode = randomCode;
        }


        @Override
        public void run() {
            login();
        }

        public void login() {
            Log.v(TAG, "randomCode" + randomCode);
            // check random code
            Map<String, String> paramsCheckRandCode = new HashMap<String, String>();
            paramsCheckRandCode.put("rand", "sjrand");
            paramsCheckRandCode.put("randCode", randomCode);
            String resultCheckRandomCode = core.postRequest(
                    MainActivity.this,POST_CHECK_RANDOM_CODE,
                    paramsCheckRandCode, HttpsHeader.postCheckCode(true), null,
                    false, false);
            Log.v(TAG, "check rand code" + resultCheckRandomCode);

            Map<String, String> paramsLoginAsynSuggest = new HashMap<String, String>();
            paramsLoginAsynSuggest.put("loginUserDTO.user_name", userName);
            paramsLoginAsynSuggest.put("userDTO.password", password);
            paramsLoginAsynSuggest.put("randCode", randomCode);
            String resultLoginAsynSuggest = core.postRequest(
                    MainActivity.this, POST_LOGIN_ASYNC_SUGGEST,
                    paramsLoginAsynSuggest, HttpsHeader.loginInitHearder(), null,
                    false, false);
            Log.v(TAG, "login asyn sugbn6kgest" + resultLoginAsynSuggest);
            Map<String, String> paramsUserLogin = new HashMap<String, String>();
            paramsUserLogin.put("_json_att", "");
            String resultUserLogin = core.postRequest(
                    MainActivity.this, USER_LOGIN, paramsUserLogin,
                    HttpsHeader.login(), null, false, false);


        }
    }


    class GetRandomCodeThread implements Runnable {

        @Override
        public void run() {
            if (core.isNullCookies(core)) {
                core.getCookie(MainActivity.this, INDEX , null, null);
            }
            Log.v(TAG, "JSESSIONID" + core.JSESSIONID);
            Log.v(TAG, "BIGipServerotn" + core.BIGipServerotn);
            Bitmap bitmap = BitmapFactory.decodeStream(core.getPicInputStream(
                    MainActivity.this,
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

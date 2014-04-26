package com.cheart.do12306.app.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.task.QueryTicketTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryActivity extends Activity {


    private static final String TAG = "QueryActivity";
    public static Map<String, String> stationsMap;
    private EditText et_from;
    private EditText et_to;
    private EditText et_date;
    private Button bt_submit;

    public static List<Map<String, String>> QUERY_RESULT_LIST = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        init();
        Log.v(TAG, stationsMap + "");


    }

    public void init() {

        stationsMap = new HashMap<String, String>();
        QUERY_RESULT_LIST = new ArrayList<Map<String, String>>();
        initView();
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QueryTicketTask(QueryActivity.this).execute(new String[]{
                        stationsMap.get(et_from.getText().toString()),
                        stationsMap.get(et_to.getText().toString()),
                        et_date.getText().toString()
                });


            }
        });

        LoadStationsTask loadStationsTask = new LoadStationsTask();
        loadStationsTask.execute("");

    }

    public void loadAllStation() {

        AssetManager am = getAssets();
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = am.open("stations");
            br = new BufferedReader(new InputStreamReader(in));
            String stationsStr = br.readLine();
            String[] stationsArray = stationsStr.split("@");

            for (int i = 0; i < stationsArray.length; i++) {
                if (i != 0 && i != stationsArray.length) {

                    String[] stationArray = stationsArray[i].split("\\|");
                    String chineseName = "";
                    String code = "";
                    for (int j = 0; j < stationArray.length; j++) {
                        if (j == 1) {
                            chineseName = stationArray[1];
                        } else if (j == 2) {
                            code = stationArray[2];
                        }
                    }
                    stationsMap.put(chineseName, code);

                } else {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initView() {
        et_from = (EditText) findViewById(R.id.et_query_from);
        et_to = (EditText) findViewById(R.id.et_query_to);
        et_date = (EditText) findViewById(R.id.et_query_date);
        bt_submit = (Button) findViewById(R.id.bt_query_submit);
    }

    class LoadStationsTask extends AsyncTask<String, Integer, String> {

        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(QueryActivity.this);
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            AssetManager am = getAssets();
            InputStream in = null;
            BufferedReader br = null;
            try {
                in = am.open("stations");
                br = new BufferedReader(new InputStreamReader(in));
                String stationsStr = br.readLine();
                String[] stationsArray = stationsStr.split("@");

                for (int i = 0; i < stationsArray.length; i++) {
                    if (i != 0 && i != stationsArray.length) {

                        String[] stationArray = stationsArray[i].split("\\|");
                        String chineseName = "";
                        String code = "";
                        for (int j = 0; j < stationArray.length; j++) {
                            if (j == 1) {
                                chineseName = stationArray[1];
                            } else if (j == 2) {
                                code = stationArray[2];
                            }
                        }
                        stationsMap.put(chineseName, code);

                    } else {

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

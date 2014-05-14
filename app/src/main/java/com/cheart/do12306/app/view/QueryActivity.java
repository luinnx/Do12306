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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.domain.Passenger;
import com.cheart.do12306.app.task.QueryTicketTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryActivity extends Activity {


    private static final String TAG = "QueryActivity";
    public static Map<String, String> stationsMap;
    private AutoCompleteTextView aet_from;
    private AutoCompleteTextView aet_to;
    private ProgressDialog pd;
    private Spinner sp_date;
    private Button bt_submit;
    public static String[] STATION_ARRAY = null;
    public static String SELECTED_DATE = "";
    public static String SELECT_DATE_PARSERED = "";

    public static List<Map<String, String>> QUERY_RESULT_LIST = null;
    public static String CAN_BY_DATE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        pd = new ProgressDialog(this);
        pd.show();
        init();
        loadAllStation();
        Log.v(TAG, stationsMap + "");
        pd.dismiss();

    }

    public void init() {
        STATION_ARRAY = new String[]{};
        stationsMap = new HashMap<String, String>();
        QUERY_RESULT_LIST = new ArrayList<Map<String, String>>();
        loadAllStation();
        initView();
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_DATE_PARSERED = parserDate(SELECTED_DATE);
                new QueryTicketTask(QueryActivity.this).execute(new String[]{
                        stationsMap.get(aet_from.getText().toString()),
                        stationsMap.get(aet_to.getText().toString()),
                        SELECT_DATE_PARSERED
                });


            }
        });


    }

    public String parserDate(String str) {
        String result = "";
        String[] arr1 = str.split("年");
        StringBuffer sb = new StringBuffer();
        sb.append(arr1[0] + "-");
        String[] arr2 = arr1[1].split("月");
        sb.append(arr2[0] + "-");
        String[] arr3 = arr2[1].split("日");
        sb.append(arr3[0]);
        result = sb.toString();
        Log.v(TAG , sb.toString());
        return result;
    }

    public void loadAllStation() {

        AssetManager am = getAssets();
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer sb_name = null;
        try {
            in = am.open("stations");
            br = new BufferedReader(new InputStreamReader(in));
            sb_name = new StringBuffer();
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
                    sb_name.append(chineseName + ",");
                    stationsMap.put(chineseName, code);

                } else {

                }
            }

            STATION_ARRAY = sb_name.toString().split(",");
            Log.v(TAG, "STATION_ARRAY" + STATION_ARRAY);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initView() {
        aet_from = (AutoCompleteTextView) findViewById(R.id.et_query_from);
        aet_to = (AutoCompleteTextView) findViewById(R.id.et_query_to);
        sp_date = (Spinner) findViewById(R.id.sp_query_date);
        sp_date.setAdapter(new ArrayAdapter<String>(
                QueryActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                MainActivity.CAN_BUY_DATE.split(",")
        ));
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_DATE = MainActivity.CAN_BUY_DATE.split(",")[i];
                Log.v(TAG, "SELECTED_DATE" + SELECTED_DATE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bt_submit = (Button) findViewById(R.id.bt_query_submit);
        aet_from.setAdapter(new ArrayAdapter<String>(
                QueryActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                STATION_ARRAY
        ));
        aet_to.setAdapter(new ArrayAdapter<String>(
                QueryActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                STATION_ARRAY
        ));
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

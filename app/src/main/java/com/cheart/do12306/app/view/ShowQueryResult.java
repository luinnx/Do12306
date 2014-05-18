package com.cheart.do12306.app.view;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.adapter.ResultQueryAdapter;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.task.QueryTicketTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowQueryResult extends Activity {


    private static final String TAG = "ShowQueryResult";
    public static List<BaseQueryLeft> TICKET_LIST;
    public static List<BaseData> TICKET_BASEDATA_LIST;
    public static Map<String, Integer> TICKET_MAP;
    public static String TICKET_NUM;
    public static String DATE = "";
    private ListView list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query_result);
        init();
    }


    public void init(){
        initView();
    }

    public void initView(){
        list = (ListView) findViewById(R.id.lv_showQueryResult);
        Log.v(TAG, "SET ADAPTER");
//        Log.v(TAG, QueryActivity.QUERY_RESULT_LIST.toString());
        list.setAdapter(new ResultQueryAdapter(
                this,
                QueryActivity.QUERY_RESULT_LIST,
                R.layout.activity_resultqueryactivity_item,
                ResultQueryActivityListActivity.FROM,
                ResultQueryActivityListActivity.TO
        ));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowQueryResult.this, ShowTicketDetail.class);
                Log.v(TAG, "TN" + QueryActivity.QUERY_RESULT_LIST.get(i).
                        get("ticket_num"));
                intent.putExtra("ticket_num",QueryActivity.QUERY_RESULT_LIST.get(i).
                        get("ticket_num"));
                intent.putExtra("ticket_info", ShowQueryResult.TICKET_LIST.get(i));
                intent.putExtra("ticket_info_baseData", ShowQueryResult.TICKET_BASEDATA_LIST.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_query_result, menu);
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
}

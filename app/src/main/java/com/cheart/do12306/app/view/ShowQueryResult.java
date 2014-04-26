package com.cheart.do12306.app.view;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cheart.do12306.app.R;

public class ShowQueryResult extends Activity {


    private static final String TAG = "ShowQueryResult";
    private ListView list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query_result);

        list = (ListView) findViewById(R.id.lv_showQueryResult);
        Log.v(TAG, "SET ADAPTER");
        Log.v(TAG, QueryActivity.QUERY_RESULT_LIST.toString());
        list.setAdapter(new SimpleAdapter(
                this,
                QueryActivity.QUERY_RESULT_LIST,
                R.layout.activity_resultqueryactivity_item,
                ResultQueryActivityListActivity.FROM,
                ResultQueryActivityListActivity.TO
        ));


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

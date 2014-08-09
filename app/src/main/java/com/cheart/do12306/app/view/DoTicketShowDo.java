package com.cheart.do12306.app.view;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;
import com.cheart.do12306.app.task.QueryTicketTask;


public class DoTicketShowDo extends ActionBarActivity {


    private TextView tv_log;
    private boolean isStop = false;
    private Button bt_stop;
    private long a = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_log.setText((String)msg.obj);
        }
    };

    private static final String TAG = "DoTicketShowDo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_ticket_show_do);
    
        init();

        QueryTicketTask task = new QueryTicketTask(DoTicketShowDo.this,handler);
        task.execute(

                new String[]{
                        CommunalData.STATION_MAP.get("北京"/*aet_from.getText().toString()*/),
                        CommunalData.STATION_MAP.get("长春"/*aet_to.getText().toString()*/),
                        "2014-08-30",
                        QueryTicketTask.DO_FLAG_QUERY
                }
        );


    }

    private void init() {
        initView();
    }

    private void initView() {
        tv_log = (TextView) findViewById(R.id.tv_log);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStop = true;
            }
        });


      //  new UpdateUITask().execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.do_ticket_show_do, menu);
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


    class UpdateUITask extends AsyncTask<String,Integer,String>{



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, "doInBack");
            while (!isStop) {
                Log.v(TAG,a + "");
                Message message = new Message();
                message.obj = "update" + a;
                handler.sendMessage(message);
                a++;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            Log.v(TAG, "progress update");

            tv_log.setText(a+ "update");
            super.onProgressUpdate(values);
        }
    }
}

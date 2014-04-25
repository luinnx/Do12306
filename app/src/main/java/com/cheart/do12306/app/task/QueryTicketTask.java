package com.cheart.do12306.app.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.BaseQueryResult;
import com.cheart.do12306.app.view.QueryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheart on 4/25/2014.
 */
public class QueryTicketTask extends AsyncTask<String, Integer, List<BaseQueryResult>> {

    private static final String TAG = "QueryTicketTask";
    private List<BaseQueryResult> result;
    Context context;

    public QueryTicketTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<BaseQueryResult> doInBackground(String... strings) {
        String ticketQueryUrl = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="
                + strings[2]
                + "&leftTicketDTO.from_station="
                + strings[0]
                + "&leftTicketDTO.to_station="
                + strings[1] + "&purpose_codes=ADULT";
        String resultTicketQuery = MainActivity.core.getRequest(context, ticketQueryUrl,
                null, HttpsHeader.tiketSearch(), null, false);
        Log.v(TAG, resultTicketQuery);

        return null;
    }

    @Override
    protected void onPreExecute() {
        result = new ArrayList<BaseQueryResult>();

        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<BaseQueryResult> baseQueryResults) {


    }
}

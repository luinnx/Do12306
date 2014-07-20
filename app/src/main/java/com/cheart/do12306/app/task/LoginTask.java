package com.cheart.do12306.app.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.cheart.do12306.app.MainActivity;
import com.cheart.do12306.app.core.HttpsHeader;
import com.cheart.do12306.app.domain.Passenger;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 5/4/2014.
 */
public class LoginTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private ProgressDialog pd;


    public LoginTask() {

    }

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }



}

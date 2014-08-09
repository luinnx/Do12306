package com.cheart.do12306.app.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.client.CommunalData;

import java.io.LineNumberReader;
import java.util.List;
import java.util.Map;

public class DoTicketSetDo extends ActionBarActivity {


    private static final String TAG = "DoTicketSetDo";
    private LinearLayout ll_seat_type;
    private TextView tv_seat_type;
    private LinearLayout ll_passenger;
    private TextView tv_passenger;
    private LinearLayout ll_trian_code;
    private TextView tv_train_code;


    private Button bt_submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_ticket_set_do);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        ll_seat_type = (LinearLayout) findViewById(R.id.ll_do_ticket_set_do_seat_type);
        tv_seat_type = (TextView) findViewById(R.id.tv_do_ticket_set_do_seat_type);
        ll_passenger = (LinearLayout) findViewById(R.id.ll_do_ticket_set_do_passenger);
        tv_passenger = (TextView) findViewById(R.id.tv_do_ticket_set_do_passenger);
        ll_trian_code = (LinearLayout) findViewById(R.id.ll_do_ticket_set_do_train_code);
        tv_train_code = (TextView) findViewById(R.id.tv_do_ticket_set_do_train_code);

        bt_submit = (Button) findViewById(R.id.bt_do_ticket_set_do_submit);

        ll_seat_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoTicketSetDo.this,"OnClick",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DoTicketSetDo.this, SelectSeatType.class);
                startActivityForResult(intent, 0);

            }
        });

        ll_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoTicketSetDo.this,"OnClick",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DoTicketSetDo.this, SelectPassengerSetDo.class);
                startActivityForResult(intent, 0);

            }
        });


        ll_trian_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoTicketSetDo.this,"OnClick",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DoTicketSetDo.this, SelectTrainCode.class);
                startActivityForResult(intent, 0);

            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoTicketSetDo.this, DoTicketShowDo.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1){

            if (SelectSeatType.SELECTED_SEAT_TYPE_SET.size() > 0){
                CommunalData.SELECTED_SEAT_TYPE_SET = SelectSeatType.SELECTED_SEAT_TYPE_SET;
                Log.v(TAG, "select" + SelectSeatType.SELECTED_SEAT_TYPE_SET);

                StringBuffer sb = new StringBuffer();
                for(String s : SelectSeatType.SELECTED_SEAT_TYPE_SET){
                    sb.append(s + "   ");
                }

                sb.delete(sb.length() - 1, sb.length());

                tv_seat_type.setText(sb.toString());

            }


        } else if (resultCode == 2){
            if (SelectPassengerSetDo.SELECTED_PASSENGER_SET.size() > 0){
                CommunalData.SELECTED_PASSENGER_SET = SelectPassengerSetDo.SELECTED_PASSENGER_SET;
                Log.v(TAG, "select" + SelectPassengerSetDo.SELECTED_PASSENGER_SET);

                StringBuffer sb = new StringBuffer();
                for(String s : SelectPassengerSetDo.SELECTED_PASSENGER_SET){
                    sb.append(s + "   ");
                }

                sb.delete(sb.length() - 1, sb.length());

                tv_passenger.setText(sb.toString());
            }

        }else if (resultCode == 3){

            if (SelectTrainCode.SELECTED_TRAIN_CODE.size() > 0){
                CommunalData.SELECTED_TRAIN_CODE_SET = SelectTrainCode.SELECTED_TRAIN_CODE;
                Log.v(TAG, "select" + SelectTrainCode.SELECTED_TRAIN_CODE);

                StringBuffer sb = new StringBuffer();
                for(String s : SelectTrainCode.SELECTED_TRAIN_CODE){
                    sb.append(s + "   ");
                }

                sb.delete(sb.length() - 1, sb.length());


                tv_train_code.setText(sb.toString());

            }

        }
//        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.do_ticket_set_do, menu);
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

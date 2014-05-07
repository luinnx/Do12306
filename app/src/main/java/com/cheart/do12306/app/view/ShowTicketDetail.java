package com.cheart.do12306.app.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.domain.BaseData;
import com.cheart.do12306.app.domain.BaseQueryLeft;
import com.cheart.do12306.app.task.SubmitOrderTask;

import org.w3c.dom.Text;

public class ShowTicketDetail extends ActionBarActivity {

    private static final String TAG = "ShowTicketDetail";

    private Button bt_submit;
    private BaseQueryLeft ticketInfo;
    public static String CAN_TICKET_TYPE;

    public static BaseData SUBMIT_BASEDATA;

    private TextView tv_stationTrainCode;
    private TextView tv_startTime;
    private TextView tv_arriveTime;
    private TextView tv_lishi;
    private TextView tv_yzNum;
    private TextView tv_rzNum;
    private TextView tv_ywNum;
    private TextView tv_rwNum;
    private TextView tv_gwNum;
    private TextView tv_zyNum;
    private TextView tv_zeNum;
    private TextView tv_ztNum;
    private TextView tv_swzNum;
    private TextView tv_wzNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket_detail);
        Intent intent = getIntent();
        ticketInfo = (BaseQueryLeft) intent.getSerializableExtra("ticket_info");
        SUBMIT_BASEDATA = (BaseData) intent.getSerializableExtra("ticket_info_baseData");
        Log.v(TAG, ticketInfo.getStation_train_code());
        init();



    }

    public void initCanTicketType(BaseQueryLeft baseQueryLeft) {
        StringBuffer sb = new StringBuffer();
        if (!baseQueryLeft.getYz_num().equals("无") && !baseQueryLeft.getYz_num().equals("--")){
            sb.append("硬座" + ",");
        } else if (!baseQueryLeft.getRz_num().equals("无") && !baseQueryLeft.getRz_num().equals("--")){
            sb.append("软座" + ",");
        } else if (!baseQueryLeft.getYw_num().equals("无") && !baseQueryLeft.getYw_num().equals("--")){
            sb.append("硬卧" + ",");
        } else if (!baseQueryLeft.getRw_num().equals("无") && !baseQueryLeft.getRw_num().equals("--")){
            sb.append("软卧" + ",");
        } else if (!baseQueryLeft.getGr_num().equals("无") && !baseQueryLeft.getGr_num().equals("--")){
            sb.append("高级软卧" + ",");
        } else if (!baseQueryLeft.getZy_num().equals("无") && !baseQueryLeft.getZy_num().equals("--")){
            sb.append("一等座" + ",");
        } else if (!baseQueryLeft.getZe_num().equals("无") && !baseQueryLeft.getZe_num().equals("--")){
            sb.append("二等座" + ",");
        } else if (!baseQueryLeft.getTz_num().equals("无") && !baseQueryLeft.getTz_num().equals("--")){
            sb.append("特等座" + ",");
        } else if (!baseQueryLeft.getSwz_num().equals("无") && !baseQueryLeft.getSwz_num().equals("--")){
            sb.append("商务座" + ",");
        }
        CAN_TICKET_TYPE = sb.toString();
    }

    public void init() {
        initView();

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTicketDetail.this, SubmitOrderActivity.class);
                intent.putExtra("ticket_info", ticketInfo);
                startActivity(intent);
                //new SubmitOrderTask(ShowTicketDetail.this).execute();
            }
        });
    }

    public void initView() {
        bt_submit = (Button) findViewById(R.id.bt_showTicketDetail_submit);
        tv_stationTrainCode = (TextView) findViewById(R.id.tv_showTicketDetail_stationTrainCode);
        tv_startTime = (TextView) findViewById(R.id.tv_showTicketDetail_startTime);
        tv_arriveTime = (TextView) findViewById(R.id.tv_showTicketDetail_arriveTime);
        tv_lishi = (TextView) findViewById(R.id.tv_showTicketDetail_lishi);
        tv_wzNum = (TextView) findViewById(R.id.tv_showTicketDetail_wzNum);
        tv_yzNum = (TextView) findViewById(R.id.tv_showTicketDetail_yzNum);
        tv_rzNum = (TextView) findViewById(R.id.tv_showTicketDetail_rzNum);
        tv_ywNum = (TextView) findViewById(R.id.tv_showTicketDetail_ywNum);
        tv_rwNum = (TextView) findViewById(R.id.tv_showTicketDetail_rwNum);
        tv_gwNum = (TextView) findViewById(R.id.tv_showTicketDetail_gwNum);
        tv_zyNum = (TextView) findViewById(R.id.tv_showTicketDetail_zyNum);
        tv_zeNum = (TextView) findViewById(R.id.tv_showTicketDetail_zeNum);
        tv_ztNum = (TextView) findViewById(R.id.tv_showTicketDetail_ztNum);
        tv_swzNum = (TextView) findViewById(R.id.tv_showTicketDetail_swzNum);

        tv_stationTrainCode.setText(ticketInfo.getStation_train_code());
        tv_lishi.setText("历时" + ticketInfo.getLishi());
        tv_startTime.setText("(" + ticketInfo.getFrom_station_name() + ")" +
                ticketInfo.getStart_time());
        tv_arriveTime.setText(ticketInfo.getArrive_time() +
                        "(" + ticketInfo.getTo_station_name() + ")"
        );
        tv_wzNum.setText(ticketInfo.getWz_num());
        tv_yzNum.setText(ticketInfo.getYz_num());
        tv_rzNum.setText(ticketInfo.getRz_num());
        tv_ywNum.setText(ticketInfo.getYw_num());
        tv_rwNum.setText(ticketInfo.getRw_num());
        tv_gwNum.setText(ticketInfo.getGg_num());
        tv_zyNum.setText(ticketInfo.getZy_num());
        tv_zeNum.setText(ticketInfo.getZe_num());
        tv_ztNum.setText(ticketInfo.getTz_num());
        tv_swzNum.setText(ticketInfo.getSwz_num());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_ticket_detail, menu);
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

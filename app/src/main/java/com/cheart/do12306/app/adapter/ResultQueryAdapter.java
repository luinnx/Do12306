package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.domain.ResultQueryListItem;
import com.cheart.do12306.app.task.QueryTicketTask;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Handler;

import static com.cheart.do12306.app.R.drawable.bg_ll_result_query_train_class_k;

/**
 * Created by cheart on 4/26/2014.
 */
public class ResultQueryAdapter extends SimpleAdapter {
    List<Map<String, String>> data;
    Context context;
    public static final String TAG = "ResultQueryAdapter";


    public ResultQueryAdapter(Context context,
                              List<Map<String, String>> data,
                              int resource,
                              String[] from,
                              int[] to) {

        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;

    }



    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (holder == null) {
            holder = new ViewHolder();
            LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = ll.inflate(R.layout.activity_resultqueryactivity_item, null);
            holder.tv_stationTrainCode = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_stationTrainCode);
            holder.tv_startTime = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_startTime);
            holder.tv_arriveTime = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_arriveTime);
            holder.tv_startStationName = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_startStationName);
            holder.tv_endStationName = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_endStationName);
            holder.ll_yz = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_yz);
            holder.ll_rz = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_rz);
            holder.ll_yw = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_yw);
            holder.ll_rw = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_rw);
            holder.ll_gr = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_gr);
            holder.ll_zy = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_zy);
            holder.ll_ze = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_ze);
            holder.ll_td = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_td);
            holder.ll_swz = (LinearLayout) convertView.findViewById(R.id.ll_resultQuery_swz);
            holder.tv_yzNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_yzNum);
            holder.tv_rzNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_rzNum);
            holder.tv_ywNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_ywNum);
            holder.tv_rwNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_rwNum);
            holder.tv_grNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_grNum);
            holder.tv_zyNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_zyNum);
            holder.tv_zeNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_zeNum);
            holder.tv_tdNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_tdNum);
            holder.tv_swzNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_swzNum);


            holder.tv_train_class_name = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_train_class_name);

            holder.tv_train_class = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_train_class);

            convertView.setTag(holder);



        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.tv_stationTrainCode.setText(data.get(position).get(QueryTicketTask.STATION_TRAIN_CODE));
        holder.tv_startTime.setText(data.get(position).get(QueryTicketTask.START_TIME));
        holder.tv_arriveTime.setText(data.get(position).get(QueryTicketTask.ARRIVE_TIME));
        holder.tv_startStationName.setText(data.get(position).get(QueryTicketTask.START_STATION_NAME));
        holder.tv_endStationName.setText(data.get(position).get(QueryTicketTask.END_STATION_NAME));


        holder.tv_yzNum.setVisibility(View.VISIBLE);
        holder.tv_yzNum.setText("TEST");

        if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("--")){
            holder.ll_yz.setVisibility(View.VISIBLE);
            holder.tv_yzNum.setText("硬座" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_yzNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_yzNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }

        }

        if (!data.get(position).get(QueryTicketTask.RZ_NUM).equals("--")){
            holder.ll_rz.setVisibility(View.VISIBLE);
            holder.tv_rzNum.setText("软座" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_rzNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_rzNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }

        if (!data.get(position).get(QueryTicketTask.YW_NUM).equals("--")){
            holder.ll_yw.setVisibility(View.VISIBLE);
            holder.tv_ywNum.setText("硬卧" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_ywNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_ywNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }
        if (!data.get(position).get(QueryTicketTask.RW_NUM).equals("--")){
            holder.ll_rw.setVisibility(View.VISIBLE);
            holder.tv_rwNum.setText("软卧" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_rwNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_rwNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }
        if (!data.get(position).get(QueryTicketTask.GR_NUM).equals("--")){
            holder.ll_gr.setVisibility(View.VISIBLE);
            holder.tv_grNum.setText("高级软卧" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_grNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_grNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }

        if (!data.get(position).get(QueryTicketTask.ZY_NUM).equals("--")){
            holder.ll_zy.setVisibility(View.VISIBLE);
            holder.tv_zyNum.setText("一等" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_zyNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_zyNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }
        if (!data.get(position).get(QueryTicketTask.ZE_NUM).equals("--")){
            holder.ll_ze.setVisibility(View.VISIBLE);
            holder.tv_zeNum.setText("二等" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_zeNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_zeNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }

        if (!data.get(position).get(QueryTicketTask.TD_NUM).equals("--")){
            holder.ll_td.setVisibility(View.VISIBLE);
            holder.tv_tdNum.setText("特等" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_tdNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_tdNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }

        if (!data.get(position).get(QueryTicketTask.SWZ_NUM).equals("--")){
            holder.ll_swz.setVisibility(View.VISIBLE);
            holder.tv_swzNum.setText("商务" + "   " + data.get(position).get(QueryTicketTask.YZ_NUM));

            if (!data.get(position).get(QueryTicketTask.YZ_NUM).equals("无")){
                holder.tv_swzNum.setTextColor(context.getResources().getColor(R.color.query_result_y));
            } else {
                holder.tv_swzNum.setTextColor(context.getResources().getColor(R.color.query_result_w));
            }
        }



        holder.tv_train_class_name.setText(data.get(position).get("train_class_name"));
        String trainClassName = data.get(position).get("train_class_name");
        if (trainClassName.equals("特快")) {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_t);
        } else if (trainClassName.equals("")){
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_gc);
        } else if (trainClassName.equals("动车")){
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_d);
        } else if (trainClassName.equals("快速")){
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_k);
        } else if (trainClassName.equals("直达")){
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_z);
        } else {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_q);
        }


        return convertView;
    }

    public class ViewHolder {
        private TextView tv_startStationName;
        private TextView tv_endStationName;
        private TextView tv_stationTrainCode;
        private TextView tv_startTime;
        private TextView tv_arriveTime;
        private TextView tv_yzNum;
        private TextView tv_rzNum;
        private TextView tv_ywNum;
        private TextView tv_rwNum;
        private TextView tv_grNum;
        private TextView tv_zyNum;
        private TextView tv_zeNum;
        private TextView tv_tdNum;
        private TextView tv_swzNum;
        private TextView tv_wpNum;
        private TextView tv_dcNum;
        private TextView tv_train_class;
        private TextView tv_train_class_name;
        private LinearLayout ll_yz;
        private LinearLayout ll_rz;
        private LinearLayout ll_yw;
        private LinearLayout ll_rw;
        private LinearLayout ll_gr;
        private LinearLayout ll_zy;
        private LinearLayout ll_ze;
        private LinearLayout ll_td;
        private LinearLayout ll_swz;
    }
}

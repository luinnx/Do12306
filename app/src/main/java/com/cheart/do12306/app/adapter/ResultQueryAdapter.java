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

            holder.tv_train_class_name = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_train_class_name);

            holder.tv_train_class = (TextView) convertView.findViewById(R.id.
                    tv_resultQuery_train_class);

            holder.tv_t0 = (TextView) convertView.findViewById(R.id.tv_resultQuery_t0);
            holder.tv_t1 = (TextView) convertView.findViewById(R.id.tv_resultQuery_t1);
            holder.tv_t2 = (TextView) convertView.findViewById(R.id.tv_resultQuery_t2);
            holder.tv_t3 = (TextView) convertView.findViewById(R.id.tv_resultQuery_t3);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv_stationTrainCode.setText(data.get(position).get(QueryTicketTask.STATION_TRAIN_CODE));
        holder.tv_startTime.setText(data.get(position).get(QueryTicketTask.START_TIME));
        holder.tv_arriveTime.setText(data.get(position).get(QueryTicketTask.ARRIVE_TIME));
        holder.tv_startStationName.setText(data.get(position).get(QueryTicketTask.START_STATION_NAME));
        holder.tv_endStationName.setText(data.get(position).get(QueryTicketTask.END_STATION_NAME));

        String[] arr1 = data.get(position).get(QueryTicketTask.TICKET_NUM).split(",");
        for (int i = 0; i < arr1.length; i++) {
            if (holder.tv_t0.getText().equals("")) {
                holder.tv_t0.setText(arr1[i].split(">")[0] + "   " + arr1[i].split(">")[1]);
                holder.tv_t0.setVisibility(View.VISIBLE);
                if (arr1[i].split(">")[1].equals("无")) {
                    holder.tv_t0.setTextColor(context.getResources().getColor(R.color.query_result_w));

                }
            } else if (holder.tv_t1.getText().equals("")) {
                holder.tv_t1.setText(arr1[i].split(">")[0] + "   " + arr1[i].split(">")[1]);
                holder.tv_t1.setVisibility(View.VISIBLE);
                if (arr1[i].split(">")[1].equals("无")) {
                    holder.tv_t1.setTextColor(context.getResources().getColor(R.color.query_result_w));
                }
            } else if (holder.tv_t2.getText().equals("")) {
                holder.tv_t2.setText(arr1[i].split(">")[0] + "   " + arr1[i].split(">")[1]);
                holder.tv_t2.setVisibility(View.VISIBLE);
                if (arr1[i].split(">")[1].equals("无")) {
                    holder.tv_t2.setTextColor(context.getResources().getColor(R.color.query_result_w));
                }
            } else if (holder.tv_t3.getText().equals("")) {
                holder.tv_t3.setText(arr1[i].split(">")[0] + "   " + arr1[i].split(">")[1]);
                holder.tv_t3.setVisibility(View.VISIBLE);
                if (arr1[i].split(">")[1].equals("无")) {
                    holder.tv_t3.setTextColor(context.getResources().getColor(R.color.query_result_w));
                }
            }
        }

        holder.tv_train_class_name.setText(data.get(position).get("train_class_name"));
        String trainClassName = data.get(position).get("train_class_name");
        if (trainClassName.equals("特快")) {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_t);
        } else if (trainClassName.equals("")) {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_gc);
        } else if (trainClassName.equals("动车")) {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_d);
        } else if (trainClassName.equals("快速")) {
            holder.tv_train_class.setBackgroundResource(R.drawable.bg_ll_result_query_train_class_k);
        } else if (trainClassName.equals("直达")) {
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
        private TextView tv_wpNum;
        private TextView tv_dcNum;
        private TextView tv_train_class;
        private TextView tv_train_class_name;
        private TextView tv_t0;
        private TextView tv_t1;
        private TextView tv_t2;
        private TextView tv_t3;

    }
}

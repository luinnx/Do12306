package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.domain.ResultQueryListItem;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by cheart on 4/26/2014.
 */
public class ResultQueryAdapter extends SimpleAdapter {
    List<Map<String, String>> data;
    Context context;
    public static final String TAG = "ResultQueryAdapter";


    public ResultQueryAdapter(Context context,
                              List<? extends Map<String, String>> data,
                              int resource,
                              String[] from,
                              int[] to) {

        super(context, data, resource, from, to);

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
            holder.tv_yzNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_yzNum);
            holder.tv_rzNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_rzNum);
            holder.tv_wpNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_wpNum);
            holder.tv_dcNum = (TextView) convertView.findViewById(R.id.tv_resultQuery_dcNum);

            convertView.setTag(holder);



        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.tv_stationTrainCode.setText(data.get(position).get("station_train_code"));
        holder.tv_startTime.setText(data.get(position).get("start_time"));
        holder.tv_arriveTime.setText(data.get(position).get("arrive_time"));
        holder.tv_yzNum.setText(data.get(position).get("yz_num"));
        holder.tv_rzNum.setText(data.get(position).get("rz_num"));
        holder.tv_wpNum.setText(data.get(position).get("wp_num"));
        holder.tv_dcNum.setText(data.get(position).get("dc_num"));

        return convertView;
    }

    public class ViewHolder {
        private TextView tv_stationTrainCode;
        private TextView tv_startTime;
        private TextView tv_arriveTime;
        private TextView tv_yzNum;
        private TextView tv_rzNum;
        private TextView tv_wpNum;
        private TextView tv_dcNum;
    }
}

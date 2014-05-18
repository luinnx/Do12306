package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.domain.PassStation;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 5/15/2014.
 */
public class ShowTicketDetailPassAdapter extends SimpleAdapter {

    private Context context;
    private List<PassStation> data;
    public ShowTicketDetailPassAdapter(Context context,
                                       List<? extends Map<String, ?>> data,
                                       List<PassStation> data1,
                                       int resource,
                                       String[] from,
                                       int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data1;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder();
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.show_ticket_detail_item, null);
            holder.tv_passStationName = (TextView) convertView.findViewById(R.id.
                    tv_showTicketDetail_pass_stationName);
            holder.tv_passArriveTime = (TextView) convertView.findViewById(R.id.
                    tv_showTicketDetail_pass_arriveTimne);
            holder.tv_passStartTime = (TextView) convertView.findViewById(R.id.
                    tv_showTicketDetail_pass_startTime);
            holder.tv_passStopoverTine = (TextView) convertView.findViewById(R.id.
                    tv_showTicketDetail_pass_stopoverTime);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_passStationName.setText(data.get(position).getStation_name());
        holder.tv_passArriveTime.setText(data.get(position).getArrive_time());
        holder.tv_passStartTime.setText(data.get(position).getStart_time());
        holder.tv_passStopoverTine.setText(data.get(position).getStopover_time());
        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder{
        private TextView tv_passStationName;
        private TextView tv_passArriveTime;
        private TextView tv_passStartTime;
        private TextView tv_passStopoverTine;

    }
}

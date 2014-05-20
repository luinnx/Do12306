package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.view.DoTicket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SimpleTimeZone;

/**
 * Created by cheart on 2014/5/20.
 */
public class DoTicketDateAdapter extends SimpleAdapter {

    private static final String TAG = "DoTicketDateAdapter";
    Context context;
    List<HashMap<String, String>> data;
    public DoTicketDateAdapter(Context context,
                               List<HashMap<String, String>> data,
                               int resource,
                               String[] from,
                               int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;

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


    private class ViewHolder{
        TextView tv_date0;
        TextView tv_date1;
        TextView tv_date2;
        TextView tv_date3;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.activity_do_ticket_date_item, null);

        ViewHolder holder = null;
        if(holder == null){
            holder = new ViewHolder();
            holder.tv_date0 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date0);
            holder.tv_date1 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date1);
            holder.tv_date2 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date2);
            holder.tv_date3 = (TextView) convertView.findViewById(R.id.tv_do_ticket_date3);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String[] arr = data.get(position).get(DoTicket.DATE_LIST).split(",");
        holder.tv_date0.setText(arr[0]);
        holder.tv_date1.setText(arr[1]);
        holder.tv_date2.setText(arr[2]);
        holder.tv_date3.setText(arr[3]);
        convertView.setTag(holder);

        return convertView;
    }
}

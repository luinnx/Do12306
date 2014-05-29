package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.view.DoTicket;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cheart on 2014/5/20.
 */
public class DoTicketPassenegrAdapter extends SimpleAdapter {

    private static final String TAG = "DoTicketPassenegrAdapter";
    Context context;
    List<HashMap<String, String>> data;
    public DoTicketPassenegrAdapter(Context context,
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


    private class ViewHolder {
        CheckBox ck_passenger0;
        CheckBox ck_passenger1;
        CheckBox ck_passenger2;
        CheckBox ck_passenger3;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.activity_do_ticket_passenger_item, null);

        ViewHolder holder = null;
        if(holder == null){
            holder = new ViewHolder();
            holder.ck_passenger0 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger0);
            holder.ck_passenger1 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger1);
            holder.ck_passenger2 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger2);
            holder.ck_passenger3 = (CheckBox) convertView.findViewById(R.id.tv_do_ticket_passenger3);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String[] arr = data.get(position).get(DoTicket.PASSENGER_LIST).split(",");
        holder.ck_passenger0.setText(null != arr[0] && !arr[0].equals("null") ? arr[0] : "");
        holder.ck_passenger1.setText(null != arr[1] && !arr[1].equals("null") ? arr[1] : "");
        holder.ck_passenger2.setText(null != arr[2] && !arr[2].equals("null") ? arr[2] : "");
        holder.ck_passenger3.setText(null != arr[3] && !arr[3].equals("null") ? arr[3] : "");

        convertView.setTag(holder);

        return convertView;
    }
}

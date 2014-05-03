package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.view.SubmitOrderActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/29/2014.
 */
public class SubmitOrderSelectPassengerAdapter extends SimpleAdapter {
    List<? extends Map<String, ?>> data;
    Context context;
    public SubmitOrderSelectPassengerAdapter(Context context,
                                             List<? extends Map<String, ?>> data,
                                             int resource,
                                             String[] from,
                                             int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
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
        private CheckBox ck_name_1;
        private CheckBox ck_name_2;
        private CheckBox ck_name_3;
        private CheckBox ck_name_4;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder();
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.submit_order_select_passenger_item, null);

            holder.ck_name_1 = (CheckBox) convertView.findViewById(R.id.
                    tv_submitOrder_selectPassenger_name_1);
            holder.ck_name_2 = (CheckBox) convertView.findViewById(R.id.
                    tv_submitOrder_selectPassenger_name_2);
            holder.ck_name_3 = (CheckBox) convertView.findViewById(R.id.
                    tv_submitOrder_selectPassenger_name_3);
            holder.ck_name_4 = (CheckBox) convertView.findViewById(R.id.
                    tv_submitOrder_selectPassenger_name_4);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ck_name_1.setText(data.get(position).get("name_1").toString());
        holder.ck_name_2.setText(data.get(position).get("name_2").toString());
        holder.ck_name_3.setText(data.get(position).get("name_3").toString());
        holder.ck_name_4.setText(data.get(position).get("name_2").toString());

        convertView.setTag(holder);
        return convertView;
    }



}


package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.view.SelectPassengerSetDo;
import com.cheart.do12306.app.view.SelectSeatType;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/28/2014.
 */
public class SelectPassengerSetDoAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends  Map<String, ?>> data;
    public static final String TAG = "SelectPassengerSetDoAdapter";

    public SelectPassengerSetDoAdapter(Context context,
                                       List<? extends Map<String, ?>> data,
                                       int resource,
                                       String[] from,
                                       int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
    }

    public SelectPassengerSetDoAdapter(Context context,
                                       List<? extends Map<String, ?>> data,
                                       int resource,
                                       String[] from,
                                       int[] to,
                                       Context context1) {
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
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.activity_select_passenger_set_do_item,null);

            holder.ck_ = (CheckBox) convertView.findViewById(R.id.ck_select_passenger_set_do_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.v(TAG, "ck_ is null" + " " + (null == holder.ck_));
        Log.v(TAG, "data is null" + " " + (null == data));
        Log.v(TAG, "map is null" + " " + (null == data.get(position)));
        Log.v(TAG, "string is null" + " " + (null == data.get(position).get("passenger").toString()));
        holder.ck_.setText(data.get(position).get("passenger").toString());
        holder.ck_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectPassengerSetDo.SELECTED_PASSENGER_SET.add(buttonView.getText().toString());
                } else {
                    SelectPassengerSetDo.SELECTED_PASSENGER_SET.remove(buttonView.getText().toString());
                }


                Log.v(TAG, "" + SelectPassengerSetDo.SELECTED_PASSENGER_SET);
            }
        });


        return convertView;
    }

    class ViewHolder {

        private CheckBox ck_;
    }
}

package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cheart.do12306.app.R;
import com.cheart.do12306.app.view.SelectSeatType;
import com.cheart.do12306.app.view.ShowQueryResult;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/28/2014.
 */
public class SelectSeatTypeAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends  Map<String, ?>> data;
    public static final String TAG = "SelectSeatTypeAdapter";

    public SelectSeatTypeAdapter(Context context,
                                 List<? extends Map<String, ?>> data,
                                 int resource,
                                 String[] from,
                                 int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
    }

    public SelectSeatTypeAdapter(Context context,
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
            convertView = li.inflate(R.layout.activity_select_seat_type_item,null);

            holder.ck_ = (CheckBox) convertView.findViewById(R.id.ck_select_seat_type_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ck_.setText(data.get(position).get("seat_type").toString());
        holder.ck_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SelectSeatType.SELECTED_SEAT_TYPE_SET.add(buttonView.getText().toString());
                } else {
                    SelectSeatType.SELECTED_SEAT_TYPE_SET.remove(buttonView.getText().toString());
                }



                Log.v(TAG, "" + SelectSeatType.SELECTED_SEAT_TYPE_SET);
            }
        });


        return convertView;
    }

    class ViewHolder {

        private CheckBox ck_;
    }
}

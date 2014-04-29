package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cheart.do12306.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/28/2014.
 */
public class SelectPassengerAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends  Map<String, ?>> data;

    public SelectPassengerAdapter(Context context,
                                  List<? extends Map<String, ?>> data,
                                  int resource,
                                  String[] from,
                                  int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
    }

    public SelectPassengerAdapter(Context context,
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
            convertView = li.inflate(R.layout.select_passenger_item,null);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_selectPassenger_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_selectPassenger_name);
            holder.tv_cardValue = (TextView) convertView.findViewById(R.id.
                    tv_selectPassenger_cardValue);
            holder.ck_ = (CheckBox) convertView.findViewById(R.id.ck_selectPassenger);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_id.setText(data.get(position).get("id").toString());
        holder.tv_name.setText("()" + data.get(position).get("passenger_name").toString() + "(" +
                               "(" +
                               data.get(position).get("passenger_type_name") +
                               ")"
        );


        holder.tv_cardValue.setText(data.get(position).get("passenger_id_no").toString());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_id;
        private TextView tv_name;
        private TextView tv_cardValue;
        private CheckBox ck_;
    }
}

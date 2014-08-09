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
import com.cheart.do12306.app.view.SelectTrainCode;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/28/2014.
 */
public class SelectTrainCodeAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends  Map<String, ?>> data;
    public static final String TAG = "SelectTrainCodeAdapter";

    public SelectTrainCodeAdapter(Context context,
                                  List<? extends Map<String, ?>> data,
                                  int resource,
                                  String[] from,
                                  int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
    }

    public SelectTrainCodeAdapter(Context context,
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
            convertView = li.inflate(R.layout.activity_select_train_code_item,null);

            holder.ck_ = (CheckBox) convertView.findViewById(R.id.ck_select_train_code_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ck_.setText(data.get(position).get("train_code").toString());
        holder.ck_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SelectTrainCode.SELECTED_TRAIN_CODE.add(buttonView.getText().toString());
                } else {
                    SelectTrainCode.SELECTED_TRAIN_CODE.remove(buttonView.getText().toString());
                }



                Log.v(TAG, "" + SelectTrainCode.SELECTED_TRAIN_CODE);
            }
        });


        return convertView;
    }

    class ViewHolder {

        private CheckBox ck_;
    }
}

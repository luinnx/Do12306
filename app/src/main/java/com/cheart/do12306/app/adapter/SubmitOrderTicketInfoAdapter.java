package com.cheart.do12306.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cheart.do12306.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by cheart on 4/30/2014.
 */
public class SubmitOrderTicketInfoAdapter extends SimpleAdapter {

    private Context context;
    private List<? extends Map<String, ?>> data;
    private LayoutInflater li;

    public SubmitOrderTicketInfoAdapter(Context context,
                                        List<? extends Map<String, ?>> data,
                                        int resource, String[] from,
                                        int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {

        return data.size();
    }

    private class ViewHolder {
        private TextView tv_passengerName;
        private Spinner sp_seatType;
        private Spinner sp_ticketType;
        private TextView tv_idNo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (holder == null) {
            holder = new ViewHolder();
            convertView = li.inflate(R.layout.submit_order_ticket_info_item, null);
            holder.tv_passengerName = (TextView) convertView.findViewById(R.id.
                    tv_submitOrder_ticket_info_item_passengerName);
            holder.tv_idNo = (TextView) convertView.findViewById(R.id.
                    tv_submitOrder_ticket_info_item_passengerIdNo);
            holder.sp_ticketType = (Spinner) convertView.findViewById(R.id.
                    sp_submitOrder_ticket_info_seat_Type);
            holder.sp_seatType = (Spinner) convertView.findViewById(R.id.
                    sp_submitOrder_ticket_info_ticket_Type);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_passengerName.setText(data.get(position).get("passenger_name").toString());
        holder.tv_idNo.setText(data.get(position).get("passenger_id_no").toString());

        holder.sp_ticketType.setAdapter(new TicketTypeAdapter(context,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"硬座", "硬卧"}));

        holder.sp_seatType.setAdapter(new SeatTypeAdapter(context,
                android.R.layout.simple_expandable_list_item_1,
                data.get(position).get("passenger_type").equals("学生")?
                        new String[] {"学生","成人"} : new String[] {"成人"}));

        convertView.setTag(holder);


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    private class TicketTypeAdapter extends ArrayAdapter {

        String[] data;
        Context context;
        private TextView tv_;

        private TicketTypeAdapter(Context context, int resource, String[] data) {
            super(context, resource, data);
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = li.inflate(R.layout.submit_order_ticket_info_ticket_type_item, null);
            tv_ = (TextView) view.findViewById(R.id.tv_submitOrder_ticket_info_item_ticketType_item);
            tv_.setText(data[position]);


            return tv_;
        }

        @Override
        public long getItemId(int position) {
            return position + 1;
        }
    }

    private class SeatTypeAdapter extends ArrayAdapter {

        String[] data;
        Context context;
        private TextView tv_;

        private SeatTypeAdapter(Context context, int resource, String[] data) {
            super(context, resource, data);
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = li.inflate(R.layout.submit_order_ticket_info_seat_type_item, null);
            tv_ = (TextView) view.findViewById(R.id.tv_submitOrder_ticket_info_item_seatType_item);
            tv_.setText(data[position]);


            return tv_;
        }

        @Override
        public long getItemId(int position) {



            return position + 1;
        }
    }

}

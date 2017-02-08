package com.android.spinnertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.spinnertest.model.ZoneData;


/**
 * Created by junaid Ahmad on 2/7/2017.
 */

public class CustomSpinnerZoneAdapter extends BaseAdapter {
    Context context;
    ZoneData zoneData;


    public CustomSpinnerZoneAdapter(Context context,ZoneData zoneData){
        this.context=context;
        this.zoneData=zoneData;
    }

    @Override
    public int getCount() {
        return zoneData.getData().getZone().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row, parent,
                false);
        TextView name = (TextView) row.findViewById(R.id.name);

        name.setText(zoneData.getData().getZone().get(position).getName());
        return row;
    }
}

package com.android.spinnertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.spinnertest.model.Country;

/**
 * Created by junaid Ahmad on 2/7/2017.
 */

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    Country country;


    public CustomSpinnerAdapter(Context context,Country country){
        this.context=context;
        this.country=country;
    }

    @Override
    public int getCount() {
        return country.getData().size();
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

        name.setText(country.getData().get(position).getName());
        return row;
    }
}

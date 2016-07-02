package com.codepath.apps.mysimpletweets.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;

import java.util.List;

public class TrendArrayAdapter extends ArrayAdapter<Trend> {
    public TrendArrayAdapter(Context context,List<Trend> trends) {
        super(context,android.R.layout.simple_list_item_1,trends);
    }

    //override getView

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trend,parent,false);
        }

        TextView tvTrend = (TextView)convertView.findViewById(R.id.tvTrend);
        TextView tvVolume = (TextView)convertView.findViewById(R.id.tvVolume);
        TextView tvCount = (TextView)convertView.findViewById(R.id.tvCount); //may not use

        Trend trend = getItem(position);
        tvTrend.setText(trend.getTopic());
        if(trend.getTweetVolume() != -1) {
            tvVolume.setText(trend.getTweetVolume() + " Tweets");
        }
        else {
            tvVolume.setText("Trending Now");
        }
//        Log.d("debug",trend.getTweetVolume()+"");
        tvCount.setText((position+1)+"");//may not work

        return convertView;
    }
}

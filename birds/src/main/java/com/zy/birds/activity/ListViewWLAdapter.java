package com.zy.birds.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Weekly;
import com.zy.birds.R;
import com.zy.birds.util.ImageUtil;

import java.util.List;

public class ListViewWLAdapter extends BaseAdapter {

    public Context context;
    public BirdsDB birdsDB = BirdsDB.getInstance(context);
    public List<Weekly> list = birdsDB.queryAllWeeklys();

    public ListViewWLAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Adapter", "adapter");
        Weekly data = list.get(list.size()-1-position);
        if (convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.lvweekly_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.bg_weekly = (ImageView) convertView.findViewById(R.id.rl_bg);
            viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_week);
            viewHolder.tv_theme = (TextView) convertView.findViewById(R.id.tv_weekpt);
//			viewHolder.bg_weekly.setImageResource(R.drawable.iv_bgo);
            ImageUtil.loadImageView(data.getWimage(), viewHolder.bg_weekly);
//			viewHolder.bg_weekly.setImageBitmap(ImageUtil.loadImage(data.getWimage()));
            viewHolder.tv_week.setText(data.getWeek());
            viewHolder.tv_theme.setText(data.getTheme());
            convertView.setTag(viewHolder);
        } else {
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
//			viewHolder.bg_weekly.setImageResource(R.drawable.iv_bgo);
            ImageUtil.loadImageView(data.getWimage(), viewHolder.bg_weekly);
//			viewHolder.bg_weekly.setImageBitmap(ImageUtil.loadImage(data.getWimage()));
            viewHolder.tv_week.setText(data.getWeek());
            viewHolder.tv_theme.setText(data.getTheme());
        }
        return convertView;
    }
    class ViewHolder{
        ImageView bg_weekly;
        TextView tv_week;
        TextView tv_theme;
    }
}
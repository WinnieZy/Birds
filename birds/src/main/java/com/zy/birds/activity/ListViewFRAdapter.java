package com.zy.birds.activity;

import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.FavoriteData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 对应个人收藏页面favorite的adapter
 */
public class ListViewFRAdapter extends BaseAdapter {

	public Context context;
	public List<FavoriteData> list;
	
	public ListViewFRAdapter(Context context, List<FavoriteData> list) {
		super();
		this.context = context;
		this.list = list;
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
		
		int point = list.size()-1-position;
		FavoriteData data = list.get(point);
		if (convertView==null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lvfavorite_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.f_pt = (TextView) convertView.findViewById(R.id.f_pt);
			viewHolder.f_pa = (TextView) convertView.findViewById(R.id.f_pa);
			viewHolder.f_pd = (TextView) convertView.findViewById(R.id.f_date);
			viewHolder.f_plcount = (TextView) convertView.findViewById(R.id.f_plcount);
			viewHolder.f_pscount = (TextView) convertView.findViewById(R.id.f_pscount);
			viewHolder.f_pt.setText(data.getF_pt());
			viewHolder.f_pd.setText(data.getF_pd());
			viewHolder.f_pa.setText(data.getF_pa());
			viewHolder.f_plcount.setText(String.valueOf(data.getF_plcount()));
			viewHolder.f_pscount.setText(String.valueOf(data.getF_pscount()));
			convertView.setTag(viewHolder);
		} else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.f_pt.setText(data.getF_pt());
			viewHolder.f_pd.setText(data.getF_pd());
			viewHolder.f_pa.setText(data.getF_pa());
			viewHolder.f_plcount.setText(String.valueOf(data.getF_plcount()));
			viewHolder.f_pscount.setText(String.valueOf(data.getF_pscount()));
		}
		return convertView;
	}

	class ViewHolder{
		TextView f_pt;
		TextView f_pd;
		TextView f_pa;
		TextView f_plcount;
		TextView f_pscount;
	}
}

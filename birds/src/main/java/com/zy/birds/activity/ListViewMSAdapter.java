package com.zy.birds.activity;

import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.MyshareData;
import com.zy.birds.Model.Poem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 个人收藏页对应adapter
 */
public class ListViewMSAdapter extends BaseAdapter {

	public Context context;
	public List<MyshareData> list;
	public List<Poem> poems = MyshareActivity.poems;
	
	public ListViewMSAdapter(Context context, List<MyshareData> list) {
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
		MyshareData data = list.get(point);
		if (convertView==null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lvmyshare_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.ms_pt = (TextView) convertView.findViewById(R.id.ms_pt);
			viewHolder.ms_pd = (TextView) convertView.findViewById(R.id.ms_date);
			viewHolder.ms_plcount = (TextView) convertView.findViewById(R.id.ms_plcount);
			viewHolder.ms_pscount = (TextView) convertView.findViewById(R.id.ms_pscount);
			viewHolder.ms_pt.setText(data.getMs_pt());
			viewHolder.ms_pd.setText(data.getMs_pd());
			viewHolder.ms_plcount.setText(String.valueOf(data.getMs_plcount()));
			viewHolder.ms_pscount.setText(String.valueOf(data.getMs_pscount()));
			convertView.setTag(viewHolder);
		} else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.ms_pt.setText(data.getMs_pt());
			viewHolder.ms_pd.setText(data.getMs_pd());
			viewHolder.ms_plcount.setText(String.valueOf(data.getMs_plcount()));
			viewHolder.ms_pscount.setText(String.valueOf(data.getMs_pscount()));
		}
		return convertView;
	}

	class ViewHolder{
		TextView ms_pt;
		TextView ms_pd;
		TextView ms_plcount;
		TextView ms_pscount;
	}
}

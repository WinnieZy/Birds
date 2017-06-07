package com.zy.birds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Today;
import com.zy.birds.R;
/*
 * today 页面暂未实现与后台连接的功能
 */
public class TodayFragment extends Fragment {

	public TextView tv_ttitle,tv_tauthor,tv_tcontent,tv_tdate;
	public ImageView iv_timage;
	public Context context;
	public BirdsDB birdsDB;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_today, container, false);
		birdsDB = BirdsDB.getInstance(context);
		tv_ttitle = (TextView) view.findViewById(R.id.tv_pt);
		tv_tauthor = (TextView) view.findViewById(R.id.tv_pa);
		tv_tcontent = (TextView) view.findViewById(R.id.tv_pc);
		tv_tdate = (TextView) view.findViewById(R.id.tv_date);
		iv_timage = (ImageView) view.findViewById(R.id.iv_today);
		Today today = birdsDB.queryAllToday();
		tv_ttitle.setText(today.getTtitle());
		tv_tauthor.setText(today.getTauthor());
		tv_tcontent.setText(today.getTcontent());
		tv_tdate.setText(today.getTdate());
//		iv_timage.setImageBitmap(bm);
		return view;
	}

}

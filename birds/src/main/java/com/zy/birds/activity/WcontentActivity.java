package com.zy.birds.activity;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Weekly;
import com.zy.birds.util.ImageUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * weekly详情页，显示该week推文
 */
public class WcontentActivity extends Activity {

	public ImageView iv_wcontent;
	public TextView tv_wcontent;
	public Weekly weekly;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wcontent);
		iv_wcontent = (ImageView) findViewById(R.id.iv_wcontent);
		tv_wcontent = (TextView) findViewById(R.id.tv_wcontent);
		Intent intent = getIntent();
		String week = intent.getStringExtra("week");
		weekly = BirdsDB.getInstance(this).queryWeeklyByWeek(week);
		ImageUtil.loadBitmap(weekly.getWimage(), iv_wcontent);
		tv_wcontent.setText(weekly.getWcontent());
	}
}

package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.MyshareData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.User;
import com.zy.birds.util.DateUtil;
import com.zy.birds.util.ExitAppUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/*
 * 个人分享页面
 */
public class MyshareActivity extends Activity {

	public BirdsDB bridsDB;
	public static User loginUser;
	public Button btn_back;
	public ListView lv_myshare;
	public ListViewMSAdapter adapter;
	public List<MyshareData> datas;
	public static List<Poem> poems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		setContentView(R.layout.activity_myshare);
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		initDatas();
		btn_back = (Button) findViewById(R.id.btn_back);
		lv_myshare = (ListView) findViewById(R.id.lv_myshare);
		adapter = new ListViewMSAdapter(MyshareActivity.this, datas);
		adapter.notifyDataSetChanged();
		lv_myshare.setAdapter(adapter);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//item点击事件监听，传输MyshareData给下一个活动
		lv_myshare.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				MyshareData myshareData = datas.get(datas.size()-1-position);
				Intent intent = new Intent(MyshareActivity.this,DetailActivity.class);
				Bundle mBundle = new Bundle();   
				mBundle.putSerializable("myshareData", myshareData);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
	}
	
	/*
	 * 从数据库中找到我的分享诗歌，加载到List中
	 */
	public List<MyshareData> initDatas(){
		datas = new ArrayList<MyshareData>();
		poems = bridsDB.queryPoemByAuthor(loginUser.getUname());
		for(Poem poem : poems){
			MyshareData data = new MyshareData();
			data.setMs_pid(poem.getId());
			data.setMs_pt(poem.getPtitle());
			data.setMs_pd(DateUtil.formatDisplayTime(poem.getPpdate()));
			data.setMs_plcount(poem.getPlcount());
			data.setMs_pscount(poem.getPscount());
			datas.add(data);
		}
		return datas;
	}
}

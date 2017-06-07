package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.FavoriteData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
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
 * 个人收藏页面
 */
public class FavoriteActivity extends Activity {

	public BirdsDB bridsDB;
	public static User loginUser;
	public Button btn_back;
	public ListView lv_favorite;
	public ListViewFRAdapter adapter;
	public List<FavoriteData> datas;
	public List<Poem> poems = new ArrayList<Poem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		setContentView(R.layout.activity_favorite);
		bridsDB = BirdsDB.getInstance(this);
		ExitAppUtil.getInstance().addActivity(this);
		initDatas();
		btn_back = (Button) findViewById(R.id.btn_back);
		lv_favorite = (ListView) findViewById(R.id.lv_favorite);
		adapter = new ListViewFRAdapter(FavoriteActivity.this, datas);
		adapter.notifyDataSetChanged();
		lv_favorite.setAdapter(adapter);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		/*
		 * item点击事件，传输FavoriteData给下一个活动
		 */
		lv_favorite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				FavoriteData favoriteData = datas.get(datas.size()-1-position);
				Intent intent = new Intent(FavoriteActivity.this,DetailActivity.class);
				Bundle mBundle = new Bundle();   
				mBundle.putSerializable("favoriteData", favoriteData);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		
		});
	}
		
	/*
	 * 从数据库查找，加载评论列表数据
	 */
	public List<FavoriteData> initDatas(){
		datas = new ArrayList<FavoriteData>();
		List<Relationship> relationships = bridsDB.queryRelationshipByUidStared(loginUser.getId(), 1);
		for(Relationship relationship : relationships){
			Poem p = bridsDB.queryPoemById(relationship.getPid());
			poems.add(p);
		}
		for(Poem poem : poems){
			FavoriteData data = new FavoriteData();
			data.setF_pid(poem.getId());
			data.setF_pt(poem.getPtitle());
			data.setF_pd(DateUtil.formatDisplayTime(poem.getPpdate()));
			data.setF_pa(poem.getPauthor());
			data.setF_plcount(poem.getPlcount());
			data.setF_pscount(poem.getPscount());
			datas.add(data);
		}
		return datas;
	}
}

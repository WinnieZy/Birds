package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.FavoriteData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.User;
import com.zy.birds.util.DateUtil;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/*
 * 搜索页面，对应右上角放大镜按钮
 */
public class SearchActivity extends Activity {

	public Button btn_back,btn_search;
	public EditText et_search;
	public static User loginUser;
	public BirdsDB birdsDB;
	public ListViewFRAdapter adapter;
	public List<FavoriteData> datas;
	public ListView lv_search;
	public String searchString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		loginUser = LoginActivity.loginUser;
		ExitAppUtil.getInstance().addActivity(this);
		birdsDB = BirdsDB.getInstance(SearchActivity.this);
		lv_search = (ListView) findViewById(R.id.lv_search);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_search = (Button) findViewById(R.id.btn_search);
		et_search = (EditText) findViewById(R.id.et_search);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datas = new ArrayList<FavoriteData>();
				searchString = et_search.getText().toString();
				if (searchString.equals("")) {
					ToastUtil.showMessage(SearchActivity.this, "搜索栏不能为空");
				}else {
					//暂时只提供从数据库内查询作者和诗名的搜索
					List<Poem> poemsByTitle = birdsDB.queryPoemByTitle(searchString);
					List<Poem> poemsByAuthor = birdsDB.queryPoemByAuthor(searchString);
					for(Poem poem : poemsByTitle){
						FavoriteData data = new FavoriteData();
						data.setF_pid(poem.getId());
						data.setF_pt(poem.getPtitle());
						data.setF_pd(DateUtil.formatDisplayTime(poem.getPpdate()));
						data.setF_pa(poem.getPauthor());
						data.setF_plcount(poem.getPlcount());
						data.setF_pscount(poem.getPscount());
						datas.add(data);
					}
					for(Poem poem : poemsByAuthor){
						FavoriteData data = new FavoriteData();
						data.setF_pid(poem.getId());
						data.setF_pt(poem.getPtitle());
						data.setF_pd(DateUtil.formatDisplayTime(poem.getPpdate()));
						data.setF_pa(poem.getPauthor());
						data.setF_plcount(poem.getPlcount());
						data.setF_pscount(poem.getPscount());
						datas.add(data);
					}
					if (datas.size()!=0) {
						adapter = new ListViewFRAdapter(SearchActivity.this, datas);
						adapter.notifyDataSetChanged();
						lv_search.setAdapter(adapter);
						lv_search.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position,
									long id) {
								FavoriteData favoriteData = datas.get(datas.size()-1-position);
								Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
								Bundle mBundle = new Bundle();   
								mBundle.putSerializable("favoriteData", favoriteData);
								intent.putExtras(mBundle);
								startActivity(intent);
							}
						
						});				
					} else {
						ToastUtil.showMessage(SearchActivity.this, "查询无结果");
					}
				}				
			}
		});

	}

}

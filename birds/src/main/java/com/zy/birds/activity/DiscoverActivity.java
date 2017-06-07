package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.DiscoverData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.activity.ListViewDCAdapter.Callback;
import com.zy.birds.util.ExitAppUtil;

/*
 * 已被discoverFragment取代
 */
public class DiscoverActivity extends SlidingFragmentActivity implements Callback{

	public BirdsDB bridsDB;
	public static User loginUser;
	public Button btn_menu,btn_search,btn_share,btn_today,btn_weekly,btn_discover;
	public ListView lv_discover;
	public ListViewDCAdapter adapter;
	public List<DiscoverData> datas;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		setContentView(R.layout.activity_discover);
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		getDatas();
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_today = (Button) findViewById(R.id.btn_today);
		btn_weekly = (Button) findViewById(R.id.btn_weekly);
		btn_discover = (Button) findViewById(R.id.btn_discover);
		lv_discover = (ListView) findViewById(R.id.ll_discover);
		adapter = new ListViewDCAdapter(DiscoverActivity.this, datas, this,loginUser);
		adapter.notifyDataSetChanged();
		lv_discover.setAdapter(adapter);
		/*
		 * 点击item跳转到诗歌详情页面
		 * 传输所点击item对应的对象给下一个活动
		 */
		lv_discover.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
//				Poem poem = BridsDB.getInstance(DiscoverActivity.this).queryPoemByPosition(position+1);
				DiscoverData discoverData = datas.get(datas.size()-1-position);
				Intent intent = new Intent(DiscoverActivity.this,DetailActivity.class);
				Bundle mBundle = new Bundle();   
//				mBundle.putSerializable("poem", poem);
				mBundle.putSerializable("discoverData", discoverData);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DiscoverActivity.this,SearchActivity.class);
				startActivity(intent);
			}
		});
		btn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DiscoverActivity.this,ShareActivity.class);
				startActivity(intent);
			}
		});
		btn_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DiscoverActivity.this,TodayActivity.class);
				startActivity(intent);
			}
		});
		btn_weekly.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DiscoverActivity.this,WeeklyActivity.class);
				startActivity(intent);
			}
		});
		initRightMenu();//加载左边滑动菜单
	}

	/*
	 * 每次重新返回当前活动时重新加载adapter以保证数据最新
	 * (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getDatas();
		adapter = new ListViewDCAdapter(DiscoverActivity.this, datas, this,loginUser);
		adapter.notifyDataSetChanged();
		lv_discover.setAdapter(adapter);
		Log.d("DiscoverAcitivity", "onRestart");
	}
	/*
	 * 从数据库中找出所有的诗的信息并转换为discoverData用于页面数据展示
	 */
	public List<DiscoverData> getDatas(){
		datas = new ArrayList<DiscoverData>();
		List<Poem> poems = bridsDB.queryAllPoems();
		for (Poem poem : poems) {
			Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
			DiscoverData data = new DiscoverData();
			data.setDpid(poem.getId());
			data.sethImage(bridsDB.queryUserByName(poem.getPauthor()).getUimage());
			data.setUserName(poem.getPauthor());
			data.setPsource(poem.getPsource());
			data.setcImage(R.drawable.clock);
			data.setTime(poem.getPpdate());
			data.setPtitle(poem.getPtitle());
			data.setPcontent(poem.getPcontent());
			data.setHeart(String.valueOf(poem.getPlcount()));
			data.setComment(String.valueOf(poem.getPccount()));
			data.setStar(String.valueOf(poem.getPscount()));
			if (relationship!=null) {
				data.setHEART_STATUS(relationship.getHearted());
				data.setSTAR_STATUS(relationship.getStared());
			} 
			datas.add(data);
		}
		return datas;
	}

	private void initRightMenu()
	{
		
		Fragment leftMenuFragment = new MenuLeftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setBehindWidth()
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
//		// menu.setBehindScrollScale(1.0f);
//		menu.setSecondaryShadowDrawable(R.drawable.shadow);
//		//设置右边（二级）侧滑菜单
//		menu.setSecondaryMenu(R.layout.right_menu_frame);
//		Fragment rightMenuFragment = new MenuRightFragment();
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
	}

	public void showLeftMenu(View view)
	{
		Log.d("DiscoverActivity", "show");
		getSlidingMenu().showMenu();
	}
	
	/*
	 * callback接口回调函数，用于点击adapter内评论按钮时得到相应数据传输给detail活动
	 * (non-Javadoc)
	 * @see com.zy.birds.activity.ListViewDCAdapter.Callback#click(android.view.View)
	 */
	@Override
	public void click(View v) {
		// TODO Auto-generated method stub
//		ToastUtil.showMessage(
//                DiscoverActivity.this,
//                "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
//                        + datas.get((Integer) v.getTag())
//                );	
//		Poem poem = BridsDB.getInstance(DiscoverActivity.this).queryPoemByPosition((Integer) v.getTag());
		DiscoverData discoverData = datas.get(datas.size()-1-(Integer) v.getTag());
		Intent intent = new Intent(DiscoverActivity.this,DetailActivity.class);
		Bundle mBundle = new Bundle();   
		mBundle.putSerializable("discoverData", discoverData);
		intent.putExtras(mBundle);
		startActivity(intent);
	}
	/*
	 * 按下返回键，程序退回后台运行
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            moveTaskToBack(true);  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 

}

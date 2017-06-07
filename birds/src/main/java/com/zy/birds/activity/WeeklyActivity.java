package com.zy.birds.activity;

import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zy.birds.R;
import com.zy.birds.Model.Weekly;
import com.zy.birds.Model.WeeklyData;
import com.zy.birds.util.ExitAppUtil;

/*
 * 已被weeklyfragment取代  每周推送页面
 */
public class WeeklyActivity extends SlidingFragmentActivity {

	public Button btn_menu,btn_search,btn_share,btn_today,btn_weekly,btn_discover;
	public ListView lv_weekly;
	public ListViewWLAdapter adapter;
	public List<Weekly> datas;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weekly);
		ExitAppUtil.getInstance().addActivity(this);
//		initData();
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_today = (Button) findViewById(R.id.btn_today);
		btn_weekly = (Button) findViewById(R.id.btn_weekly);
		btn_discover = (Button) findViewById(R.id.btn_discover);
		lv_weekly = (ListView) findViewById(R.id.ll_weekly);
		adapter = new ListViewWLAdapter(WeeklyActivity.this);
		adapter.notifyDataSetChanged();
		lv_weekly.setAdapter(adapter);
		//对应放大镜按钮,可进行搜索
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WeeklyActivity.this,SearchActivity.class);
				startActivity(intent);
			}
		});
		//对应加号按钮，进入即可分享诗歌
		btn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WeeklyActivity.this,ShareActivity.class);
				startActivity(intent);
			}
		});
		btn_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WeeklyActivity.this,TodayActivity.class);
				startActivity(intent);
			}
		});
		btn_discover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WeeklyActivity.this,DiscoverActivity.class);
				startActivity(intent);
			}
		});
		initRightMenu();//加载左边滑动菜单
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

	//弹出左边菜单
	public void showLeftMenu(View view)
	{
		getSlidingMenu().showMenu();
	}
//	/*
//	 * 加载每周推送数据
//	 */
//	public List<Weekly> initData() {
//		datas = new ArrayList<Weekly>();
//		Weekly data1 = new Weekly();
//		data1.setWeek(20);
//		data1.setTheme("描写风景的诗");
//		data1.setWcontent(null);
//		data1.setWimage(R.drawable.iv_bgo);
//		datas.add(data1);
//		Weekly data2 = new Weekly();
//		data2.setWeek(19);
//		data2.setTheme("为什么我的眼里常含泪水");
//		data2.setWcontent(null);
//		data2.setWimage(R.drawable.iv_bgt);
//		datas.add(data2);
//		Weekly data3 = new Weekly();
//		data3.setWeek(18);
//		data3.setTheme("是魔法的特技");
//		data3.setWcontent(null);
//		data3.setWimage(R.drawable.iv_bgth);		
//		datas.add(data3);
//		return datas;
//	}

	/*
	 * 点击返回键不销毁代码
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

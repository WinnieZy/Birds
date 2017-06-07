package com.zy.birds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.User;
import com.zy.birds.R;
import com.zy.birds.fragment.DiscoverFragment;
import com.zy.birds.fragment.TodayFragment;
import com.zy.birds.fragment.WeeklyFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * 主界面，对应应用开启时的today，weekly，discover及左侧滑动菜单4个fragment
 */
public class MainActivity extends SlidingFragmentActivity implements OnClickListener{
	
	public static User loginUser;
	public static MainActivity currentMainActivity;
	public BirdsDB bridsDB;
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private TextView tView;
	private Button btn_today,btn_weekly,btn_discover;
	public Button btn_search,btn_share;
	public Intent intent;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		currentMainActivity=this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		loginUser = LoginActivity.loginUser;
		bridsDB = BirdsDB.getInstance(this);
		btn_today = (Button) findViewById(R.id.btn_today);
		btn_weekly = (Button) findViewById(R.id.btn_weekly);
		btn_discover = (Button) findViewById(R.id.btn_discover);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		tView = (TextView) findViewById(R.id.tv_today);
		tView.setText("Today");
		// 初始化SlideMenu
		initRightMenu();
		// 初始化ViewPager
		initViewPager();
		//点击底部today按钮跳转到第一个fragment
		btn_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mViewPager.setCurrentItem(0); 
			}
		});
		//点击底部weekly按钮跳转到第二个fragment
		btn_weekly.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mViewPager.setCurrentItem(1); 
			}
		});
		//点击底部discover按钮跳转到第三个fragment
		btn_discover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mViewPager.setCurrentItem(2); 
			}
		});

	}
	//初始化viewpager，加入today，weekly,discover三个碎片
	private void initViewPager()
	{
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		TodayFragment today = new TodayFragment();
		WeeklyFragment weekly = new WeeklyFragment();
		DiscoverFragment discover = new DiscoverFragment();
		mFragments.add(today);
		mFragments.add(weekly);
		mFragments.add(discover);
		/**
		 * 初始化Adapter
		 */
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mFragments.get(arg0);
			}
			//防止销毁fragment
			@Override  
			public void destroyItem(ViewGroup container, int position, Object object) 
			{  
			    super.destroyItem(container, position, object);  
			}  

		};
		mViewPager.setAdapter(mAdapter);
		//翻页监听，翻到第一页时将头部标题改为today，翻到第二页将头部标题改为weekly，翻到第三页将头部标题改为discover
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (position==0) {
					tView.setText("Today");
				}
				if (position==1) {
					tView.setText("Weekly");
				}
				if (position==2) {
					tView.setText("Discover");
				}
			}
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	//初始化左边弹出的菜单
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
	//显示左边菜单
	public void showLeftMenu(View view)
	{
		getSlidingMenu().showMenu();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_share://右上角加号为分享按钮
			intent = new Intent(MainActivity.this,ShareActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_search://右上角放大镜为搜索按钮
			Log.d("TodayActivity", "btn_search");
			intent = new Intent(MainActivity.this,SearchActivity.class);
			startActivity(intent);
			break;			
			
		default:
			break;
		}		
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

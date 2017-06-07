package com.zy.birds.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zy.birds.R;
import com.zy.birds.Model.User;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.ToastUtil;

/*
 * 已被todayfragment取代 首页today推送的每天一首诗
 */
public class TodayActivity extends SlidingFragmentActivity implements OnClickListener{

	public Button btn_menu,btn_search,btn_share,btn_today,btn_weekly,btn_discover;
	public Intent intent;
	public static User loginUser = LoginActivity.loginUser;//拿到当前登录中的用户
	private long exitTime = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_today);
		ExitAppUtil.getInstance().addActivity(this);
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_today = (Button) findViewById(R.id.btn_today);
		btn_weekly = (Button) findViewById(R.id.btn_weekly);
		btn_discover = (Button) findViewById(R.id.btn_discover);
		btn_share.setOnClickListener(this);
		btn_weekly.setOnClickListener(this);
		btn_discover.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		initRightMenu();		//初始化菜单			
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

	/*
	 * 点击菜单相应，弹出菜单
	 */
	public void showLeftMenu(View view)
	{
		getSlidingMenu().showMenu();
	}

	public void onClick(View v) {
		switch (v.getId()) {
			
		case R.id.btn_share://右上角加号为分享按钮
			intent = new Intent(TodayActivity.this,ShareActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btn_weekly:
			intent = new Intent(TodayActivity.this,WeeklyActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btn_discover:
			intent = new Intent(TodayActivity.this,DiscoverActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_search://右上角放大镜为搜索按钮
			Log.d("TodayActivity", "btn_search");
			intent = new Intent(TodayActivity.this,SearchActivity.class);
			startActivity(intent);
			break;			
			
		default:
			break;
		}

	}
	/*
	 * today页面点击两次返回键则退出应用
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){ 
            if((System.currentTimeMillis()-exitTime) > 2000){ 
                ToastUtil.showMessage(getApplicationContext(),"再按一次返回键退出应用");
                exitTime = System.currentTimeMillis(); 
            } else {
            ExitAppUtil.getInstance().exit();
            //finish();
            //System.exit(0);
            }
            return true; 
        }
        return super.onKeyDown(keyCode, event);
    }		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

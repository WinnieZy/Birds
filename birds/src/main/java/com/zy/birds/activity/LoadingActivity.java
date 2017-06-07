package com.zy.birds.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.R;
import com.zy.birds.util.HttpCallBackListener;
import com.zy.birds.util.HttpUtil;
import com.zy.birds.util.JsonUtil;
import com.zy.birds.util.ToastUtil;

/*
 * 应用初始化界面
 */
public class LoadingActivity extends Activity {

	public BirdsDB birdsDB;
	
	public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
			String errormsg = (String) msg.obj;
			ToastUtil.showMessage(LoadingActivity.this, errormsg);
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		birdsDB = BirdsDB.getInstance(this);
		//在初始化界面开始从网络下载weekly的内容并存入数据库
		String address = "http://qtb.quantacenter.com/Birds_proj/index.php/Home/Api/weekly";
		HttpUtil.sendGetHttpRequest(address, new HttpCallBackListener() {
			
			@Override
			public void onFinish(String response) {
				JsonUtil.handleWeeklyResponse(response);
			}
			
			@Override
			public void onError(Exception e) {
				String error = "wf连接服务器失败";
				Message message = new Message();
				message.obj = error;
				handler.sendMessage(message);	
				Log.d("sad", "error");
			}
		});
		//loading图片显示3秒
		new Handler().postDelayed(new Runnable(){ 
			public void run() { 
				if (LoginActivity.loginUser!=null) {
					//当前登录用户不为空则直接进入主界面
					Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else {
					//当前登录为空则进入登录界面
					Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}
		    }      
		}, 3000); 
	}
	
	
//	private Handler mHandler = new Handler(); 
//	//... final SplashFragment splashFragment = new SplashFragment(); 
//	final FragmentTransaction transaction = getFragmentManager().beginTransaction(); 
//	transaction.replace(R.id.frame, splashFragment); transaction.commit(); 
//	//... mHandler.postDelayed(new DelayRunnable(this, splashFragment, mProgressBar), 2500); 
//	//... static class DelayRunnable implements Runnable 
//	{ private WeakReference contextRef; 
//	private WeakReference fragmentRef;
//	 private WeakReference progressBarRef; 
//	public DelayRunnable(Context context, SplashFragment splashFragment, ProgressBar progressBar) { 
//	contextRef = new WeakReference(context); 
//	fragmentRef = new WeakReference(splashFragment); 
//	progressBarRef = new WeakReference(progressBar); } 
//	@Override public void run() { 
//	ProgressBar progressBar = progressBarRef.get(); 
//	if (progressBar != null) progressBar.setVisibility(View.GONE); 
//	Activity context = (Activity) contextRef.get(); 
//	if (context != null) {
//	 SplashFragment splashFragment = fragmentRef.get(); 
//	if (splashFragment == null) return; 
//	final FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
//	 transaction.remove(splashFragment); 
//	transaction.commit(); 
//	} } } 
//	@Override protected void onDestroy() { 
//	super.onDestroy();
//	 mHandler.removeCallbacksAndMessages(null); } 
}

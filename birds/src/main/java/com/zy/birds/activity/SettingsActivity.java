package com.zy.birds.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.User;
import com.zy.birds.R;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.HttpCallBackListener;
import com.zy.birds.util.HttpUtil;
import com.zy.birds.util.JsonUtil;
import com.zy.birds.util.ToastUtil;

/*
 * 个人设置页面
 */
public class SettingsActivity extends Activity implements OnClickListener{

	public static User loginUser;
	public static Bitmap loginUserImage = LoginActivity.loginUserImage;
	public BirdsDB bridsDB;
	public ImageView sethead;
	public Button btn_exitac,btn_changeac,btn_sure,btn_back;
	public EditText et_chname,et_chemail,et_chphone;
	public TextView tv_chpass,et_grade,et_about,et_connect;
	public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
			String errormsg = (String) msg.obj;
			ToastUtil.showMessage(SettingsActivity.this, errormsg);
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		loginUser = LoginActivity.loginUser;
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		Log.d("asd", loginUser.getUname());
		Log.d("asd", loginUser.getUpass());
		sethead = (ImageView) findViewById(R.id.iv_head);
		sethead.setImageBitmap(loginUserImage);
		btn_exitac = (Button) findViewById(R.id.exit_ac);
		btn_changeac = (Button) findViewById(R.id.change_ac);
		btn_sure = (Button) findViewById(R.id.sure);
		btn_back = (Button) findViewById(R.id.btn_back);
		et_chname = (EditText) findViewById(R.id.et_chname);
		et_chname.setText(loginUser.getUname());
		et_chname.setFocusableInTouchMode(false);//设置editText的不可编辑
		et_chname.clearFocus();
		et_chemail = (EditText) findViewById(R.id.et_chemail);
		et_chemail.setText(loginUser.getUemail());
		et_chemail.setFocusableInTouchMode(false);//设置editText的不可编辑
		et_chemail.clearFocus();
		et_chphone = (EditText) findViewById(R.id.et_chphone);
		et_chphone.setText(loginUser.getUphone());
		et_chphone.setFocusableInTouchMode(false);//设置editText的不可编辑
		et_chphone.clearFocus();
		et_grade = (TextView) findViewById(R.id.grade);
		et_about = (TextView) findViewById(R.id.about);
		et_connect = (TextView) findViewById(R.id.connect);
		tv_chpass = (TextView) findViewById(R.id.tv_chpass);
		sethead.setOnClickListener(this);
		btn_exitac.setOnClickListener(this);
		btn_changeac.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		et_chname.setOnClickListener(this);
		et_chemail.setOnClickListener(this);
		et_chphone.setOnClickListener(this);
		et_grade.setOnClickListener(this);
		et_about.setOnClickListener(this);
		et_connect.setOnClickListener(this);
		tv_chpass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		
		switch (v.getId()) {
			case R.id.iv_head:
				
				break;
			case R.id.btn_back:	
				finish();
			    break;
			//确定时通知数据库改变用户信息，由于后台没有设置email和phone这两个属性，此处为安卓端实现
			case R.id.sure:
				String newUname = et_chname.getText().toString();
//				String newEmail = et_chemail.getText().toString();
//				String newPhone = et_chphone.getText().toString();
				loginUser.setUname(newUname);
				bridsDB.updateUser(loginUser);
				break;
			//退出账号
			case R.id.exit_ac:
				String address = "http://qtb.quantacenter.com/Birds_proj/index.php/Home/Api/logout";
				HttpUtil.sendGetHttpRequest(address, new HttpCallBackListener() {
					
					@Override
					public void onFinish(String response) {
						// TODO Auto-generated method stub
						String status = JsonUtil.handleLogoutResponse(response);
						Log.d("SettingActivity", status);
						if (status.equals("success")) {
							LoginActivity.loginUser = null;
							Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
							MainActivity.currentMainActivity.finish();
							startActivity(intent);
						}
						if (status.equals("error")) {
							ToastUtil.showMessage(SettingsActivity.this, "登出失败,请重试");
						}
					}
					
					@Override
					public void onError(Exception e) {
						// TODO Auto-generated method stub
						String error = "Logout连接服务器失败";
						Message message = new Message();
						message.obj = error;
						handler.sendMessage(message);	
						Log.d("Main", "error");
					}
				});

				break;
			case R.id.change_ac:
				
				break;
			//修改昵称，点击时将对应edittext修改为可编辑
			case R.id.et_chname:
				et_chname.setFocusableInTouchMode(true);//点击对应edittext设置editText可编辑
				et_chname.requestFocus();
				et_chname.setSelection(et_chname.getText().length());
				imm.showSoftInput(et_chname, 0);
				//监听editText的编辑事件，点击弹出软键盘
				et_chname.setOnEditorActionListener(new OnEditorActionListener() {
					
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						boolean handled = false;
				        if (actionId == EditorInfo.IME_ACTION_DONE) {
				        	imm.hideSoftInputFromWindow(et_chname.getWindowToken(), 0);
				            handled = true;
				        }
				        return handled;
	
					}
				});
				break;
				//修改邮箱，点击时将对应edittext修改为可编辑
			case R.id.et_chemail:
				et_chemail.setFocusableInTouchMode(true);
				et_chemail.requestFocus();
				et_chemail.setSelection(et_chemail.getText().length());
				imm.showSoftInput(et_chemail, 0);
				et_chemail.setOnEditorActionListener(new OnEditorActionListener() {
					
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						boolean handled = false;
				        if (actionId == EditorInfo.IME_ACTION_DONE) {
				        	imm.hideSoftInputFromWindow(et_chemail.getWindowToken(), 0);
				            handled = true;
				        }
				        return handled;
	
					}
				});
				break;
				//修改手机号，点击时将对应edittext修改为可编辑
			case R.id.et_chphone:
				et_chphone.setFocusableInTouchMode(true);
				et_chphone.requestFocus();
				et_chphone.setSelection(et_chphone.getText().length());
				imm.showSoftInput(et_chphone, 0);
				et_chphone.setOnEditorActionListener(new OnEditorActionListener() {
					
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						boolean handled = false;
				        if (actionId == EditorInfo.IME_ACTION_DONE) {
				        	imm.hideSoftInputFromWindow(et_chphone.getWindowToken(), 0);
				            handled = true;
				        }
				        return handled;
	
					}
				});			
				break;
			case R.id.grade:
				
				break;
			case R.id.about:
				
				break;
			case R.id.connect:
				
				break;
			case R.id.tv_chpass:
				Intent intent = new Intent(SettingsActivity.this,PasswordActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
		
	}
}

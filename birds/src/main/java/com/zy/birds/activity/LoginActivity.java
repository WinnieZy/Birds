package com.zy.birds.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.User;
import com.zy.birds.R;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.ToastUtil;

import java.lang.ref.WeakReference;

/*
 * 登录页面
 */
public class LoginActivity extends Activity {

	public static final int CLEAR_TEXT = 1;
	public static final int PASS_WRONG = 2;
	public static final int CONNECT_ERROR = 3;
	public static final int UNAME_WRONG = 4;
	public static final int UPASS_WRONG = 5;
	public static final int NULLINPUT = 6;
	public static Context context;
	public BirdsDB bridsDB;
	public Button btn_login;
	public Button btn_clearAccount;
	public EditText et_account;
	public EditText et_password;
	public TextView tv_register_link;
	public static User loginUser;
	public static Bitmap loginUserImage;
	MyHandler handler = new MyHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		bridsDB = BirdsDB.getInstance(this);
		ExitAppUtil.getInstance().addActivity(this);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_clearAccount = (Button) findViewById(R.id.btn_clearAccount);
		tv_register_link = (TextView) findViewById(R.id.tv_register_link);
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.edt_password);		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				final String userName = et_account.getText().toString();
				final String password = et_password.getText().toString();
				if (userName.equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "用户名不能为空";
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}else if (password.equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "密码不能为空";
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}
//				}else {
//					//发送用户名密码至网络端，正确则返回所有用户信息存入数据库
//					String address = "http://qtb.quantacenter.com/Birds_proj/index.php/Home/Api/check_login";
//					address = address +"?username="+userName+"&&password="+password;
//					HttpUtil.sendGetHttpRequest(address, new HttpCallBackListener() {
//
//						@Override
//						public void onFinish(String response) {
//							Message message = new Message();
//							int result = JsonUtil.handleLoginResponse(response);
//							if (result==1) {
//								//登录成功
////								User user= new User(userName, password,"","","");
////								if (bridsDB.queryUserByName(userName) != null) {
////									bridsDB.updateUser(user);
////									loginUser = bridsDB.queryUser(user);
////								} else {
////									bridsDB.addUser(user);
////									loginUser = bridsDB.queryUser(user);
////								}
////								Intent intent = new Intent(LoginActivity.this,MainActivity.class);
////								startActivity(intent);
////								finish();
//								loginUser=bridsDB.queryUserByUnameUpass(userName, password);
//								loginUserImage = ImageUtil.loadImage(loginUser.getUimage());
//								Log.d("LoginActivity", loginUser.getUimage());
//								Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//								startActivity(intent);
//								finish();
//							} else if (result==2) {
//								//用户名错误
//								message.what = UNAME_WRONG;
//								message.obj = "用户名错误";
//								handler.sendMessage(message);
//							} else {
//								//密码错误
//								message.what = UPASS_WRONG;
//								message.obj = "密码错误";
//								handler.sendMessage(message);
//							}
//						}
//
//						@Override
//						public void onError(Exception e) {
//							String error = "Login连接服务器失败";
//							Message message = new Message();
//							message.what = CONNECT_ERROR;
//							message.obj = error;
//							handler.sendMessage(message);
//							Log.d("Main", "error");
//						}
//					});
//				}
				/*
				 * 在数据库中查找用户信息，找到即登录
				 */
				if (bridsDB.queryUserByUnameUpass(userName, password)!=null) {
					loginUser=bridsDB.queryUserByUnameUpass(userName, password);
                    loginUserImage = BitmapFactory.decodeResource(getResources(),R.drawable.head);
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else {
					ToastUtil.showMessage(LoginActivity.this, "账号或密码错误，登录失败");
					new Thread(new Runnable() {

						@Override
						public void run() {
							Message message = new Message();
							message.what = PASS_WRONG;
							handler.sendMessage(message);
						}
					}).start();
				}
			}
		});
		/*
		 * 点击进入注册页面
		 */
		tv_register_link.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		tv_register_link.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); 
		btn_clearAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Message message = new Message();
						message.what = CLEAR_TEXT;
						handler.sendMessage(message);					
					}
				}).start();
				
			}
		});
	}
	
	static class MyHandler extends Handler{
		WeakReference<LoginActivity> mActivity; 
		MyHandler(LoginActivity activity) { 
			mActivity = new WeakReference<LoginActivity>(activity); 
		}
		@Override
		public void handleMessage(Message msg) {
			LoginActivity thisActivity = mActivity.get();
			switch (msg.what) {
			case CLEAR_TEXT:
				thisActivity.et_account.setText("");
				break;
			case PASS_WRONG:
				thisActivity.et_password.setText("");
				break;
			case CONNECT_ERROR:
				String errormsg = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, errormsg);
				break;
			case UNAME_WRONG:
				String unamewrong = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, unamewrong);
				thisActivity.et_account.setText("");
				thisActivity.et_password.setText("");
				break;
			case UPASS_WRONG:
				String upasswrong = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, upasswrong);
				thisActivity.et_password.setText("");
				break;
			case NULLINPUT:
				String nullinput = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, nullinput);
				break;
			default:				
				break;
			}
		}
	}
	
}
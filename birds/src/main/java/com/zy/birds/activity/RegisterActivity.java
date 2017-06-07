package com.zy.birds.activity;

import android.app.Activity;
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
import java.util.Random;

/*
 * 注册页面
 */
public class RegisterActivity extends Activity {

	public static final int CLEAR_TEXT = 1;
	public static final int UNAMEEXIST = 2;
	public static final int CHECKCODE = 3;
	public static final int REGISTERSUCCESS = 4;
	public static final int REGISTERFAIL = 5;
	public static final int PASSWORDWRONG = 6;
	public static final int CHECKCODEWRONG = 7;
	public static final int NULLINPUT = 8;
	public static final int CONNECT_ERROR = 9;
	public BirdsDB bridsDB;
	public EditText et_rName;
	public EditText et_rPW;	
	public EditText et_rSurePW;	
	public EditText et_rCheckCode;	
	public Button btn_register;
	public Button btn_clearAccount_r;
	public TextView tv_checkCode;
	public User registerUser;
	MyHandler handler = new MyHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		bridsDB = BirdsDB.getInstance(this);
		setContentView(R.layout.activity_register);
		ExitAppUtil.getInstance().addActivity(this);
		et_rName = (EditText) findViewById(R.id.et_rName);
		et_rPW = (EditText) findViewById(R.id.et_rPW);
		et_rSurePW = (EditText) findViewById(R.id.et_rSurePW);
		et_rCheckCode = (EditText) findViewById(R.id.et_rCheckCode);
		tv_checkCode = (TextView) findViewById(R.id.tv_checkCode);
		btn_clearAccount_r = (Button) findViewById(R.id.btn_clearAccount_r);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if (et_rName.getText().toString()==null||et_rName.getText().toString().equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "请输入账号";
							handler.sendMessage(message);					
						}
					}).start();	
					return;
				}	
				if (et_rPW.getText().toString()==null||et_rPW.getText().toString().equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "请输入密码";
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}		
				if (et_rSurePW.getText().toString()==null||et_rSurePW.getText().toString().equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "请确认密码";
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}
				if (et_rCheckCode.getText().toString()==null||et_rCheckCode.getText().toString().equals("")) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = NULLINPUT;
							message.obj = "请输入验证码";
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}
				if (!et_rPW.getText().toString().equals(et_rSurePW.getText().toString())) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = PASSWORDWRONG;
							handler.sendMessage(message);					
						}
					}).start();
					return;
				}				
				if (!tv_checkCode.getText().toString().equalsIgnoreCase(et_rCheckCode.getText().toString())) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = CHECKCODEWRONG;
							handler.sendMessage(message);					
						}
					}).start();		
					return;
				}
				String userName = et_rName.getText().toString();
				String password = et_rPW.getText().toString();
//				//连接服务器将用户新注册信息转为Json字符串发送至后台接收
//				String address = "http://qtb.quantacenter.com/Birds_proj/index.php/Home/Api/check_register";
//				String jsonString = JsonUtil.createUserJSONString(userName,password);
//				HttpUtil.sendPostHttpRequest(address, jsonString,new HttpCallBackListener() {
//
//					@Override
//					public void onFinish(String response) {
//						Message message = new Message();
//						boolean result = JsonUtil.handleRegisterResponse(response);
//						if (result) {
//							//注册成功
//							message.what = REGISTERSUCCESS;
//							message.obj = "注册成功";
//							handler.sendMessage(message);
//							finish();
//						} else {
//							//用户名已存在，注册失败
//							message.what = REGISTERFAIL;
//							handler.sendMessage(message);
//						}
//					}
//
//					@Override
//					public void onError(Exception e) {
//						String error = "连接服务器失败";
//						Message message = new Message();
//						message.what = CONNECT_ERROR;
//						message.obj = error;
//						handler.sendMessage(message);
//						Log.d("Main", "error");
//					}
//				});
				registerUser = new User(userName, password,null,null,null);
				if (bridsDB.queryUserByName(userName)!=null) {
					ToastUtil.showMessage(RegisterActivity.this, "用户名已存在，请重新输入");
					new Thread(new Runnable() {

						@Override
						public void run() {
							Message message = new Message();
							message.what = CLEAR_TEXT;
							handler.sendMessage(message);
						}
					}).start();
				}else if (bridsDB.queryUser(registerUser)!=null) {
					ToastUtil.showMessage(RegisterActivity.this, "用户已存在，注册失败");
					new Thread(new Runnable() {

						@Override
						public void run() {
							Message message = new Message();
							message.what = REGISTERFAIL;
							handler.sendMessage(message);
						}
					}).start();
				}else if (registerUser==null) {
					ToastUtil.showMessage(RegisterActivity.this, "注册用户失败");
					setCheckCode();
				}else {
					bridsDB.addUser(registerUser);
					ToastUtil.showMessage(RegisterActivity.this, "注册成功");
					finish();
				}
			}
		});
		tv_checkCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Message message = new Message();
						message.what = CHECKCODE;
						handler.sendMessage(message);					
					}
				}).start();								
			}
		});
		setCheckCode();
		btn_clearAccount_r.setOnClickListener(new OnClickListener() {
			
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
	
	/*
	 * 得到验证码
	 */
	public void setCheckCode(){
		String checkCode = getCkeckCode();
		tv_checkCode.setText(checkCode);
	}
	/*
	 * 产生验证码
	 */
	public String getCkeckCode(){
		String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String str2[] = str.split(",");//将字符串以,分割  
        Random rand = new Random();//创建Random类的对象rand  
        int index = 0;  
        String randStr = "";//创建内容为空字符串对象randStr    
        for (int i=0; i<4; ++i)  
        {  
            index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index  
            randStr += str2[index];//将对应索引的数组与randStr的变量值相连接  
        }  
        return randStr;//输出所求的验证码的值           		
	}
	
	static class MyHandler extends Handler{
		WeakReference<RegisterActivity> mActivity; 
		MyHandler(RegisterActivity activity) { 
			mActivity = new WeakReference<RegisterActivity>(activity); 
		}
		@Override
		public void handleMessage(Message msg) {
			RegisterActivity thisActivity = mActivity.get();
			switch (msg.what) {
			case CLEAR_TEXT:
				thisActivity.et_rName.setText("");
				break;
			case UNAMEEXIST:
				thisActivity.et_rName.setText("");
				thisActivity.et_rCheckCode.setText("");
				thisActivity.setCheckCode();
				break;
			case CHECKCODE:
				thisActivity.setCheckCode();	
				break;
			case REGISTERSUCCESS:
				ToastUtil.showMessage(thisActivity, "注册成功");
				break;	
			case REGISTERFAIL:
				ToastUtil.showMessage(thisActivity, "用户已存在，注册失败");
				thisActivity.et_rName.setText("");
				thisActivity.et_rPW.setText("");
				thisActivity.et_rSurePW.setText("");
				thisActivity.et_rCheckCode.setText("");
				thisActivity.setCheckCode();
				break;	
			case PASSWORDWRONG:
				ToastUtil.showMessage(thisActivity, "确认密码错误，请重新输入");
				thisActivity.et_rSurePW.setText("");
				thisActivity.et_rCheckCode.setText("");
				thisActivity.setCheckCode();
				break;
			case CHECKCODEWRONG:
				ToastUtil.showMessage(thisActivity, "验证码输入错误，请重新输入");
				thisActivity.et_rCheckCode.setText("");
				thisActivity.setCheckCode();
				break;							
			case NULLINPUT:
				String nullinput = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, nullinput);
				break;
			case CONNECT_ERROR:
				String errormsg = (String) msg.obj;
				ToastUtil.showMessage(thisActivity, errormsg);
				break;
			default:
				break;
			}
		}
	}	
	
}

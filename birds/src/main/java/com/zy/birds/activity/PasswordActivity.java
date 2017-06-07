package com.zy.birds.activity;

import java.lang.ref.WeakReference;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.User;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/*
 * 修改密码页面
 */
public class PasswordActivity extends Activity {

	public static final int OLD_WRONG = 1;
	public static final int SURE_WRONG = 2;
	public BirdsDB bridsDB;
	public static User loginUser;
	public Button btn_back,btn_sure;
	public EditText et_oldpass, et_newpass, et_surepass;
	public CheckBox cb_showpassword;
	MyHandler handler = new MyHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		setContentView(R.layout.activity_password);
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.suretochange);
		et_oldpass = (EditText) findViewById(R.id.et_oldpass);
		et_newpass = (EditText) findViewById(R.id.et_newpass);
		et_surepass = (EditText) findViewById(R.id.et_surepass);
		cb_showpassword = (CheckBox) findViewById(R.id.showpassword); 
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		//显示密码checkbox监听
		cb_showpassword.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() { 
			
		    @Override
		    public void onCheckedChanged(CompoundButton arg0, boolean arg1) { 
		        if (cb_showpassword.isChecked()) { 
		            /* 设定EditText的内容为可见的 */ 
		        	et_oldpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); 
		        	et_newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); 
		        	et_surepass.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); 
		        } else { 
		            /* 设定EditText的内容为隐藏的 */ 
		        	et_oldpass.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
		        	et_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
		        	et_surepass.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
		        } 
		    }

		}); 

		//按下确定按钮时开始判断信息有效性，成功则成功修改密码，失败则根据相应情况Toast信息
		btn_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String oldpass = et_oldpass.getText().toString();
				String newpass = et_newpass.getText().toString();
				String surepass = et_surepass.getText().toString();
				if (!oldpass.equals(loginUser.getUpass())) {
					ToastUtil.showMessage(PasswordActivity.this, "原始密码输入有误，请重新输入");
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = OLD_WRONG;
							handler.sendMessage(message);					
						}
					}).start();
				}else if (newpass.equals(oldpass)) {
					ToastUtil.showMessage(PasswordActivity.this, "新密码不能与原密码相同，请重新输入");
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = SURE_WRONG;
							handler.sendMessage(message);					
						}
					}).start();					
				}else if(!newpass.equals(surepass)){					
					ToastUtil.showMessage(PasswordActivity.this, "两次密码不相同，请重新输入");
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Message message = new Message();
							message.what = SURE_WRONG;
							handler.sendMessage(message);					
						}
					}).start();															
				}else {
					loginUser.setUpass(newpass);
					bridsDB.updateUser(loginUser);		
					finish();
				}

			}
		});
	}
	
	static class MyHandler extends Handler{
		WeakReference<PasswordActivity> mActivity; 
		MyHandler(PasswordActivity activity) { 
			mActivity = new WeakReference<PasswordActivity>(activity); 
		}
		@Override
		public void handleMessage(Message msg) {
			PasswordActivity thisActivity = mActivity.get();
			switch (msg.what) {
			case OLD_WRONG:
				thisActivity.et_oldpass.setText("");
				break;
			case SURE_WRONG:
				thisActivity.et_newpass.setText("");
				thisActivity.et_surepass.setText("");
				break;
			default:
				break;
			}
		}
	}
}

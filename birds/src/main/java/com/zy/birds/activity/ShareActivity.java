package com.zy.birds.activity;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.User;
import com.zy.birds.util.DateUtil;
import com.zy.birds.util.ExitAppUtil;
import com.zy.birds.util.ToastUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/*
 * 分享诗歌页面，对应右上角加号
 */
public class ShareActivity extends Activity {

	public static User loginUser;
	public static Bitmap loginUserImage;
	public BirdsDB bridsDB;
	public Button btn_back, btn_sharesend;
	public ImageView head;
	public RadioGroup radioGroup;
	public RadioButton rb_yuan,rb_ban;
	public EditText et_sharept,et_sharepc;
	public int shareps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		loginUserImage = LoginActivity.loginUserImage;
		setContentView(R.layout.activity_share);
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_sharesend = (Button) findViewById(R.id.btn_sharesend);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		head = (ImageView) findViewById(R.id.iv_dc_head);
		head.setImageBitmap(loginUserImage);
		rb_yuan = (RadioButton) findViewById(R.id.rb_yuan);
		rb_ban = (RadioButton) findViewById(R.id.rb_ban);
		et_sharept = (EditText) findViewById(R.id.share_pt);
		et_sharepc = (EditText) findViewById(R.id.share_pc);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
				if (checkId == R.id.rb_yuan) {
					shareps=1;//1表示原创
				} else if(checkId == R.id.rb_ban){
					shareps=0;//0表示搬运
				}
				
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		//发布按钮的监听
		btn_sharesend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (rb_yuan.isChecked()==false&&rb_ban.isChecked()==false) {
					ToastUtil.showMessage(ShareActivity.this, "请选择原创或搬运");
				}else {
					String sharept = et_sharept.getText().toString();
					String sharepc = et_sharepc.getText().toString();
					if (sharept.equals("")) {
						ToastUtil.showMessage(ShareActivity.this, "请补充诗名");
					}else if (sharepc.equals("")) {
						ToastUtil.showMessage(ShareActivity.this, "诗的内容不能为空");
					}else {
						String sharepa = loginUser.getUname();
						String sharepd = DateUtil.getDateString();
						Poem poem = new Poem(sharept, sharepa, sharepc, shareps, sharepd, 0, 0, 0);
						bridsDB.addPoem(poem);
						if (bridsDB.queryPoemByTitleAuthorDate(poem.getPtitle(), loginUser.getUname(),poem.getPpdate())!=null) {
							ToastUtil.showMessage(ShareActivity.this, "分享成功");
							finish();
						}else {
							ToastUtil.showMessage(ShareActivity.this, "分享失败，请重试");
						}							
					}				
				}
			}
		});
	}
}

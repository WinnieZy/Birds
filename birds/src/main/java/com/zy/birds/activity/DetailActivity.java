package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.CommentDIY;
import com.zy.birds.Model.CommentData;
import com.zy.birds.Model.DiscoverData;
import com.zy.birds.Model.FavoriteData;
import com.zy.birds.Model.MyshareData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.activity.ListViewCMAdapter.Callback;
import com.zy.birds.util.DateUtil;
import com.zy.birds.util.ExitAppUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 每首诗对应的详情页
 */
public class DetailActivity extends Activity implements Callback{

	public BirdsDB bridsDB;
	public Poem poem;
	public DiscoverData discoverData;
	public FavoriteData favoriteData;
	public MyshareData myshareData;
	public static User loginUser;
	public Button btn_back,btn_share,btn_sendCM;
	public EditText cmEditText;
	public TextView tv_comment;	
	public ListView lv_comment;
	public ListViewCMAdapter adapter;
	public List<CommentData> datas = new ArrayList<CommentData>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginUser = LoginActivity.loginUser;
		setContentView(R.layout.activity_detail);
		ExitAppUtil.getInstance().addActivity(this);
		bridsDB = BirdsDB.getInstance(this);
		Intent intent = getIntent();
		//判断接受到的数据来自哪个活动
		discoverData = (DiscoverData) intent.getSerializableExtra("discoverData");
		favoriteData = (FavoriteData) intent.getSerializableExtra("favoriteData");
		myshareData = (MyshareData) intent.getSerializableExtra("myshareData");
		//将得到的数据转换为discover详细数据用于detail页面展示
		if (favoriteData!=null&&discoverData==null) {
			discoverData = transformFtoD(favoriteData);
		}
		if (myshareData!=null&&discoverData==null) {
			discoverData = transformMtoD(myshareData);
		}
//		poem = (Poem) intent.getSerializableExtra("poem");
		initdata();
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_share = (Button) findViewById(R.id.btn_share);
//		btn_sendCM = (Button) findViewById(R.id.btn_sendcm);
//		cmEditText = (EditText) findViewById(R.id.et_comment);
		tv_comment= (TextView) findViewById(R.id.tv_comment);
		lv_comment= (ListView) findViewById(R.id.lv_comment);
		adapter = new ListViewCMAdapter(DetailActivity.this, datas,discoverData,this);
		adapter.notifyDataSetChanged();
		lv_comment.setAdapter(adapter);
//		cmEditText.setMinLines(4);
//		btn_sendCM.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				String text = cmEditText.getText().toString();
//				if (text=="") {
//					ToastUtil.showMessage(DetailActivity.this, "评论内容不能为空");
//				} else {
//					int uid = loginUser.getId();
//					int pid = poem.getId();
//					String cpdate = DateUtil.getDateString();
//					Comment comment = new Comment(uid, pid, text, cpdate);
//					bridsDB.addComment(comment);
//					ToastUtil.showMessage(DetailActivity.this, "评论成功");
//					lv_comment.setAdapter(adapter);
//				}
//			}
//		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();			
			}
		});
		btn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetailActivity.this,ShareActivity.class);
				startActivity(intent);
			}
		});
	}

	/*
	 * 加载评论列表所有评论数据
	 */
	private List<CommentData> initdata() {
		poem = bridsDB.queryPoemById(discoverData.getDpid());
		int pid = poem.getId();
		datas = new ArrayList<CommentData>();
		List<CommentDIY> commentDIYs = bridsDB.queryCommentByPid(pid);	
		for (CommentDIY commentDIY : commentDIYs) {
			CommentData data = new CommentData ();
			data.setHeadImage(R.drawable.head);
			data.setuName(commentDIY.getUname());
			data.setClockImage(R.drawable.clock);			
			data.setDate(DateUtil.formatDisplayTime(commentDIY.getCpdate()));
			data.setComment(commentDIY.getCcontent());
			datas.add(data);
		}
		return datas;
	}

	/*
	 * FavoriteData转换为DiscoverData
	 */
	public DiscoverData transformFtoD(FavoriteData favoriteData){
		Poem poem = bridsDB.queryPoemById(favoriteData.getF_pid());	
		Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
		DiscoverData discoverData = new DiscoverData();
		discoverData.setDpid(poem.getId());
		discoverData.sethImage(bridsDB.queryUserByName(poem.getPauthor()).getUimage());
		discoverData.setUserName(poem.getPauthor());
		discoverData.setcImage(R.drawable.clock);
		discoverData.setTime(poem.getPpdate());
		discoverData.setPtitle(poem.getPtitle());
		discoverData.setPcontent(poem.getPcontent());
		discoverData.setHeart(String.valueOf(poem.getPlcount()));
		discoverData.setComment(String.valueOf(poem.getPccount()));
		discoverData.setStar(String.valueOf(poem.getPscount()));
		if (relationship!=null) {
			discoverData.setHEART_STATUS(relationship.getHearted());
			discoverData.setSTAR_STATUS(relationship.getStared());
		} 
		return discoverData;
	}
	/*
	 * MyshareData转换为DiscoverData
	 */	
	public DiscoverData transformMtoD(MyshareData myshareData){
		Poem poem = bridsDB.queryPoemById(myshareData.getMs_pid());	
		Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
		DiscoverData discoverData = new DiscoverData();
		discoverData.setDpid(poem.getId());
		discoverData.sethImage(bridsDB.queryUserByName(poem.getPauthor()).getUimage());
		discoverData.setUserName(poem.getPauthor());
		discoverData.setcImage(R.drawable.clock);
		discoverData.setTime(poem.getPpdate());
		discoverData.setPtitle(poem.getPtitle());
		discoverData.setPcontent(poem.getPcontent());
		discoverData.setHeart(String.valueOf(poem.getPlcount()));
		discoverData.setComment(String.valueOf(poem.getPccount()));
		discoverData.setStar(String.valueOf(poem.getPscount()));
		if (relationship!=null) {
			discoverData.setHEART_STATUS(relationship.getHearted());
			discoverData.setSTAR_STATUS(relationship.getStared());
		} 
		return discoverData;
	}	
	
	/*
	 * callback接口回调函数，用于判断adapter内点击事件
	 * 此处为点击评论按钮之后评论列表的即时更新
	 * (non-Javadoc)
	 * @see com.zy.birds.activity.ListViewCMAdapter.Callback#click(android.view.View)
	 */
	@Override
	public void click(View v) {
		initdata();
		discoverData.setComment(String.valueOf(Integer.parseInt(discoverData.getComment())+1));		
		adapter = new ListViewCMAdapter(DetailActivity.this, datas,discoverData,this);
		adapter.notifyDataSetChanged();
		lv_comment.setAdapter(adapter);
	}
}

package com.zy.birds.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.DiscoverData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.R;
import com.zy.birds.activity.DetailActivity;
import com.zy.birds.activity.ListViewDCAdapter;
import com.zy.birds.activity.ListViewDCAdapter.Callback;
import com.zy.birds.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * discover页面对应的碎片
 */
public class DiscoverFragment extends Fragment implements Callback{

	public BirdsDB bridsDB;
	public static User loginUser;
	public ListView lv_discover;
	public ListViewDCAdapter adapter;
	public List<DiscoverData> datas;
	public Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, container, false);
		loginUser = LoginActivity.loginUser;
		Log.d("Discover", loginUser.getUname());
		Log.d("Discover", loginUser.getUpass());
		bridsDB = BirdsDB.getInstance(context);
		getDatas();
		lv_discover = (ListView) view.findViewById(R.id.ll_discover);
		adapter = new ListViewDCAdapter(getActivity(), datas, this,loginUser);
		adapter.notifyDataSetChanged();
		lv_discover.setAdapter(adapter);
		/*
		 * 点击item跳转到诗歌详情页面
		 * 传输所点击item对应的对象给下一个活动
		 */
		lv_discover.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
//				Poem poem = BridsDB.getInstance(DiscoverActivity.this).queryPoemByPosition(position+1);
				DiscoverData discoverData = datas.get(datas.size()-1-position);
				Intent intent = new Intent(getActivity(),DetailActivity.class);
				Bundle mBundle = new Bundle();   
//				mBundle.putSerializable("poem", poem);
				mBundle.putSerializable("discoverData", discoverData);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		return view;
	}
	
	/*
	 * 每次重新返回当前活动时重新加载adapter以保证数据最新
	 * (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getDatas();
		adapter = new ListViewDCAdapter(getActivity(), datas, this,loginUser);
		adapter.notifyDataSetChanged();
		lv_discover.setAdapter(adapter);
		Log.d("DiscoverFragment", "onStart");
	}
	/*
	 * 从数据库中找出所有的诗的信息并转换为discoverData用于页面数据展示
	 */
	public List<DiscoverData> getDatas(){
		datas = new ArrayList<DiscoverData>();
		List<Poem> poems = bridsDB.queryAllPoems();
		for (Poem poem : poems) {
			Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
			DiscoverData data = new DiscoverData();
			data.setDpid(poem.getId());
			data.sethImage(bridsDB.queryUserByName(poem.getPauthor()).getUimage());
			data.setUserName(poem.getPauthor());
			data.setPsource(poem.getPsource());
			data.setcImage(R.drawable.clock);
			data.setTime(poem.getPpdate());
			data.setPtitle(poem.getPtitle());
			data.setPcontent(poem.getPcontent());
			data.setHeart(String.valueOf(poem.getPlcount()));
			data.setComment(String.valueOf(poem.getPccount()));
			data.setStar(String.valueOf(poem.getPscount()));
			if (relationship!=null) {
				data.setHEART_STATUS(relationship.getHearted());
				data.setSTAR_STATUS(relationship.getStared());
			} 
			datas.add(data);
		}
		return datas;
	}

	/*
	 * callback接口回调函数，用于点击adapter内评论按钮时得到相应数据传输给detail活动
	 * (non-Javadoc)
	 * @see com.zy.birds.activity.ListViewDCAdapter.Callback#click(android.view.View)
	 */
	@Override
	public void click(View v) {
		// TODO Auto-generated method stub
//		ToastUtil.showMessage(
//                DiscoverActivity.this,
//                "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
//                        + datas.get((Integer) v.getTag())
//                );	
//		Poem poem = BridsDB.getInstance(DiscoverActivity.this).queryPoemByPosition((Integer) v.getTag());
		DiscoverData discoverData = datas.get(datas.size()-1-(Integer) v.getTag());
		Intent intent = new Intent(getActivity(),DetailActivity.class);
		Bundle mBundle = new Bundle();   
		mBundle.putSerializable("discoverData", discoverData);
		intent.putExtras(mBundle);
		startActivity(intent);
	}
}

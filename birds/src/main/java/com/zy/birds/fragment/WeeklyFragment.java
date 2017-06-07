package com.zy.birds.fragment;

import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Weekly;
import com.zy.birds.Model.WeeklyData;
import com.zy.birds.activity.PicAdapter;
import com.zy.birds.activity.WcontentActivity;
import com.zy.birds.util.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/*
 * 对应weekly页面的碎片
 */
public class WeeklyFragment extends Fragment {

	public Context context;
	public Button btn_search,btn_share;
	public ListView lv_weekly;
	public static PicAdapter adapter;
	public BirdsDB birdsDB = BirdsDB.getInstance(context);
	public List<Weekly> datas = birdsDB.queryAllWeeklys();
	public WeeklyData wimage;
	public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
			String errormsg = (String) msg.obj;
			ToastUtil.showMessage(getActivity(), errormsg);
        }
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weekly, container, false);
		lv_weekly = (ListView) view.findViewById(R.id.ll_weekly);
//		getDatas();		
//		String address = "http://10.173.116.109/Birds_proj/index.php/Home/Api/weekly";
//		HttpUtil.sendGetHttpRequest(address, new HttpCallBackListener() {
//			
//			@Override
//			public void onFinish(String response) {
//				JsonUtil.handleWeeklyResponse(response);
//			}
//			
//			@Override
//			public void onError(Exception e) {
//				String error = "wf连接服务器失败";
//				Message message = new Message();
//				message.obj = error;
//				handler.sendMessage(message);	
//				Log.d("sad", "error");
//			}
//		});
		adapter = new PicAdapter(getActivity());		
		lv_weekly.setAdapter(adapter);
		//点击item进入对应week的内容
		lv_weekly.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Weekly weekly = datas.get(datas.size()-1-position);
				String week = weekly.getWeek();
				Intent intent = new Intent(getActivity(),WcontentActivity.class);
//				Bundle mBundle = new Bundle();   
//				mBundle.putSerializable("poem", poem);
//				mBundle.putSerializable("weekly", weekly);
				intent.putExtra("week", week);
				startActivity(intent);
			}
		});
		return view;
	}
//
//	@Override
//	public void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		adapter.notifyDataSetChanged();
//		Log.d("WeeklyFragment", "onStart");
//	}
	
//	/*
//	 * 加载每周推送数据
//	 */
//	public List<Weekly> initData() {
//		datas = new ArrayList<Weekly>();
//		Weekly data1 = new Weekly();
//		data1.setWeek(20);
//		data1.setTheme("描写风景的诗");
//		data1.setWcontent(null);
//		data1.setWimage(R.drawable.iv_bgo);
//		datas.add(data1);
//		Weekly data2 = new Weekly();
//		data2.setWeek(19);
//		data2.setTheme("为什么我的眼里常含泪水");
//		data2.setWcontent(null);
//		data2.setWimage(R.drawable.iv_bgt);
//		datas.add(data2);
//		Weekly data3 = new Weekly();
//		data3.setWeek(18);
//		data3.setTheme("是魔法的特技");
//		data3.setWcontent(null);
//		data3.setWimage(R.drawable.iv_bgth);		
//		datas.add(data3);
//		return datas;
//	}
//	/*
//	 * 从数据库中找出所有的weekly的信息并转换为weeklyData用于页面数据展示
//	 */
//	public List<WeeklyData> getDatas(){
//		datas = new ArrayList<WeeklyData>();
//		List<Weekly> weeklies = BirdsDB.getInstance(context).queryAllWeeklys();
//		for (Weekly weekly : weeklies) {
//			WeeklyData data = new WeeklyData();
//			data.setWeek(weekly.getWeek());
//			data.setTheme(weekly.getTheme());
//			Log.d("WF", weekly.getWimage());
//			ImageUtil.loadImageView(weekly.getWimage(), imageView);
//			data.setwImageView(imageView);
//			data.setWcontent(weekly.getWcontent());
//			datas.add(data);
//		}
//		return datas;
//	}
}

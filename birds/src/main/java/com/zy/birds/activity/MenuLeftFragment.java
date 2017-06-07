package com.zy.birds.activity;

import com.zy.birds.R;
import com.zy.birds.Model.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * 左侧滑动菜单页
 */
public class MenuLeftFragment extends Fragment {
	
	public static User loginUser;
	public static Bitmap loginUserImage = LoginActivity.loginUserImage;
	public ImageView iv_head;
	public TextView tv_username,tv_myshare,tv_favorite,tv_settings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_menu, container, false);
		loginUser = LoginActivity.loginUser;
		Log.d("asd", loginUser.getUname());
		Log.d("asd", loginUser.getUpass());
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		tv_myshare = (TextView) view.findViewById(R.id.pshare);
		tv_favorite = (TextView) view.findViewById(R.id.pstar);
		tv_settings = (TextView) view.findViewById(R.id.psetting);
		if (loginUser!=null) {
			iv_head = (ImageView) view.findViewById(R.id.iv_head);
			iv_head.setImageBitmap(loginUserImage);
			tv_username.setText(loginUser.getUname());			
		}
		tv_myshare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),MyshareActivity.class);
				startActivity(intent);
			}
		});
		tv_favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),FavoriteActivity.class);
				startActivity(intent);
			}
		});
		tv_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),SettingsActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}

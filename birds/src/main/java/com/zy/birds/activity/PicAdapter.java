package com.zy.birds.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Weekly;
import com.zy.birds.R;
import com.zy.birds.util.ImageUtil;

import java.util.List;

/*
 * weeklyfragment对应adapter，此处加入缓存图片的方法 
 */
public class PicAdapter extends BaseAdapter {

	public Context context;
	public BirdsDB birdsDB = BirdsDB.getInstance(context);
	public List<Weekly> list = birdsDB.queryAllWeeklys();
	
	private final static int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取当前应用程序所分配的最大内存
	private final static int cacheSize = maxMemory / 5;//只分5分之一用来做图片缓存
	public static LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize) {
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {//复写sizeof()方法
			// replaced by getByteCount() in API 12
			return bitmap.getRowBytes() * bitmap.getHeight() / 1024; //这里是按多少KB来算
		}
	};
	
	public PicAdapter(Context context) {
		super();
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("Adapter", "adapter");
		Weekly data = list.get(list.size()-1-position);
		if (convertView==null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lvweekly_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.bg_weekly = (ImageView) convertView.findViewById(R.id.rl_bg);
			viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_week);
			viewHolder.tv_theme = (TextView) convertView.findViewById(R.id.tv_weekpt);	
//			viewHolder.bg_weekly.setImageResource(R.drawable.iv_bgo);
//			ImageUtil.loadImageView(data.getWimage(), viewHolder.bg_weekly);
			ImageUtil.loadBitmap(data.getWimage(), viewHolder.bg_weekly);
//			viewHolder.bg_weekly.setImageBitmap(ImageUtil.loadImage(data.getWimage()));
			viewHolder.tv_week.setText(data.getWeek());
			viewHolder.tv_theme.setText(data.getTheme());
			convertView.setTag(viewHolder);
		} else {
			ViewHolder viewHolder = (ViewHolder)convertView.getTag();
//			viewHolder.bg_weekly.setImageResource(R.drawable.iv_bgo);
//			ImageUtil.loadImageView(data.getWimage(), viewHolder.bg_weekly);
//			viewHolder.bg_weekly.setImageBitmap(ImageUtil.loadImage(data.getWimage()));
			ImageUtil.loadBitmap(data.getWimage(), viewHolder.bg_weekly);
			viewHolder.tv_week.setText(data.getWeek());
			viewHolder.tv_theme.setText(data.getTheme());
		}
		return convertView;
	}
	class ViewHolder{
		ImageView bg_weekly;
		TextView tv_week;
		TextView tv_theme;
	}
//
//	/**
//	  * 
//	  * @param urlStr 所需要加载的图片的url，以String形式传进来，可以把这个url作为缓存图片的key
//	  * @param image ImageView 控件
//	  */
//	 private void loadBitmap(String urlStr, ImageView image) {
//		 AsyncImageLoader asyncLoader = new AsyncImageLoader(image, mLruCache);//什么一个异步图片加载对象
//		 Bitmap bitmap = asyncLoader.getBitmapFromMemoryCache(urlStr);//首先从内存缓存中获取图片
//		 if (bitmap != null) {
//			 image.setImageBitmap(bitmap);//如果缓存中存在这张图片则直接设置给ImageView
//		 } else {
//			 image.setImageResource(R.drawable.iv_bgo);//否则先设置成默认的图片
//			 asyncLoader.execute(urlStr);//然后执行异步任务AsycnTask 去网上加载图片
//		 }
//	 }	
	
}

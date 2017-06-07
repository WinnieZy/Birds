package com.zy.birds.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.zy.birds.R;
import com.zy.birds.activity.PicAdapter;
import com.zy.birds.fragment.WeeklyFragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtil {

	private static LruCache<String, Bitmap> mLruCache = PicAdapter.mLruCache ;
	/*
	 * 下载图片转为Bitmap格式
	 */
	public static Bitmap loadImage(String urlString){
		
		Bitmap bitmap = null;
        try {
        	URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
            connection.connect();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }		
		return bitmap;
	}
	/*
	 * 下载图片转为ImageView格式
	 */
	public static ImageView loadImageView(final String urlString,final ImageView imageView){
		
	    final Handler handler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            Bitmap bitmap = (Bitmap) msg.obj;
	            imageView.setImageBitmap(bitmap);
	            WeeklyFragment.adapter.notifyDataSetChanged();
	        }
	    };
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
	            try {
	            	URL url = new URL(urlString);
	                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	                connection.setRequestMethod("GET");
	                connection.connect();
	                InputStream is = connection.getInputStream();
	                Bitmap bitmap = BitmapFactory.decodeStream(is);
//	                Bitmap bm = compressImage(bitmap);
	                Message msg = new Message();
	                msg.obj = bitmap;
	                handler.sendMessage(msg);      
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
			}
		}).start();
		
		return imageView;
	}
	/*
	 * 压缩图片方法
	 */
//	private static Bitmap compressImage(Bitmap image) {
//	
//	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//	        int options = 100;
//	        while ( baos.toByteArray().length / 1024>50) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩        
//	            baos.reset();//重置baos即清空baos
//	            options -= 10;//每次都减少10
//	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//	
//	        }
//	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//	        return bitmap;
//	    }
	

	/**
	  * 以内存形式缓存图片，关闭程序时回收
	  * @param urlStr 所需要加载的图片的url，以String形式传进来，可以把这个url作为缓存图片的key
	  * @param image ImageView 控件
	  */
	 public static void loadBitmap(String urlStr, ImageView image) {
		 AsyncImageLoader asyncLoader = new AsyncImageLoader(image, mLruCache);//什么一个异步图片加载对象
		 Bitmap bitmap = asyncLoader.getBitmapFromMemoryCache(urlStr);//首先从内存缓存中获取图片
		 if (bitmap != null) {
			 image.setImageBitmap(bitmap);//如果缓存中存在这张图片则直接设置给ImageView
		 } else {
			 image.setImageResource(R.drawable.iv_bgo);//否则先设置成默认的图片
			 asyncLoader.execute(urlStr);//然后执行异步任务AsycnTask 去网上加载图片
		 }
	 }	
	
}


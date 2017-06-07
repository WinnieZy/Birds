package com.zy.birds.util;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
/**
 * 图片异步加载类，有图片内存缓存
 */
public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {
	
	private ImageView image;
	private static LruCache<String, Bitmap> lruCache;
	 /**
	  * 构造方法，需要把ImageView控件和LruCache 对象传进来
	  * @param image 加载图片到此 {@code}ImageView
	  * @param lruCache 缓存图片的对象
	  */
	public AsyncImageLoader(ImageView image, LruCache<String, Bitmap> lruCache) {
		super();
		this.image = image;
		AsyncImageLoader.lruCache = lruCache;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		try {
			bitmap = ImageUtil.loadImage(params[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBitmapToMemoryCache(params[0], bitmap);
		return bitmap;
	}
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		image.setImageBitmap(bitmap);
	}
	//调用LruCache的put 方法将图片加入内存缓存中，要给这个图片一个key 方便下次从缓存中取出来
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
		lruCache.put(key, bitmap);
		}
	}
	//调用Lrucache的get 方法从内存缓存中去图片
	public Bitmap getBitmapFromMemoryCache(String key) {
		return lruCache.get(key);
	}
}

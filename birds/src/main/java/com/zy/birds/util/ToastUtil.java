package com.zy.birds.util;

import android.content.Context;
import android.widget.Toast;

//Toast展示信息工具类
public class ToastUtil {
	public static void showMessage(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}

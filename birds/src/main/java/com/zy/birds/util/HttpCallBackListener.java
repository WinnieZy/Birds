package com.zy.birds.util;

public interface HttpCallBackListener {

	void onFinish(String response);
	
	void onError(Exception e);
}

package com.zy.birds.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/*
 * 用于收集所有Activity，在退出应用是全部关闭
 */
public class ExitAppUtil extends Application {
	
	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitAppUtil instance;  
    private ExitAppUtil() {
    }
   
    // 单例模式中获取唯一的ExitAPPUtils实例
    public static ExitAppUtil getInstance() {
        if(null == instance) {
            instance =new ExitAppUtil();
        }
        return instance;
    }
   
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
   
    // 遍历所有Activity并finish
    public void exit() { 
        try { 
            for (Activity activity : activityList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            System.exit(0); 
        } 
    }

}

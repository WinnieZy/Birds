package com.zy.birds.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/*
 * 时间转换工具类
 */
public class DateUtil {

	//得到"yyyy-MM-dd HH:mm:ss"格式的当前时间
	public static String getDateString() {
		String dateStr = "2016-04-22 20:25:15";
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		Date date = new Date(System.currentTimeMillis());
		if (dFormat!=null&&date!=null) {
			dateStr = dFormat.format(date);
		}
		return dateStr;
	}
	//得到该时间与当前时刻之间的关系
	public static String formatDisplayTime(String time) { 
		
        String display = ""; 
        int tMin = 60 * 1000; 
        int tHour = 60 * tMin; 
        int tDay = 24 * tHour; 
 
        if (time != null) { 
            try { 
                Date tDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).parse(time); 
                Date today = new Date(); 
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy",Locale.CHINA); 
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA); 
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime()); 
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime()); 
                Date beforeYes = new Date(yesterday.getTime() - tDay); 
                if (tDate != null) { 
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM-dd",Locale.CHINA); 
                    long dTime = today.getTime() - tDate.getTime(); 
                    if (tDate.before(thisYear)) { 
                        display = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(tDate); 
                    } else { 
                        if (dTime < tMin) { 
                            display = "刚刚"; 
                        } else if (dTime < tHour) { 
                            display = (int) Math.ceil(dTime / tMin) + "分钟前"; 
                        } else if (dTime < tDay && tDate.after(yesterday)) { 
                            display = (int) Math.ceil(dTime / tHour) + "小时前"; 
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) { 
                            display = "昨天" ; 
                        } else { 
                            display = halfDf.format(tDate); 
                        } 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        } 
        return display; 
    }
}

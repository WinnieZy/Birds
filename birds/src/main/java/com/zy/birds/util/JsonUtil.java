package com.zy.birds.util;

import android.content.Context;
import android.util.Log;

import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Comment;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.Model.Weekly;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Json数据解析类
 */
public class JsonUtil {
	
	public static Context context;
	public static BirdsDB birdsDB = BirdsDB.getInstance(context);
	//处理login界面服务器端返回的Json字符串
	public static int handleLoginResponse(String response){
		try {

				JSONObject jsonObject = new JSONObject(response);
				JSONObject data = jsonObject.getJSONObject("data");
				String info = data.getString("info");
				JSONObject reinfo = data.getJSONObject("reinfo");
				String username = reinfo.getString("username");
				String password = reinfo.getString("password");
//				String uemail = reinfo.getString("email");
//				String uphone = reinfo.getString("uphone");
				int status = jsonObject.getInt("status");
				Log.d("handleUserResponse", "info is "+info);
				Log.d("handleUserResponse", "username is "+username);
				Log.d("handleUserResponse", "password is "+password);			
//				Log.d("handleUserResponse", "uemail is "+uemail);	
//				Log.d("handleUserResponse", "uphone is "+uphone);	
				Log.d("handleUserResponse", "status is "+status);
				if (info.equals("success")) {
					String uimage = reinfo.getString("pic");
					uimage = "http://qtb.quantacenter.com"+uimage;
					Log.d("handleUserResponse", "uimage is "+uimage);
					User user = birdsDB.queryUserByName(username) ;//现在数据库找是否存在此用户
					if (birdsDB.queryUserByName(username) != null) {//已存在则更新用户信息
						user.setUpass(password);
//						user.setUemail(uemail);
//						user.setUphone(uphone);
						user.setUimage(uimage);
						birdsDB.updateUser(user);
					} else {//不存在则将整条用户信息加入数据库
						user = new User(username, password, uimage, "", "");
						birdsDB.addUser(user);
					}
					return 1;
				}else {
					if (username.equals("用户名错误")) {
						return 2;
					}else {
						return 3;
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//处理注册页面服务器返回的json字符串
	public static boolean handleRegisterResponse(String response){
		try {

				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.getInt("status");				
				JSONObject data = jsonObject.getJSONObject("data");
				String info = data.getString("info");
				if (info.equals("error")) {
					JSONObject reinfo = data.getJSONObject("reinfo");
					String username = reinfo.getString("username");			
					Log.d("handleUserResponse", "username is "+username);						
				}
				Log.d("handleUserResponse", "status is "+status);
				Log.d("handleUserResponse", "info is "+info);					
				if (info.equals("success")) {
					return true;
				}else {
					return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//处理退出账号
	public static String handleLogoutResponse(String response){
		try {
//			JSONObject jsonObject = new JSONObject(response);
			String info = response.substring(1,response.length()-1);
			Log.d("handleLogoutResponse", "info is "+info);	
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//未用
	public static void handleUserResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("user_id");
				String uname = jsonObject.getString("username");
				String upass = jsonObject.getString("password");
				Log.d("handleUserResponse", "id is "+String.valueOf(id));
				Log.d("handleUserResponse", "uname is "+uname);
				Log.d("handleUserResponse", "upass is "+upass);
//				User user = new User(id,uname,upass);
//				bridsDB.addUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//未用
	public static void handleCommentResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String ccontent = jsonObject.getString("c_content");
				int pid = jsonObject.getInt("s_id");
				int uid = jsonObject.getInt("username");
				String cpdate = jsonObject.getString("time");
				Log.d("handleCommentResponse", "ccontent is "+ccontent);
				Log.d("handleCommentResponse", "pid is "+pid);
				Log.d("handleCommentResponse", "uid is "+uid);
				Log.d("handleCommentResponse", "cpdate is "+cpdate);
//				Comment comment = new Comment(uid,pid,ccontent,cpdate);
//				bridsDB.addComment(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//未用
	public static void handleShareResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("s_id");
				String ptitle = jsonObject.getString("s_title");
				String ppcontent = jsonObject.getString("s_content");
				String source = jsonObject.getString("source");
				String pauthor = jsonObject.getString("author");
				String ppdate = jsonObject.getString("time");
				Log.d("handleShareResponse", "id is "+String.valueOf(id));
				Log.d("handleShareResponse", "ptitle is "+ptitle);
				Log.d("handleShareResponse", "ppcontent is "+ppcontent);
				Log.d("handleShareResponse", "source is "+source);
				Log.d("handleShareResponse", "pauthor is "+pauthor);
				Log.d("handleShareResponse", "ppdate is "+ppdate);
//				Poem poem = new Poem(id,ptitle,pauthor,ppcontent,ppdate,0,0,0);
//				bridsDB.addPoem(poem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//未用
	public static void handleTodayResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String t_title = jsonObject.getString("t_title");
				String t_content = jsonObject.getString("t_content");
				String imgname = jsonObject.getString("imgname");				
				String time = jsonObject.getString("time");
				String author = jsonObject.getString("author");
				Log.d("handleTodayResponse", "t_title is "+t_title);
				Log.d("handleTodayResponse", "t_content is "+t_content);
				Log.d("handleTodayResponse", "imgname is "+imgname);
				Log.d("handleTodayResponse", "time is "+time);
				Log.d("handleTodayResponse", "author is "+author);
//				Today today = new Today(t_title,t_content,imgname,time,author);
//				bridsDB.addToday(today);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	//处理服务器返回的weekly Json数据
	public static void handleWeeklyResponse(String response){
		try {
			JSONObject jsonObject = new JSONObject(response);
			String week = jsonObject.getString("week");
			String theme = jsonObject.getString("theme");	
			String wcontent = jsonObject.getString("content");
			String wimage = jsonObject.getString("pic");
			wimage = "http://qtb.quantacenter.com"+wimage;
			Log.d("handleWeeklyResponse", "week is "+week);
			Log.d("handleWeeklyResponse", "theme is "+theme);
			Log.d("handleWeeklyResponse", "imgname is "+wimage);
			Log.d("handleWeeklyResponse", "wcontent is "+wcontent);			
			if (birdsDB.queryWeeklyByWeek(week)==null) {//现在数据库查找是否有本周的数据，无则从网络端下载并保存到数据库
				Weekly weekly = new Weekly(week,theme,wcontent,wimage);
				birdsDB.addWeekly(weekly);
			}else {
				Weekly weekly = birdsDB.queryWeeklyByWeek(week);//有则更新当前周的信息
				weekly.setWcontent(wcontent);
				weekly.setTheme(theme);
				weekly.setWimage(wimage);
				birdsDB.updateWeekly(weekly);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	//未用
	public static void handleClickResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String s_id = jsonObject.getString("s_id");
				String like_num = jsonObject.getString("like_num");
				String collect_num = jsonObject.getString("collect_num");				
				String comment_num = jsonObject.getString("comment_num");
				Log.d("handleClickResponse", "s_id is "+s_id);
				Log.d("handleClickResponse", "like_num is "+like_num);
				Log.d("handleClickResponse", "collect_num is "+collect_num);
				Log.d("handleClickResponse", "comment_num is "+comment_num);
//				Poem poem = bridsDB.queryPoemById(s_id);
//				poem.setPlcount(like_num);
//				poem.setPccount(comment_num);
//				poem.setPscount(collect_num);
//				brids.DB.updatePoemById(poem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	//未用
	public static void handleMycollectResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int pid = jsonObject.getInt("s_id");
				String author = jsonObject.getString("author");
				int uid = jsonObject.getInt("username");	
				String sdate = jsonObject.getString("time");
				Log.d("handleMycollectResponse", "s_id is "+pid);
				Log.d("handleMycollectResponse", "author is "+author);
				Log.d("handleMycollectResponse", "username is "+uid);
				Log.d("handleMycollectResponse", "sdate is "+sdate);
//				Relationship relationship = bridsDB.queryRelationshipByUidPid(username,pid);
//				if (relationship==null) {
//					relationship = new Relationship(uid,pid,0,1,sdate);
//					bridsDB.addRelationship(relationship);
//				}else {
//					relationship.setStared(1);
//					relationship.setSdate(sdate);
//					bridsDB.updateRelationship(relationship);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//未用
	public static void handleMylikeResponse(String response){
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String pid = jsonObject.getString("s_id");
				String username = jsonObject.getString("username");	
				Log.d("handleMylikeResponse", "s_id is "+pid);
				Log.d("handleMylikeResponse", "username is "+username);
//				Relationship relationship = bridsDB.queryRelationshipByUidPid(username,pid);
//				if (relationship==null) {
//					relationship = new Relationship(uid,pid,1,0,null);
//					bridsDB.addRelationship(relationship);
//				}else {
//					relationship.setStared(1);
//					relationship.setSdate(sdate);
//					bridsDB.updateRelationship(relationship);
//				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//用于注册时生成Json字符串post到服务器端
//	User user
	public static String createUserJSONString(String username,String password){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("username",username);
	        jsonObject.put("password", password);
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}		
	//未用
	public static String createShareJSONString(Poem poem){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("s_title",poem.getPtitle());
	        jsonObject.put("s_content",poem.getPcontent());
	        jsonObject.put("source", poem.getPtitle());
	        jsonObject.put("author", poem.getPauthor());
	        jsonObject.put("time", poem.getPpdate());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	//未用
	public static String createCommentJSONString(Comment comment){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("c_content",comment.getCcontent());
	        jsonObject.put("s_id",comment.getPid());
	        jsonObject.put("username", comment.getPid());
	        jsonObject.put("time", comment.getCpdate());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	//未用
	public static String createTodayJSONString(Comment comment){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("c_content",comment.getCcontent());
	        jsonObject.put("s_id",comment.getPid());
	        jsonObject.put("username", comment.getPid());
	        jsonObject.put("time", comment.getCpdate());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	//未用
	public static String createWeeklyJSONString(Comment comment){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("c_content",comment.getCcontent());
	        jsonObject.put("s_id",comment.getPid());
	        jsonObject.put("username", comment.getPid());
	        jsonObject.put("time", comment.getCpdate());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	//未用
	public static String createClickJSONString(Poem poem){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("s_id",poem.getId());
	        jsonObject.put("like_num",poem.getPlcount());
	        jsonObject.put("collect_num", poem.getPscount());
	        jsonObject.put("comment_num", poem.getPccount());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	//未用
	public static String createMylikeJSONString(Relationship relationship){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("s_id",relationship.getPid());
	        jsonObject.put("username",relationship.getUid());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	//未用
	public static String createMyCollectJSONString(Relationship relationship){
		try {
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("s_id",relationship.getPid());
//	        jsonObject.put("author", bridsDB.queryPoemById(relationship.getId()).getPauthor());
	        jsonObject.put("username",relationship.getUid());
	        String json = jsonObject.toString();
	        return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	
}

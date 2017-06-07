package com.zy.birds.Model;

/*
 * 评论页面中adapter要显示的item信息
 */
public class CommentData {

	private int headImage;
	private String uName;
	private String comment;
	private int clockImage;
	private String date;
	
	public int getHeadImage() {
		return headImage;
	}
	public void setHeadImage(int headImage) {
		this.headImage = headImage;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getClockImage() {
		return clockImage;
	}
	public void setClockImage(int clockImage) {
		this.clockImage = clockImage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}

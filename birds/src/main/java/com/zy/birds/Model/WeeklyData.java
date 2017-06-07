package com.zy.birds.Model;

import java.io.Serializable;

import android.widget.ImageView;

/*
 * 未使用 weekly页面所需数据
 */
public class WeeklyData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String week;
	private String theme;
	private String wcontent;
	private ImageView wImageView;

	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getWcontent() {
		return wcontent;
	}
	public void setWcontent(String wcontent) {
		this.wcontent = wcontent;
	}
	public ImageView getwImageView() {
		return wImageView;
	}
	public void setwImageView(ImageView wImageView) {
		this.wImageView = wImageView;
	}

}

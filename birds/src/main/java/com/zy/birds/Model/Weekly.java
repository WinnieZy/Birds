package com.zy.birds.Model;

import java.io.Serializable;

/*
 * weekly页面所需数据
 */
public class Weekly implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String week;
	private String theme;
	private String wcontent;
	private String wimage;
	
	public Weekly() {
		super();
	}

	public Weekly(String week, String theme, String wcontent, String wimage) {
		super();
		this.week = week;
		this.theme = theme;
		this.wcontent = wcontent;
		this.wimage = wimage;
	}

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
	public String getWimage() {
		return wimage;
	}
	public void setWimage(String wimage) {
		this.wimage = wimage;
	}

}

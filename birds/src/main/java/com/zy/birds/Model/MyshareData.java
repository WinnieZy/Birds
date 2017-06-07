package com.zy.birds.Model;

import java.io.Serializable;

/*
 * MyshareActivity中每个item要显示的信息
 */
public class MyshareData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ms_pid;
	private String ms_pt;
	private String ms_pd;
	private int ms_plcount;
	private int ms_pscount;
	
	public int getMs_pid() {
		return ms_pid;
	}
	public void setMs_pid(int ms_pid) {
		this.ms_pid = ms_pid;
	}
	public String getMs_pt() {
		return ms_pt;
	}
	public void setMs_pt(String ms_pt) {
		this.ms_pt = ms_pt;
	}
	public String getMs_pd() {
		return ms_pd;
	}
	public void setMs_pd(String ms_pd) {
		this.ms_pd = ms_pd;
	}
	public int getMs_plcount() {
		return ms_plcount;
	}
	public void setMs_plcount(int ms_plcount) {
		this.ms_plcount = ms_plcount;
	}
	public int getMs_pscount() {
		return ms_pscount;
	}
	public void setMs_pscount(int ms_pscount) {
		this.ms_pscount = ms_pscount;
	}
}

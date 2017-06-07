package com.zy.birds.Model;

import java.io.Serializable;

/*
 * FavoriteActivity中每个item要显示的信息
 */
public class FavoriteData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int f_pid;
	private String f_pt;
	private String f_pd;
	private String f_pa;
	private int f_plcount;
	private int f_pscount;
	
	public int getF_pid() {
		return f_pid;
	}
	public void setF_pid(int f_pid) {
		this.f_pid = f_pid;
	}
	public String getF_pt() {
		return f_pt;
	}
	public void setF_pt(String f_pt) {
		this.f_pt = f_pt;
	}
	public String getF_pd() {
		return f_pd;
	}
	public void setF_pd(String f_pd) {
		this.f_pd = f_pd;
	}
	public String getF_pa() {
		return f_pa;
	}
	public void setF_pa(String f_pa) {
		this.f_pa = f_pa;
	}
	public int getF_plcount() {
		return f_plcount;
	}
	public void setF_plcount(int f_plcount) {
		this.f_plcount = f_plcount;
	}
	public int getF_pscount() {
		return f_pscount;
	}
	public void setF_pscount(int f_pscount) {
		this.f_pscount = f_pscount;
	}
}

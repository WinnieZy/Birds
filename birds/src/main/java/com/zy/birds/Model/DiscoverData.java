package com.zy.birds.Model;

import java.io.Serializable;

/*
 * discoverFragment中每个item要显示的信息
 */
public class DiscoverData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dpid;
	private String hImage;
	private String userName;
	private int psource;
	private int cImage;
	private String time;
	private String ptitle;
	private String pcontent;
	private String heart;
	private String comment;
	private String star;
	private int HEART_STATUS;
	private int STAR_STATUS;

	public int getDpid() {
		return dpid;
	}
	public void setDpid(int dpid) {
		this.dpid = dpid;
	}
	public String gethImage() {
		return hImage;
	}
	public void sethImage(String hImage) {
		this.hImage = hImage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	public int getPsource() {
		return psource;
	}
	public void setPsource(int psource) {
		this.psource = psource;
	}
	public int getcImage() {
		return cImage;
	}
	public void setcImage(int cImage) {
		this.cImage = cImage;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public String getHeart() {
		return heart;
	}
	public String getComment() {
		return comment;
	}
	public String getStar() {
		return star;
	}
	public void setHeart(String heart) {
		this.heart = heart;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public int getHEART_STATUS() {
		return HEART_STATUS;
	}
	public void setHEART_STATUS(int hEART_STATUS) {
		HEART_STATUS = hEART_STATUS;
	}
	public int getSTAR_STATUS() {
		return STAR_STATUS;
	}
	public void setSTAR_STATUS(int sTAR_STATUS) {
		STAR_STATUS = sTAR_STATUS;
	}
	
}

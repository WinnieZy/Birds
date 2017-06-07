package com.zy.birds.Model;

/*
 * 关系集对应DIY集合，用于将用户和诗的ID显示为用户名和诗名
 */
public class RelationshipDIY {
	
	private int id;
	private int uid;
	private int pid;
	private String uname;
	private String ptitle;
	private int hearted;
	private int stared;
	private String sdate;
	
	public RelationshipDIY(int id, String uname, String ptitle, int hearted,
			int stared, String sdate) {
		super();
		this.id = id;
		this.uname = uname;
		this.ptitle = ptitle;
		this.hearted = hearted;
		this.stared = stared;
		this.sdate = sdate;
	}
	
	public RelationshipDIY(String uname, String ptitle, int hearted,
			int stared, String sdate) {
		super();
		this.uname = uname;
		this.ptitle = ptitle;
		this.hearted = hearted;
		this.stared = stared;
		this.sdate = sdate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public int getHearted() {
		return hearted;
	}
	public void setHearted(int hearted) {
		this.hearted = hearted;
	}
	public int getStared() {
		return stared;
	}
	public void setStared(int stared) {
		this.stared = stared;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

}

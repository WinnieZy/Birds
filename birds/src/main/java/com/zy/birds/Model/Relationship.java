package com.zy.birds.Model;

/*
 * 关系集，用于存储用户对诗歌的点赞，收藏状态
 */
public class Relationship {

	private int id;
	private int uid;
	private int pid;
	private int hearted;
	private int stared;
	private String sdate;
	
	public Relationship(int id, int uid, int pid, int hearted, int stared,
			String sdate) {
		super();
		this.id = id;
		this.uid = uid;
		this.pid = pid;
		this.hearted = hearted;
		this.stared = stared;
		this.sdate = sdate;
	}
	
	public Relationship(int uid, int pid, int hearted, int stared, String sdate) {
		super();
		this.uid = uid;
		this.pid = pid;
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

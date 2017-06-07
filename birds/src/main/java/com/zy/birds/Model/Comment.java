package com.zy.birds.Model;

/*
 * 评论实体类
 */
public class Comment {

	private int id;
	private int uid;
	private int pid;
	private String ccontent;
	private String cpdate;
	
	public Comment(int id, int uid, int pid, String ccontent, String cpdate) {
		super();
		this.id = id;
		this.uid = uid;
		this.pid = pid;
		this.ccontent = ccontent;
		this.cpdate = cpdate;
	}
	
	public Comment(int uid, int pid, String ccontent, String cpdate) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.ccontent = ccontent;
		this.cpdate = cpdate;
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
	public String getCcontent() {
		return ccontent;
	}
	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
	public String getCpdate() {
		return cpdate;
	}
	public void setCpdate(String cpdate) {
		this.cpdate = cpdate;
	}
	
}

package com.zy.birds.Model;

/*
 * 自定义评论类，用于将用户和诗的ID转换为用户名和诗名
 */
public class CommentDIY {
	private int id;
	private int uid;
	private int pid;
	private String uname;
	private String ptitle;
	private String ccontent;
	private String cpdate;
	
	public CommentDIY(int id, String uname, String ptitle, String ccontent,
			String cpdate) {
		super();
		this.id = id;
		this.uname = uname;
		this.ptitle = ptitle;
		this.ccontent = ccontent;
		this.cpdate = cpdate;
	}
	
	public CommentDIY(String uname, String ptitle, String ccontent,
			String cpdate) {
		super();
		this.uname = uname;
		this.ptitle = ptitle;
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

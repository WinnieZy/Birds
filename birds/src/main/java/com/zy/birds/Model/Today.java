package com.zy.birds.Model;

/*
 * Today页面所需数据
 */
public class Today {

	private String ttitle;
	private String tauthor;
	private String tcontent;
	private String tdate;
	private String timage;
	
	public Today(String ttitle, String tauthor, String tcontent, String tdate,
			String timage) {
		super();
		this.ttitle = ttitle;
		this.tauthor = tauthor;
		this.tcontent = tcontent;
		this.tdate = tdate;
		this.timage = timage;
	}
	public String getTtitle() {
		return ttitle;
	}
	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}
	public String getTauthor() {
		return tauthor;
	}
	public void setTauthor(String tauthor) {
		this.tauthor = tauthor;
	}
	public String getTcontent() {
		return tcontent;
	}
	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getTimage() {
		return timage;
	}
	public void setTimage(String timage) {
		this.timage = timage;
	}
}

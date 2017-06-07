package com.zy.birds.Model;

/*
 * User类，储存user信息
 */
public class User {
	
	private int id;
	private String uname;
	private String upass;
	private String uimage;
	private String uemail;
	private String uphone;
	
	public User(int id, String uname, String upass, String uimage,
			String uemail, String uphone) {
		super();
		this.id = id;
		this.uname = uname;
		this.upass = upass;
		this.uimage = uimage;
		this.uemail = uemail;
		this.uphone = uphone;
	}
	
	public User(String uname, String upass, String uimage, String uemail,
			String uphone) {
		super();
		this.uname = uname;
		this.upass = upass;
		this.uimage = uimage;
		this.uemail = uemail;
		this.uphone = uphone;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public String getUimage() {
		return uimage;
	}
	public void setUimage(String uimage) {
		this.uimage = uimage;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
}

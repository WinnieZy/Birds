package com.zy.birds.Model;

import java.io.Serializable;

/*
 * 诗的实体类
 */
public class Poem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String ptitle;
	private String pauthor;
	private String pcontent;
	private int psource;
	private String ppdate;
	private int plcount;
	private int pccount;
	private int pscount;

	public Poem(int id, String ptitle, String pauthor, String pcontent,
			int psource, String ppdate, int plcount, int pccount, int pscount) {
		super();
		this.id = id;
		this.ptitle = ptitle;
		this.pauthor = pauthor;
		this.pcontent = pcontent;
		this.psource = psource;
		this.ppdate = ppdate;
		this.plcount = plcount;
		this.pccount = pccount;
		this.pscount = pscount;
	}
	
	public Poem(String ptitle, String pauthor, String pcontent, int psource,
			String ppdate, int plcount, int pccount, int pscount) {
		super();
		this.ptitle = ptitle;
		this.pauthor = pauthor;
		this.pcontent = pcontent;
		this.psource = psource;
		this.ppdate = ppdate;
		this.plcount = plcount;
		this.pccount = pccount;
		this.pscount = pscount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public String getPauthor() {
		return pauthor;
	}
	public void setPauthor(String pauthor) {
		this.pauthor = pauthor;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public int getPsource() {
		return psource;
	}
	public void setPsource(int psource) {
		this.psource = psource;
	}
	public String getPpdate() {
		return ppdate;
	}
	public void setPpdate(String ppdate) {
		this.ppdate = ppdate;
	}
	public int getPlcount() {
		return plcount;
	}
	public void setPlcount(int plcount) {
		this.plcount = plcount;
	}
	public int getPccount() {
		return pccount;
	}
	public void setPccount(int pccount) {
		this.pccount = pccount;
	}
	public int getPscount() {
		return pscount;
	}
	public void setPscount(int pscount) {
		this.pscount = pscount;
	}
	
}

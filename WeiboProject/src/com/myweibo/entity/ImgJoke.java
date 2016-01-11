package com.myweibo.entity;

import java.util.Date;

public class ImgJoke {
	private int id;
	private String title;
	private String filename;
	private int sendc;
	private Date cjsj;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSendc() {
		return sendc;
	}
	public void setSendc(int sendc) {
		this.sendc = sendc;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
}

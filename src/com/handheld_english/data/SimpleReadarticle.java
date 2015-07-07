package com.handheld_english.data;

import java.sql.Date;


public class SimpleReadarticle {
	int id;
    private String title;
    private String content;
    private 
    java.util.Date datetime;
    public SimpleReadarticle(int id, String title,String content,java.util.Date date1)
    {
    	this.id = id;
    	this.title=title;
    	this.content=content;
    	this.datetime=date1;
    }
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public java.util.Date getDatetime() {
		return datetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}

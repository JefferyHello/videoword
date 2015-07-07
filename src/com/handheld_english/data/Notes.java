package com.handheld_english.data;

public class Notes {
	int id;
   
    private String note;
  
    public Notes(int id,String content)
    {
    	this.id = id;
    	
    	this.note=content;
    	
    }
	
	public Notes() {
		// TODO Auto-generated constructor stub
	}

	public String getNotes() {
		return note;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

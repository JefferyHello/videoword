package com.handheld_english.data;

import java.util.List;

public class Article {
	int id;
    private String title;
    private String content;
    float score;
   List<String> words;
   String video; 
   
    public Article(int id, String title,String content, String video)
    {
    	this.id = id;
    	this.title=title;
    	this.content=content;
    	this.video = video;
    }
    
	public Article() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	
	
}

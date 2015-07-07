package com.handheld_english.data;



public class Video {
    private int imageVideo;
    private String name;
    private String file;
    public Video(int imageVideo,String name,String file)
    {
    	this.imageVideo=imageVideo;
    	this.name=name;
    	this.file=file;
    }
	public int getImageVideo() {
		return imageVideo;
	}
	public String getName() {
		return name;
	}
	public String getFile() {
		return file;
	}
}

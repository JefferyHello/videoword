package com.handheld_english.data;

public class Word {
//	public static final String[][] word={
//		   {"happy","幸福的；高兴的；巧妙的","That happy day will be for ever embedded in my memory."},
//		   {"grace","优雅；恩惠；魅力；慈悲","The grace of our Lord Jesus Christ be with you."},
//		   {"light","光；光线；领悟；浅色；天窗","Bamboo is light because it is hollow."},
//	   };
	int id;
    private String word;
    private String translation;
    private String explain;
    private String pronounce;
    public Word(int id,String word,String pronounce,String translation,String explain)
    {
    	this.id=id;
    	this.pronounce=pronounce;
    	this.word=word;
    	this.translation=translation;
   	    this.explain=explain;
    }
    
    
    public Word() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}

	
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}

	
	public String getPronounce() {
		return pronounce;
	}
	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}


	
}

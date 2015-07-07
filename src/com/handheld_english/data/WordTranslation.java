package com.handheld_english.data;

public class WordTranslation {
    private String word;
    private String translation;
    public WordTranslation(String word,String translation)
    {
    	this.word=word;
    	this.translation=translation;
    
    }
	public String getWord() {
		return word;
	}
	public String getTranslation() {
		return translation;
	}
	
}

package com.handheld_english.data;

public class Word {
//	public static final String[][] word={
//		   {"happy","�Ҹ��ģ����˵ģ������","That happy day will be for ever embedded in my memory."},
//		   {"grace","���ţ����ݣ��������ȱ�","The grace of our Lord Jesus Christ be with you."},
//		   {"light","�⣻���ߣ�����ǳɫ���촰","Bamboo is light because it is hollow."},
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

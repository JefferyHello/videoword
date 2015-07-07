package com.handheld_english;

import java.util.HashMap;

import com.handheld_english.data.User;

public class AppSession {

	public static final String USER = "user";
	public static final String COURSE = "course";
	public static final String ARTICLE = "article";
	public static final String WORD = "word";
	public static final String LEARNWORDS = "learnwords";
	public static final String READARTICLE = "readarticle";
	public static final String NOTES = "notes";

	static HashMap<String, Object> map =  new HashMap<String, Object>();
	
	public static void put(String key, Object value) {
		map.put(key, value);
	}


	public static Object get(String key ) {
		return map.get(key);
	}

}


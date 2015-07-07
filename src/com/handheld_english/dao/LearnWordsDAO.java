package com.handheld_english.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.handheld_english.AppSession;
import com.handheld_english.data.Learnword;
import com.handheld_english.data.User;
import com.handheld_english.data.Word;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LearnWordsDAO {

	private MyDatabaseHelper dbHelper;
	public LearnWordsDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	}
	public void save_learnwords( int uId, String gmtString, String wordlist) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor3 = db.rawQuery(
				"select w_id from Words where word=?",
				new String[] {wordlist});
//		Word words = (Word) AppSession.get(AppSession.WORD);
		Word words = new Word();
		if (cursor3.moveToFirst()) {
			int id1 = cursor3.getInt(cursor3.getColumnIndex("w_id"));
			
//			words.setId(id1);
			
			words.setId(id1);
			AppSession.put(AppSession.WORD, words);
			
		}
		cursor3.close();
		
        ContentValues values1 = new ContentValues();
		values1.put("u_id", uId);
		values1.put("w_id", words.getId());
		values1.put("lw_time",gmtString);
		
		db.insert("Learnwords", null, values1);
	}

	public List<String>  getTargetWords(int uId) {
		List<String> words = new ArrayList<String>();
		
		List<Learnword> learns=getLearnwords(uId,  0,  2) ;
		
		for(Learnword l: learns )
		{
			words.add(l.getWord().getWord());
		}
		return words;
	}
	
	public void save_learn_words(List<Learnword> learnwords) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < learnwords.size(); i++) {
        	Learnword learnword = learnwords.get(i); 
			ContentValues values = new ContentValues();
			values.put("u_id", learnword.getUser().getUId());
			values.put("w_id", learnword.getWord().getId());
			values.put("score", learnword.getScore());
			values.put("lw_time", learnword.getDate().getTime());
			db.insert("Learnwords", null, values);
		}
	}
	
	//大于3分的词汇学习记录
	public List<Learnword> getLearnwords(int uId, int minScore, int maxScore) {
		List<Learnword> list = new ArrayList<Learnword>();
		User u = null;
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select u.*, w.*, lw.lw_id, lw.score, lw.lw_time from User as u, Words as w, Learnwords as lw  where u.u_id = lw.u_id and lw.w_id = w.w_id and lw.score>=? and lw.score<=? and u.u_id =? and lw.lw_id in ( select lw_id from (select lw.lw_id, max(lw.lw_time) as lw_time  from  Learnwords as lw GROUP BY lw.w_id) ) ",
						
				new String[] {minScore+"",maxScore+"" , uId+""});
 
		if (cursor.moveToFirst()) {
			do{
				Word w = new Word();
				w.setId(  cursor.getInt( cursor.getColumnIndex("w_id")) );
				w.setWord(  cursor.getString(cursor.getColumnIndex("word")) );
				w.setTranslation(  cursor.getString(cursor.getColumnIndex("translation")) );
				w.setExplain(  cursor.getString(cursor.getColumnIndex("explain")) );
	
				if(u==null)
				{
					u = new User();
					u.setUId(   cursor.getInt( cursor.getColumnIndex("u_id")) );
					u.setUName( cursor.getString(cursor.getColumnIndex("u_name")) );
				}
				
				Learnword lw = new Learnword();
				lw.setUser(u);
				lw.setWord(w);
				
				lw.setId(  cursor.getInt( cursor.getColumnIndex("lw_id")) );
				lw.setScore( cursor.getInt( cursor.getColumnIndex("score")) );
				lw.setDate(  new Date( cursor.getInt( cursor.getColumnIndex("lw_time")) ) );  
				 
				list.add(lw);
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		return list;
	} 
	

}

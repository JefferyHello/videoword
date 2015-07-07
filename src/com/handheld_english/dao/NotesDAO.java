package com.handheld_english.dao;

import com.handheld_english.AppSession;
import com.handheld_english.data.Article;
import com.handheld_english.data.Course;
import com.handheld_english.data.Notes;
import com.handheld_english.data.User;
import com.handheld_english.data.Word;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesDAO {

	private MyDatabaseHelper dbHelper;
	public NotesDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	}
	public void save_article_note(String note) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("note", note);
		db.insert("Notes", null, values);
		
		//获取笔记ID
		Cursor cursor=db.rawQuery("select n_id from Notes where note=?",new String[] {note});
		if(cursor.moveToFirst()){
			int note_id=cursor.getInt(cursor.getColumnIndex("n_id"));
			Notes notes = new Notes();
			notes.setId(note_id);
			AppSession.put(AppSession.NOTES, notes);
			
		}
		cursor.close();
				

		//添加数据到文章笔记表中
		Notes notes = (Notes) AppSession.get(AppSession.NOTES);
		Article article1 = (Article) AppSession.get(AppSession.ARTICLE);
		ContentValues values1 = new ContentValues();
		values1.put("n_id", notes.getId());
		values1.put("a_id", article1.getId());
		db.insert("Articlenotes", null, values1);
	
	}
	public void save_word_note(String note, String wordlist) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("note", note);
		db.insert("Notes", null, values);
		// 获取笔记ID
	
//		Notes note1 = (Notes) AppSession.get(AppSession.NOTES);
		Notes note1 =new Notes();
		Cursor cursor = db.rawQuery(
				"select n_id from Notes where note=?",
				new String[] { note });
		if (cursor.moveToFirst()) {
			int note_id = cursor.getInt(cursor
					.getColumnIndex("n_id"));
//			note1.setId(note_id);
			note1.setId(note_id);
			AppSession.put(AppSession.NOTES, note1);
		}
		cursor.close();
		// 获取单词id
//		Word word1 = (Word) AppSession.get(AppSession.WORD);
		Word word1 = new Word();
		Cursor cursor2 = db.rawQuery(
				"select w_id from Words where word=?",
				new String[] { wordlist });
		if (cursor2.moveToFirst()) {
			int id = cursor2.getInt(cursor2.getColumnIndex("w_id"));
//			word1.setId(id);
			
			word1.setId(id);
			AppSession.put(AppSession.WORD, word1);
		}
		cursor2.close();

		// 添加数据到单词笔记表中
		ContentValues values1 = new ContentValues();
		values1.put("n_id", note1.getId());
		values1.put("w_id", word1.getId());
		db.insert("Wordnotes", null, values1);

	} 
	

}

package com.handheld_english.dao;

import com.handheld_english.AppSession;
import com.handheld_english.data.Course;
import com.handheld_english.data.Readarticle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReadArticleDAO {

	private MyDatabaseHelper dbHelper;
	public ReadArticleDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	} 
	 

/*
	public void save(int a_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor2 = db
				.rawQuery("select rda_id from Readarticle where a_id = ?",
						new String[] { Integer.toString(a_id) });
		int readarticle_id;
		
		if (cursor2.moveToFirst()) {
			do {

				int readarticle_id = cursor2.getInt(cursor2
						.getColumnIndex("rda_id"));
				
			} while (cursor2.moveToNext());
		}
		cursor2.close();
		
		Readarticle readarticle1 = (Readarticle) AppSession.get(AppSession.READARTICLE);
		
		readarticle1.setId(readarticle_id);
	}
	*/


	public void save(Readarticle read) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into Readarticle(a_id, u_id,rda_time) VALUES(?, ?,?)",
				new String[] { String.valueOf(  read.getArticle().getId() ),String.valueOf(read.getUser().getUId()),""+read.getDate().getTime() });
	
		
		Cursor cursor2 = db
				.rawQuery("select rda_id from Readarticle where a_id = ? and u_id=? and rda_time=?",
						new String[] { String.valueOf(  read.getArticle().getId() ),String.valueOf(read.getUser().getUId()),""+read.getDate().getTime() });
				
		int readarticle_id=0;
		
		if (cursor2.moveToFirst()) {
				readarticle_id = cursor2.getInt(cursor2
						.getColumnIndex("rda_id"));
		}
		cursor2.close();
		
		read.setId(readarticle_id);
	}

}

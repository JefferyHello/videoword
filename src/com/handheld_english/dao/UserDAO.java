package com.handheld_english.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.handheld_english.AppSession;
import com.handheld_english.data.User;

public class UserDAO {
	private MyDatabaseHelper dbHelper;
	public UserDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	} 

	public void save_user(String name, String pwd) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("u_name", name);
		values.put("password", pwd);
		db.insert("User", null, values);
	}
	
	public User getUserByname(String name) {
		// TODO Auto-generated method stub


		SQLiteDatabase db = dbHelper.getWritableDatabase();
		User user = null;
		Cursor cursor = db.rawQuery("select u_id,u_name from User where u_name=?",
				new String[] { name });
		if (cursor.moveToFirst()) {

			int id = cursor.getInt(cursor.getColumnIndex("u_id"));
			String name1 = cursor.getString(cursor.getColumnIndex("u_name"));
			user = new User();
			user.setUId(id);
			user.setUName(name1);
			
		}
		cursor.close();
		

		return user;
	}

	
	
		

}

package com.handheld_english.dao;

import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.handheld_english.AppSession;
import com.handheld_english.data.Course;
import com.handheld_english.data.User;

public class CourseDAO {
	
	public CourseDAO(MyDatabaseHelper dbHelper) {
		super();
		this.dbHelper = dbHelper;
	}

	MyDatabaseHelper dbHelper;
	

	public void save_course() {
	    SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "Àƒº∂¥ ª„" });
		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "¡˘º∂¥ ª„" });
		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "—≈Àº¥ ª„" });
		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "øº—–¥ ª„" });
		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "Õ–∏£¥ ª„" });
		db.execSQL("insert into Courses (c_name) values(?)",
				new String[] { "»»√≈¥ ª„" });
	}
	
	public  HashMap<String, Course> getAllCourses()
	{
		HashMap<String, Course> courses = new HashMap<String, Course>();
		
		//TODO
		SQLiteDatabase db = dbHelper.getWritableDatabase();

    	Cursor cursor = db.rawQuery("select c_id,c_name from Courses",
				null);
		if (cursor.moveToFirst()) {
			do{
				
				int id = cursor.getInt(cursor.getColumnIndex("c_id"));
				String name = cursor.getString(cursor
						.getColumnIndex("c_name"));
				Course c = new Course();
				c.setId( id );
				c.setName(name);
				courses.put(c.getName(), c);
				
			}while(cursor.moveToNext());
		}
		cursor.close();
		return courses;
		
	}


}

package com.handheld_english.dao;

import com.handheld_english.AppSession;
import com.handheld_english.data.Course;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SelectCourseDAO {

	private MyDatabaseHelper dbHelper;
	public SelectCourseDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	} 
	
	public void save(int user_id, int course_id) {


		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values1 = new ContentValues();
		values1.put("u_id",  user_id);
		values1.put("c_id", course_id);
		db.insert("Selectcourses", null, values1);
					
		
	}

	public Course getSelectedCourse(String username) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db.rawQuery("select * from Selectcourses,Courses,User where Selectcourses.c_id=Courses.c_id and u_name=?",
				new String[] { username });
		
		Course course =null;
		if (cursor1.moveToFirst()) {

			int id = cursor1.getInt(cursor1.getColumnIndex("c_id"));
			String name2 = cursor1.getString(cursor1.getColumnIndex("c_name"));
			course = new Course();
			course.setId(id);
			course.setName(name2);
			         
		}
		cursor1.close();
		
		
		return course;
		
	}

}

package com.handheld_english.dao;

import com.handheld_english.AppSession;
import com.handheld_english.data.Course;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseWordDAO {

	private MyDatabaseHelper dbHelper;
	public CourseWordDAO( MyDatabaseHelper dbHelper )
	{
		this.dbHelper = dbHelper;
	} 
	
	public void save_course_word(int word_id, int course_id) {


		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		db.execSQL(
				"insert into Courses_Words (w_id,c_id) values(?,?)",
				new String[] { word_id+"", course_id+"" });
					
	}

//	public void save_selected_course(String name) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		Cursor cursor1 = db.rawQuery("select * from Selectcourses,Courses,User where Selectcourses.c_id=Courses.c_id and u_name=?",
//				new String[] { name });
//		if (cursor1.moveToFirst()) {
//
//			int id = cursor1.getInt(cursor1.getColumnIndex("c_id"));
//			String name2 = cursor1.getString(cursor1.getColumnIndex("c_name"));
//			Course course = new Course();
//			course.setId(id);
//			course.setName(name2);
//			AppSession.put(AppSession.COURSE, course);
//			         
//		}
//		cursor1.close();
//		
//	}

}

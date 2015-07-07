package com.handheld_english.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper{
	
	
	public static final String CREATE_USER="create table User("+
            "u_id integer primary key autoincrement not null,"+
			"u_name text not null,"+
            "password text not null)";
	public static final String CREATE_SELECT_COURSES="create table Selectcourses("+
            "s_id integer primary key autoincrement not null,"+
			"u_id integer not null,"+
            "c_id  integer not null)";
	public static final String CREATE_COURSES="create table Courses("+
            "c_id integer primary key autoincrement,"+
			"c_name text not null)";
	
	public static final String CREATE_ARTICLE="create table Article("+
            "a_id integer primary key autoincrement not null," +
			"a_title text not null,"+
            "article text not null,"+
            "video text ,"+
			
            "a_score integer )";
	
	public static final String CREATE_RARTICLE="create table Rarticle("+
            "ra_id integer primary key autoincrement not null,"+
			"u_id integer not null,"+
            "a_id integer not null, "+
            "ra_time integer not null,"+
            "a_score integer,"+
            "a_rank integer not null)";
	
	public static final String CREATE_READ_ARTICLE="create table Readarticle("+
            "rda_id integer primary key autoincrement not null,"+
            "u_id integer not null,"+
            "a_id integer not null,"+
            "rda_time datetime not null)";
	public static final String CREATE_QUERY_WORDS="create table Querywords("+
            "qw_id integer primary key autoincrement not null,"+
            "rda_id integer not null,"+
            "w_id integer not null,"+
            "qwc integer)";
	public static final String CREATE_LEARN_WORDS="create table Learnwords("+
            "lw_id integer primary key autoincrement not null,"+
            "u_id integer not null,"+
            "w_id integer not null,"+
            "score integer , "+
            "lw_time integer not null)";
	public static final String CREATE_WORDS="create table Words("+
            "w_id integer primary key autoincrement not null,"+
            "word text not null,"+
            "pronounce text,"+
            "translation text,"+
            "explain text)";
	public static final String CREATE_COURSES_WORDS="create table Courses_Words("+
            "cw_id integer primary key autoincrement not null,"+
            "w_id integer not null,"+
            "c_id integer not null)";
	public static final String CREATE_NOTES="create table Notes("+
            "n_id integer primary key autoincrement not null,"+
            "note text not null)";
	public static final String CREATE_WORD_NOTES="create table Wordnotes("+
            "wordnote_id integer primary key autoincrement not null," +
            "n_id integer not null,"+
            "w_id integer not null)";
	public static final String CREATE_ARTICLE_NOTES="create table Articlenotes("+
            "articlenote_id integer primary key autoincrement not null," +
            "n_id integer not null,"+
            "a_id integer not null)";
	public static final String CREATE_VIDEO="create table Video("+
            "v_id integer primary key autoincrement not null,"+
            "v_title text not null,"+
            "video text not null)";
//	private Context mContext;
	
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_SELECT_COURSES);
		db.execSQL(CREATE_COURSES);
		db.execSQL(CREATE_ARTICLE);
		db.execSQL(CREATE_RARTICLE);
		db.execSQL(CREATE_READ_ARTICLE);
		db.execSQL(CREATE_QUERY_WORDS);
		db.execSQL(CREATE_LEARN_WORDS);
		db.execSQL(CREATE_WORDS);
		db.execSQL(CREATE_COURSES_WORDS);
		db.execSQL(CREATE_NOTES);
		db.execSQL(CREATE_WORD_NOTES);
		db.execSQL(CREATE_ARTICLE_NOTES);
		db.execSQL(CREATE_VIDEO);
		

	}
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		switch(oldVersion){
		case 1:
			db.execSQL(CREATE_SELECT_COURSES);
			db.execSQL(CREATE_COURSES);
			db.execSQL(CREATE_ARTICLE);
			db.execSQL(CREATE_RARTICLE);
			db.execSQL(CREATE_READ_ARTICLE);
			db.execSQL(CREATE_QUERY_WORDS);
			db.execSQL(CREATE_LEARN_WORDS);
			db.execSQL(CREATE_WORDS);
			db.execSQL(CREATE_COURSES_WORDS);
			db.execSQL(CREATE_NOTES);
			db.execSQL(CREATE_WORD_NOTES);
			db.execSQL(CREATE_ARTICLE_NOTES);
			db.execSQL(CREATE_VIDEO);
			
		default:
		}
	
	}
	
	public static MyDatabaseHelper instance(Context c) {
		// TODO Auto-generated method stub
		return   new MyDatabaseHelper(c, "HandheldEnglish.db", null, 1);
	}

}

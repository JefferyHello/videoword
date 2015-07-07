package com.handheld_english.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handheld_english.AppSession;
import com.handheld_english.Config;
import com.handheld_english.R;
import com.handheld_english.data.Article;
import com.handheld_english.data.Course;
import com.handheld_english.data.Word;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

public class QuerywordDAO {

	private MyDatabaseHelper dbHelper;

	public QuerywordDAO(MyDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
 
	
	public List<Word> getQueryWord(SQLiteDatabase db, int rdaId) {
		List<Word> words = new ArrayList<Word>();
		
		Cursor cursor1 = db.rawQuery("select * from Words,Querywords where Words.w_id=Querywords.w_id and Querywords.rda_id=? ", new String[] {String.valueOf(rdaId)});
//		Cursor cursor1 = db.query("Words", null, null, null, null, null, null);
		if (cursor1.moveToFirst()) {
			do {
				 int id = cursor1.getInt(cursor1.getColumnIndex("w_id"));
				 String pronounce = cursor1.getString(cursor1
						 .getColumnIndex("pronounce"));
				String word = cursor1.getString(cursor1.getColumnIndex("word"));
				String translation = cursor1.getString(cursor1
						.getColumnIndex("translation"));
				String explain = cursor1.getString(cursor1
						.getColumnIndex("explain"));
				Word w = new Word(id,word,pronounce, translation, explain);
				words.add(w);
			} while (cursor1.moveToNext());
		}
		cursor1.close();
		
		return words;
	}
}

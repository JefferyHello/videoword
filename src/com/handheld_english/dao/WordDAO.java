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

public class WordDAO {

	private MyDatabaseHelper dbHelper;

	public WordDAO(MyDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public void save_word(int id, String word, String translation,
			String explain) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.execSQL(
				"insert into Words (w_id, word, pronounce, translation, explain) values(?,?,?,?,?)",
				new String[] { id + "", word, "", translation, explain });

	}

	public Word getWord( String word) {
		// TODO Auto-generated method stub
		Word w = new Word();
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor1 = db.rawQuery(
				"select w_id,translation,explain from Words where word = ?",
				new String[] { word });
		if (cursor1.moveToFirst()) { 
			int	w_id = cursor1.getInt(cursor1.getColumnIndex("w_id"));
			String	explain = cursor1.getString(cursor1.getColumnIndex("explain"));
			String	translation = cursor1.getString(cursor1.getColumnIndex("translation"));
		
			w.setId(w_id);
			w.setWord(word);
			w.setExplain( explain );
			w.setTranslation(translation); 
		}
		cursor1.close();
		
		return w;
	}

	public void select_translation(String explain, String url) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select translation from Words where word = ?",
				new String[] { url });
		if (cursor.moveToFirst()) {
			do {
				explain = cursor
						.getString(cursor.getColumnIndex("translation"));

			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	public void saveQuerywords(int wordId, int readId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into Querywords( w_id,rda_id,qwc) VALUES(?, ?,?)",
				new String[] { String.valueOf(wordId), String.valueOf(readId), null });

	}

	public boolean hasWord(String w ) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db.rawQuery("select word from Words where word = ?",
				new String[] { w });
		boolean has = false;
		if (cursor1.moveToFirst()) {
				has = true;
		}
		cursor1.close();
		
		return has;
	}

	public void progress(TextView textview1, TextView textview2) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db.rawQuery(
				"select seq from sqlite_sequence where name=? ",
				new String[] { "Readarticle" });
		if (cursor1.moveToFirst()) {
			do {

				textview1.setText(cursor1.getString(cursor1
						.getColumnIndex("seq")));

			} while (cursor1.moveToNext());
		}
		cursor1.close();

		Cursor cursor2 = db.rawQuery(
				"select seq from sqlite_sequence where name=? ",
				new String[] { "Querywords" });
		if (cursor2.moveToFirst()) {
			do {

				textview2.setText(cursor2.getString(cursor2
						.getColumnIndex("seq")));

			} while (cursor2.moveToNext());
		}
		cursor2.close();
	}

	public List<Word> getRemoteWords(int courseID) {

		List<Word> words = new ArrayList<Word>();

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Config.SERVER_BASE
				+ "getWord.php?cid=" + courseID);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String response = EntityUtils.toString(entity, "utf-8");
				
				Gson gson = new Gson();
				
				 
				words = gson.fromJson( response ,  new  TypeToken< List<Word>>(){}.getType()    )   ;
				return words;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("my", e.toString());
		} 
		 
		return null;
	}
   
	public void saveWordsInLocal(int courseID, List<Word> words) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	 	
		for (Word w : words) {
			
			try {
				db.execSQL(
						"insert into Courses_Words (w_id,c_id) values(?,?)",
						new String[] { w.getId()+"", courseID+"" });
				db.execSQL(
						"insert into Words (w_id, word, pronounce, translation, explain) values(?,?,?,?,?)",
						new String[] { w.getId() + "", w.getWord(), "", w.getTranslation(), w.getExplain() });

			} catch (Exception e) {
				Log.i("my", e.getMessage());
				Log.i("my", "词汇已存在本地");
			}
		}

	}
	
	//课程
	public List<Word> getLocalWord(SQLiteDatabase db ) {
		List<Word> words = new ArrayList<Word>();
		
		Cursor cursor1 = db.rawQuery("select * from Words" , null);
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

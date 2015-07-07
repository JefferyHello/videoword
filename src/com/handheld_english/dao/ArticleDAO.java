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

public class ArticleDAO {

	private MyDatabaseHelper dbHelper;

	public ArticleDAO(MyDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
 
     
	public List<Article> getRemoteArticles(List<String> words) throws IOException,
			ClientProtocolException {

		String url = "";
		if( words!=null && words.size()>0)
		{
				
			String wordparam = "";
			for (int i = 0; i < words.size(); ++i) {
	
				if (i > 0)
					wordparam += "%2C";
				wordparam += words.get(i);
			}

			url = Config.SERVER_BASE
					+ "getArticle.php?searchedWord=" + wordparam;
		}
		else {
			url = Config.SERVER_BASE+ "getArticle.php";
		}
		List<Article> articles = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "utf-8");
			
			Gson gson = new Gson();
			
			articles = gson.fromJson(response, 
					new  TypeToken< List<Article>>(){}.getType());			
		}
		return articles;
	}

	public void saveLocalArticles(List<Article> articleList) {
 
		try {
			 
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.delete("Article", null, null);

			
			for (int i = 0; i < articleList.size(); i++) {
				  Article a = articleList.get(i);
				  
				ContentValues values = new ContentValues();
				values.put("a_id", a.getId());
				values.put("a_title", a.getTitle());
				values.put("article", a.getContent());
				values.put("a_score", a.getScore());
				values.put("video", a.getVideo());				
				db.insert("Article", null, values);
				values.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}  
	}


	public List<Article> getLocalRecommentArticle() {
		List<Article> articleList =  new ArrayList<Article>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db
				.query("Article", null, null, null, null, null, null);
		if (cursor1.moveToFirst()) {
			do {
				int id = cursor1.getInt(cursor1.getColumnIndex("a_id"));
				String a_title1 = cursor1.getString(cursor1.getColumnIndex("a_title"));
				String a_content = cursor1.getString( cursor1.getColumnIndex("article") );
				String video = cursor1.getString(cursor1.getColumnIndex("video"));
				Article articleL_1 = new Article(id,a_title1, a_content, video);
				articleList.add(articleL_1);
			} while (cursor1.moveToNext());
		}
		cursor1.close();
		
		return articleList;  
	}
  
}

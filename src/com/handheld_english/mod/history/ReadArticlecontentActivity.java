package com.handheld_english.mod.history;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import com.handheld_english.AppSession;
import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.NotesDAO;
import com.handheld_english.dao.WordDAO;
import com.handheld_english.data.Article;
import com.handheld_english.data.Notes;
import com.handheld_english.data.User;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReadArticlecontentActivity extends Activity {
	private MyDatabaseHelper dbHelper;
	LinearLayout lay2;
	LinearLayout lay1;
	boolean flag = false;
	String a = "";
	String explain = null;
	String word = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articlecontent);
		dbHelper = MyDatabaseHelper.instance(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setSupportZoom(true);// 双击缩放功能
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setInitialScale(100);

		WebSettings settings = webView.getSettings(); // 自动换行
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.setHorizontalScrollBarEnabled(false);
		webView.setHorizontalScrollbarOverlay(false);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		final TextView textView = (TextView) findViewById(R.id.textView2);
		// textView.setVisibility(View.GONE);
		lay1 = (LinearLayout) findViewById(R.id.article1_layout);

		int a_id = bundle.getInt("a_id");
		final SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor = db.rawQuery(
				"select article,a_id from Article where a_id = ?",
				new String[] { Integer.toString(a_id) });
		if (cursor.moveToFirst()) {
			do {
				String article = cursor.getString(cursor
						.getColumnIndex("article"));
				int id = cursor.getInt(cursor.getColumnIndex("a_id"));
				
				Article article1 = (Article) AppSession.get(AppSession.ARTICLE);
				article1.setId(id);
				String s1 = linkWords(article);

				webView.setWebViewClient(new WebViewClient() {
					/*
					 * 此处能拦截超链接的url,即拦截href请求的内容.
					 */
					public boolean shouldOverrideUrlLoading(WebView view,
							String url) {
					
						// 从后台获取词义
						WordDAO dao1 = new WordDAO( dbHelper );
						dao1.select_translation(explain, url);
						
						// 将词义显示在界面上
						if (a.equals(explain)
								|| lay1.getVisibility() != View.GONE) {
							lay1.setVisibility(View.GONE);
							
						}

						else {
							lay1.setVisibility(View.VISIBLE);
							textView.setText(explain);
						}
						// textView.setVisibility(View.VISIBLE);
						// textView.setText(explain);
						a = explain;
						return true;
					}
				});

				try {

					webView.loadData(
							URLEncoder.encode(s1, "utf-8").replaceAll("\\+",
									" "), "text/html", "utf-8");

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} while (cursor.moveToNext());
		}
		cursor.close();

		// 返回
		Button button1 = (Button) findViewById(R.id.back1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ReadArticlecontentActivity.this,
						AlreadyreadActivity.class);
				startActivity(intent);
			}
		});
		// 笔记
		lay2 = (LinearLayout) findViewById(R.id.article2_layout);
		Button btn_note1 = (Button) findViewById(R.id.note1);
		btn_note1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == false) {
					lay2.setVisibility(View.VISIBLE);
					flag = true;
				} else {
					lay2.setVisibility(View.GONE);
					flag = false;
				}
				// text1.setVisibility(View.GONE);

			}
		});
		// 保存笔记
		Button btn_save_note = (Button) findViewById(R.id.save);
		btn_save_note.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == false) {
					lay2.setVisibility(View.VISIBLE);
					flag = true;

				} else {
					lay2.setVisibility(View.GONE);
					flag = false;
					String note = ((EditText) findViewById(R.id.txt_word2))
							.getText().toString();
					
					NotesDAO note_dao = new NotesDAO( dbHelper );
					note_dao.save_article_note(note);
					
				}
			}
		});

	}

	private String linkWords(String s0) {
		WordDAO word_dao = new WordDAO( dbHelper );
		
		if(s0.length()>100000)
			s0 = s0.substring(0, 100000);
		s0= s0.replace("?", " ? ");
		s0= s0.replace(".", " . ");
			
		String[] words = s0.split(" ");
		String s1 = "<html><body>";
		int count=0;
		for (String w : words) {
			if(count<10000)
				count++;
			else 
				break;
			 
			if (word_dao.hasWord(w ) ) {
				// w = "<a href='" + w + "'>" + w + "</a> ";
				w = "<a style='text-decoration: none;color:red' a href='"
						+ w + "'>" + w + "</a> ";
			}
			s1 += w + " ";
		}

		s1+="</body></html>";
		return s1;
	}

}

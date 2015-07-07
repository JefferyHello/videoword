package com.handheld_english.mod.history;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.handheld_english.AppSession;
import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.adapter.ReadArticleAdapter;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.data.SimpleReadarticle;
import com.handheld_english.data.User;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AlreadyreadActivity extends Activity {
	private MyDatabaseHelper dbHelper;
	private List<SimpleReadarticle> read_articleList = new ArrayList<SimpleReadarticle>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alreadyread);

		dbHelper = MyDatabaseHelper.instance(this);

		Button button1 = (Button) findViewById(R.id.back5);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AlreadyreadActivity.this,
						StudyActivity.class);
				startActivity(intent);
			}
		});

		User user = (User) AppSession.get(AppSession.USER);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor3 = db
				.rawQuery(
						"SELECT a.a_id, a_title, rda_time from Readarticle as r, Article as a where r.a_id=a.a_id and r.u_id = ?",
						new String[] { Integer.toString(user.getUId()) });
		if (cursor3.moveToFirst()) {
			do {
				int id = cursor3.getInt(cursor3.getColumnIndex("a_id"));
			
				String a_title1 = cursor3.getString(cursor3
						.getColumnIndex("a_title"));
				long m=cursor3.getLong(cursor3
						.getColumnIndex("rda_time"));
				Date date =  new Date(m);
				
					SimpleReadarticle read_article = new SimpleReadarticle(id, a_title1,null,date);
					read_articleList.add(read_article);
	
			} while (cursor3.moveToNext());
		}
		cursor3.close();

		ReadArticleAdapter adapter = new ReadArticleAdapter(AlreadyreadActivity.this,
				R.layout.article, read_articleList);
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SimpleReadarticle read_article = read_articleList.get(position);
				Intent intent = new Intent(AlreadyreadActivity.this,
						ReadArticlecontentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("a_id", read_article.getId());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

}
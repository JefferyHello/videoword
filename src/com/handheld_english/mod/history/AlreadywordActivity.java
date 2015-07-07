package com.handheld_english.mod.history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.handheld_english.AppSession;
import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.adapter.WordAdapter;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.data.User;
import com.handheld_english.data.WordTranslation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlreadywordActivity extends Activity {
	private MyDatabaseHelper dbHelper ;
	private List<WordTranslation> wordList=new ArrayList<WordTranslation>();
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alreadyword);
		dbHelper = MyDatabaseHelper.instance(this);
		Button button1 = (Button) findViewById(R.id.back2);
		button1.setOnClickListener(new OnClickListener() {
	    	@Override

			public void onClick(View v) {
				
						Intent intent = new Intent(AlreadywordActivity.this,
								StudyActivity.class);
						startActivity(intent);
				}
			});
		
		
		
		User user = (User) AppSession.get(AppSession.USER);
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		
//		 Cursor cursor=db.rawQuery("SELECT word,translation from Querywords as q, Words as w, where q.w_id=w.w_id and r.u_id = ?",
//	    		 new String[] { Integer.toString(user.getUId())});
		 Cursor cursor=db.rawQuery("SELECT word,translation from Querywords as q, Words as w where q.w_id=w.w_id ",
	    		 null);    
	    if(cursor.moveToFirst()){
	     do {
	    	 String word = cursor.getString(cursor
	    			 .getColumnIndex("word"));
	    
	    	 String translation = cursor.getString(cursor
			 .getColumnIndex("translation"));
			
			 WordTranslation word1 = new WordTranslation(word, translation);
			 
			 wordList.add(word1);
			 } while (cursor.moveToNext());
			 }
			 cursor.close(); 

        WordAdapter adapter=new WordAdapter(AlreadywordActivity.this,R.layout.word,wordList);
        ListView listview=(ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new OnItemClickListener()
//        {
//        	@Override
//        	public void onItemClick(AdapterView<?> parent,View view,int position,long id)
//        	{
//        		Article article=articalList.get(position);
//        		Intent intent = new Intent(AlreadywordActivity.this,
//        				ArticalcontentActivity.class);
//				startActivity(intent);
//      		
//
//        	}
//        });

			
	}

}
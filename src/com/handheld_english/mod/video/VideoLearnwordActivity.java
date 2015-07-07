package com.handheld_english.mod.video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.handheld_english.AppSession;
import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.adapter.WordAdapter;
import com.handheld_english.dao.LearnWordsDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.data.Learnword;
import com.handheld_english.data.User;
import com.handheld_english.data.WordTranslation;
import com.handheld_english.mod.video.adapter.LearnwordAdapter;

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

public class VideoLearnwordActivity extends Activity {
	private MyDatabaseHelper dbHelper ;
	
	private List<Learnword> list=new ArrayList<Learnword>();
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learnword);
		dbHelper = MyDatabaseHelper.instance(this);
		Button button1 = (Button) findViewById(R.id.back2);
		button1.setOnClickListener(new OnClickListener() {
	    	@Override

			public void onClick(View v) {
				
						Intent intent = new Intent(VideoLearnwordActivity.this,
								VideoListActivity.class);
						startActivity(intent);
				}
			});
		
		
		
		User user = (User) AppSession.get(AppSession.USER);
		
		LearnWordsDAO dao = new LearnWordsDAO(dbHelper);
		
		list = dao.getLearnwords(user.getUId(),0,3);
		 
		LearnwordAdapter adapter=new LearnwordAdapter(VideoLearnwordActivity.this,R.layout.word,list);
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
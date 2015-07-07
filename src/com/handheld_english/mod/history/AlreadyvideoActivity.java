package com.handheld_english.mod.history;

import java.util.ArrayList;
import java.util.List;

import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.adapter.WordAdapter;
import com.handheld_english.data.WordTranslation;

import android.app.Activity;
import android.content.Intent;
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

public class AlreadyvideoActivity extends Activity {
	/** Called when the activity is first created. */
	private List<WordTranslation> wordList=new ArrayList<WordTranslation>();
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alreadyvideo);
		Button button1 = (Button) findViewById(R.id.back2);
		button1.setOnClickListener(new OnClickListener() {
	    	@Override

			public void onClick(View v) {
				
						Intent intent = new Intent(AlreadyvideoActivity.this,
								StudyActivity.class);
						startActivity(intent);
				}
			});
		initWord();
        WordAdapter adapter=new WordAdapter(AlreadyvideoActivity.this,R.layout.word,wordList);
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
	private void initWord() {
		// TODO Auto-generated method stub
		WordTranslation word_1=new WordTranslation("apple","Æ»¹û");
		wordList.add(word_1);
		WordTranslation word_2=new WordTranslation("banana","Ïã½¶");
		wordList.add(word_2);
		WordTranslation word_3=new WordTranslation("orange","éÙ×Ó");
		wordList.add(word_3);
		WordTranslation word_4=new WordTranslation("pear","Àæ×Ó");
		wordList.add(word_4);
	}
}
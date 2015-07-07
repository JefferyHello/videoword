package com.handheld_english;

import com.handheld_english.mod.article.ArticallistActivity;
import com.handheld_english.mod.history.AlreadyreadActivity;
import com.handheld_english.mod.history.AlreadyvideoActivity;
import com.handheld_english.mod.history.AlreadywordActivity;
import com.handheld_english.mod.my.MyNoteActivity;
import com.handheld_english.mod.my.ProgressActivity;
import com.handheld_english.mod.my.SetUpActivity;
import com.handheld_english.mod.video.VideoLearnwordActivity;
import com.handheld_english.mod.video.VideoListActivity;
import com.handheld_english.mod.word.WordrememberActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudyActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study);
		
		Button buttona = (Button) findViewById(R.id.read);
		buttona.setOnClickListener(new OnClickListener() {
    	@Override
		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							ArticallistActivity.class);
					startActivity(intent);
			}
		});
		
		Button buttonb = (Button) findViewById(R.id.watch);
		buttonb.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							VideoListActivity.class);
					startActivity(intent);
			}
		});
		
		Button buttonc = (Button) findViewById(R.id.memorize);
		buttonc.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							WordrememberActivity.class);
					startActivity(intent);
			}
		});
		
		Button mynote = (Button) findViewById(R.id.mynote);
		mynote.setOnClickListener(new OnClickListener() {
   	@Override

		public void onClick(View v) {
			//得到已读文章ID
   		
					Intent intent = new Intent(StudyActivity.this,
							MyNoteActivity.class);
					startActivity(intent);
			}
		});
		
		Button buttond = (Button) findViewById(R.id.mian_page);
		buttond.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							MainActivity.class);
					startActivity(intent);
			}
		});
		
		/*设置按钮*/
		Button buttone = (Button) findViewById(R.id.setup_page);
		buttone.setOnClickListener(new OnClickListener() {
    	@Override
		public void onClick(View v) {
					Intent intent = new Intent(StudyActivity.this,
							SetUpActivity.class);
					startActivity(intent);
			}
		});
		
		Button button1 = (Button) findViewById(R.id.progress);
		button1.setOnClickListener(new OnClickListener() {
    	@Override
		public void onClick(View v) {
					Intent intent = new Intent(StudyActivity.this,
							VideoLearnwordActivity.class);
					startActivity(intent);
			}
		});
		Button button2 = (Button) findViewById(R.id.btn_leaningpace);
		button2.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							AlreadyreadActivity.class);
					startActivity(intent);
			}
		});
		Button button3 = (Button) findViewById(R.id.wordform);
		button3.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							AlreadywordActivity.class);
					startActivity(intent);
			}
		});
		Button button4 = (Button) findViewById(R.id.alreadyvideo);
		button4.setOnClickListener(new OnClickListener() {
    	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(StudyActivity.this,
							AlreadyvideoActivity.class);
					startActivity(intent);
			}
		});
		
		
    }

}
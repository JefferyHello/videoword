package com.handheld_english.mod.my;

import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.dao.CourseDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.SelectCourseDAO;
import com.handheld_english.dao.WordDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProgressActivity extends Activity {
	/** Called when the activity is first created. */
	private MyDatabaseHelper dbHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		
		TextView textview1 = (TextView) findViewById(R.id.textView3);
		TextView textview2 = (TextView) findViewById(R.id.textView5);
		WordDAO dao = new WordDAO(dbHelper);
		dao.progress(textview1,textview2);
		
    	Button button1 = (Button) findViewById(R.id.back1);
		button1.setOnClickListener(new OnClickListener() {
	    	@Override

			public void onClick(View v) {
				
						Intent intent = new Intent(ProgressActivity.this,
								StudyActivity.class);
						startActivity(intent);
				}
			});
    }

}
package com.handheld_english.mod.word;


import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class InReadScore_RulesActivity extends Activity {
	/** Called when the activity is first created. */
	
	@Override 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_rules);
		Button button = (Button) findViewById(R.id.back1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(InReadScore_RulesActivity.this,
						MywordInReadActivity.class);
				startActivity(intent);
			}
		});
		 
	}
}
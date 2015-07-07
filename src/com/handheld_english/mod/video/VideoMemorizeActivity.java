package com.handheld_english.mod.video;

import android.content.Intent;

import com.handheld_english.mod.article.ArticallistActivity;
import com.handheld_english.mod.word.MywordInReadActivity;

public class VideoMemorizeActivity extends MywordInReadActivity  { 

	@Override
	public void done()
	{

		Intent intent = new Intent(
				VideoMemorizeActivity.this,
				VideoLearnwordActivity.class);
		startActivity(intent); 
	}
	
}
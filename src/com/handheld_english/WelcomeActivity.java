package com.handheld_english;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	/** Called when the activity is first created. */
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hallow);
		final Intent it = new Intent(this, UserLoginActivity.class); //��Ҫת���Activity   
		Timer timer = new Timer(); 
		TimerTask task = new TimerTask() {  
		    @Override  
		    public void run() {   
		    startActivity(it); //ִ��  
		     } 
		 };
		timer.schedule(task, 1000 * 2); //10���
	}
}
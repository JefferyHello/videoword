package com.handheld_english;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.handheld_english.dao.CourseDAO;
import com.handheld_english.dao.CourseWordDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.SelectCourseDAO;
import com.handheld_english.dao.WordDAO;
import com.handheld_english.data.Course;
import com.handheld_english.data.User;
import com.handheld_english.data.Word;
import com.handheld_english.mod.my.SetUpActivity;
import com.handheld_english.mod.word.MywordInReadActivity;
import com.handheld_english.mod.word.snsConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public  class AboutActivity extends Activity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Button button = (Button) findViewById(R.id.back1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AboutActivity.this,
						SetUpActivity.class);
				startActivity(intent);
			}
		});
	}
}
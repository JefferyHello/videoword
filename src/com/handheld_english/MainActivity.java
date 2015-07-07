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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener,
OnGestureListener {
	boolean flag = false;
	GestureDetector mGestureDetector;
	private MyDatabaseHelper dbHelper;
	private SharedPreferences sp;
	HashMap<String,Course> courses;
	public static final int MSG_DOWNLOADED = 1;
	public static final int MSG_DOWNLOADING = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mGestureDetector = new GestureDetector((OnGestureListener) this);
		FrameLayout main_Layout = (FrameLayout) findViewById(R.id.main_Layout);
		main_Layout.setOnTouchListener(this);
		main_Layout.setLongClickable(true);
		dbHelper = MyDatabaseHelper.instance(this);
		ImageView myImage = (ImageView) findViewById(R.id.imageback);
		Intent intent=getIntent();
		//显示用户名
		final User user = (User) AppSession.get(AppSession.USER);
		TextView textview1 = (TextView) findViewById(R.id.user_name);
		textview1.setText(user.getUName());
		
		if(intent.getStringExtra("myph")==null)
		 {}
		else{
			 try {Bitmap bitmap=null;
				    byte[]bitmapArray;
				    bitmapArray=Base64.decode(intent.getStringExtra("myph"), Base64.DEFAULT);
				bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
				 Drawable drawable = new BitmapDrawable(bitmap);      
					myImage.setImageDrawable(drawable);
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
		

		sp = this.getSharedPreferences("ck", Context.MODE_WORLD_READABLE);

		CourseDAO courseDao = new CourseDAO(dbHelper);
		courses =  courseDao.getAllCourses();
		
		Button button2 = (Button) findViewById(R.id.study_page);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						StudyActivity.class);
				startActivity(intent);
			}
		});
		
		/*设置按钮*/
		Button button3 = (Button) findViewById(R.id.setup_page);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SetUpActivity.class);
				startActivity(intent);
			}
		});

		/*词库动画效果*/
		RelativeLayout lay1 = (RelativeLayout) findViewById(R.id.Layout_choice);
		final LinearLayout lay2 = (LinearLayout) findViewById(R.id.lay_choice);
		lay1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flag == false) {
					lay2.setVisibility(View.VISIBLE);
					flag = true;
				} else {
					lay2.setVisibility(View.GONE);
					flag = false;
				}
			}
		});
		
		// 选择词库
		Button buttona = (Button) findViewById(R.id.ck1);
		Button buttonb = (Button) findViewById(R.id.ck2);
		Button buttonc = (Button) findViewById(R.id.ck3);
		Button buttond = (Button) findViewById(R.id.ck4);
		Button buttone = (Button) findViewById(R.id.ck5);
		Button buttonf = (Button) findViewById(R.id.ck6);
		final TextView textview = (TextView) findViewById(R.id.myword);
		final Editor editor = sp.edit();
		User user1 = (User) AppSession.get(AppSession.USER);
		Course course1 = (Course) AppSession.get(AppSession.COURSE);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db.rawQuery(
						"select u_id,c_id from Selectcourses where u_id=?",
						new String[] { String.valueOf(user1.getUId()) });
		if (cursor1.moveToFirst()) {
			do {

				
				textview.setText(course1.getName());
			} while (cursor1.moveToNext());
		}
		else
		   {
			sp.edit().putBoolean("Choice", false).commit();
		   }
		cursor1.close();
		
		
			
		
		buttona.setOnClickListener( onclick );
		buttonb.setOnClickListener( onclick );
		buttonc.setOnClickListener( onclick );
		buttond.setOnClickListener( onclick );
		buttone.setOnClickListener( onclick );
		buttonf.setOnClickListener( onclick );
		

	}

	OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			//1. 获得课程名称

			final Button button = (Button) v;

			String courseName = (String)  button.getText();
			
			//2. 根据课程名称，获得课程ID

			Course course = courses.get(courseName);
			
			//3. 记录用户选择课程

			User user = (User) AppSession.get(AppSession.USER);

			course.setId(course.getId());
			
			SelectCourseDAO dao = new SelectCourseDAO( dbHelper );
			
			dao.save( user.getUId(), course.getId()  );
			
			//4. 如果app还没有该课程词汇，则从服务器下载该课程词汇

			// if(hasDownloadWords( course.getId() ) )
			
				downloadWords(  course.getId());
//			db.execSQL(
//					"insert into Words (w_id, word,pronounce,translation,explain) values(?,?,?,?,?)",
//					new String[] { "1", "Throughout"," ","prep.自始至终; ","Throughout this long, everyone has focused on the presidential candidates ."  });
//			db.execSQL(
//					"insert into Courses_Words (w_id,c_id) values(?,?)",
//					new String[] {"1", course.getId() +"" });
//			db.execSQL(
//					"insert into Words (w_id, word,pronounce,translation,explain) values(?,?,?,?,?)",
//					new String[] { "2", "fascinated"," ","adj.着迷的; ","I'm more fascinated by Michelle Obama and what she might be able to do."  });
//			db.execSQL(
//					"insert into Courses_Words (w_id,c_id) values(?,?)",
//					new String[] {"2", course.getId() +"" });
//			db.execSQL(
//					"insert into Words (w_id, word,pronounce,translation,explain) values(?,?,?,?,?)",
//					new String[] { "3", "potential"," ","adj.潜在的，有可能的; ","As the potential First Lady, she would have the world's attention."  });
//			db.execSQL(
//					"insert into Courses_Words (w_id,c_id) values(?,?)",
//					new String[] {"3", course.getId() +"" });

			//5. 跳转到学习模块
			
		}

		private boolean hasDownloadWords(int id) {
			//根据 词汇-课程 的联系表
			//Courses_Words
			
//			SQLiteDatabase db = dbHelper.getWritableDatabase();
//			Cursor cursor1 = db
//					.rawQuery(
//							"select * from Courses_Words where c_id=?",
//							new String[] { String.valueOf(id) });
//			if (cursor1.moveToFirst()) {
//				do {
//					return true;
//				} while (cursor1.moveToNext());
//			}
//			else
			return false;
//			
//			cursor1.close();
			
		}

		//根据课程id，下载课程词汇
		private void downloadWords( final int courseID) {

			//1. 访问服务器的词汇下载词汇
			final WordDAO dao = new WordDAO(dbHelper);
			
			
			new Thread(new Runnable() {


				@Override
				public void run() {
					
					Message msg = new Message();
					 msg.what = MSG_DOWNLOADING;					  
					 handler.sendMessage(msg);
					 
					 List<Word> words = dao.getRemoteWords(courseID);
					
					 dao.saveWordsInLocal(courseID, words);
					 
					 Message msg2 = new Message();
					 msg2.what = MSG_DOWNLOADED;
					  
					 handler.sendMessage(msg2);
				}
				
				
			}).start();
			
			 
			//2. 存放到本地数据库中

			
		}	
	};
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case MSG_DOWNLOADED: 
       			Toast.makeText(MainActivity.this, "词库下载成功",
						Toast.LENGTH_SHORT).show();

    			Intent intent = new Intent(MainActivity.this, StudyActivity.class);
    			startActivity(intent);
    			
				break;
			case MSG_DOWNLOADING: 
       			Toast.makeText(MainActivity.this, "正在下载词库....",
						Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
	};
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		if (e1.getX() - e2.getX() > snsConstant.getFlingMinDistance()
				&& Math.abs(velocityX) > snsConstant.getFlingMinVelocity()) {
			Log.d("my", "1-2");
			// 切换Activity
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			startActivity(intent);
			Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
		} else if (e2.getX() - e1.getX() > snsConstant.getFlingMinDistance()
				&& Math.abs(velocityX) > snsConstant.getFlingMinVelocity()) {

			// 切换Activity
			Intent intent = new Intent(MainActivity.this, StudyActivity.class);
			startActivity(intent);
			// overridePendingTransition(android.R.anim.slide_in_left,
			// android.R.anim.slide_out_right);
			Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
		}

		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
package com.handheld_english;

import com.handheld_english.dao.CourseDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.SelectCourseDAO;
import com.handheld_english.dao.UserDAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private MyDatabaseHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		dbHelper = MyDatabaseHelper.instance(this);
		Button button = (Button) findViewById(R.id.register);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = ((EditText) findViewById(R.id.name)).getText()
						.toString();
				String pwd = ((EditText) findViewById(R.id.pwd)).getText()
						.toString();
				String repwd = ((EditText) findViewById(R.id.repwd)).getText()
						.toString();

				if (!"".equals(name) && !"".equals(pwd)) {
					if (!pwd.equals(repwd)) {
						Toast.makeText(RegisterActivity.this,
								"两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
						((EditText) findViewById(R.id.name)).requestFocus();
						((EditText) findViewById(R.id.repwd)).setText("");
						((EditText) findViewById(R.id.pwd)).setText("");
					} else {
						//新注册的用户名保存到数据库中
						UserDAO user_dao = new UserDAO( dbHelper );
						user_dao.save_user( name,pwd );
				
						//插入6个词库到词库表中
						CourseDAO course_dao = new CourseDAO( dbHelper );
						course_dao.save_course( );
						
						
						Intent intent = new Intent(RegisterActivity.this,
								UserLoginActivity.class);
						startActivity(intent);
					}
				}
			}

		});
	}
}
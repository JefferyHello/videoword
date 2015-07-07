package com.handheld_english;

import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.SelectCourseDAO;
import com.handheld_english.dao.UserDAO;
import com.handheld_english.data.Course;
import com.handheld_english.data.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

	public class UserLoginActivity extends Activity {
	private MyDatabaseHelper dbHelper;
	private SharedPreferences sp; 
	// 是否保存密码
	boolean flag = true;
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlogin);
		
		dbHelper = MyDatabaseHelper.instance(this);
		final CheckBox rem_pw = (CheckBox) findViewById(R.id.login_cb);
		final CheckBox auto_pw = (CheckBox) findViewById(R.id.login_auto);
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		 
		Button button = (Button) findViewById(R.id.login);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = ((EditText) findViewById(R.id.name_edt))
						.getText().toString();
				String pwd = ((EditText) findViewById(R.id.pwd_edt))
						.getText().toString();
				
				/* 动态更改IP地址*/
				String ip = ((EditText) findViewById(R.id.ip_edt))
						.getText().toString();
				Config.SERVER_BASE = "http://" + ip +"/vw/";
				
//				boolean flag = false;
//               SQLiteDatabase db = dbHelper.getWritableDatabase();
               //把用户名作为全局变量记录下来：

				UserDAO u_dao = new UserDAO( dbHelper );               
	       		User user = u_dao.getUserByname( name);
	       		
	       		if(user!=null)
	       		{
	       			if (rem_pw.isChecked()) {
						// 记住用户名、密码、
						Editor editor = sp.edit();
						editor.putString("USER_NAME", name);
						editor.putString("PASSWORD", pwd);
						editor.commit();
					}
	       			
					//查询用户是否选择过课程，如果选择过把用户选择的课程作为全局变量存下来
	       			AppSession.put(AppSession.USER, user);
	       			SelectCourseDAO dao = new SelectCourseDAO( dbHelper );
					Course course = dao.getSelectedCourse(name);
					AppSession.put(AppSession.COURSE, course);
				
					/*  如果已选择过词库，则直接跳转到学习模块*/	
					Intent intent = new Intent(UserLoginActivity.this,
								MainActivity.class);
					startActivity(intent);
	       		}
	       		else {
	       			Toast.makeText(UserLoginActivity.this, "您输入的账号或密码有误",
							Toast.LENGTH_SHORT).show();
	       		}
			}
		});
		 
		 //监听记住密码多选框按钮事件  
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (rem_pw.isChecked()) {  
                      
                    System.out.println("记住密码已选中");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("记住密码没有选中");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
        //监听自动登录多选框事件  
        auto_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (auto_pw.isChecked()) {  
                    System.out.println("自动登录已选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
  
                } else {  
                    System.out.println("自动登录没有选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                }  
            }  
        });  
		Button button1 = (Button) findViewById(R.id.bn_register);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserLoginActivity.this,
					RegisterActivity.class);
				startActivity(intent);
			}
		});
		Button button2 = (Button) findViewById(R.id.cancel);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((EditText) findViewById(R.id.name_edt)).setText(null);
				((EditText) findViewById(R.id.pwd_edt)).setText(null);
			}
		});
		
		if(sp.getBoolean("ISCHECK", false))  
        {  
          //设置默认是记录密码状态  
          rem_pw.setChecked(true);  
          ((EditText) findViewById(R.id.name_edt)).setText(sp.getString("USER_NAME", ""));  
          ((EditText) findViewById(R.id.pwd_edt)).setText(sp.getString("PASSWORD", ""));  
          //判断自动登陆多选框状态  
          if(sp.getBoolean("AUTO_ISCHECK", false))  
          {  
                 //设置默认是自动登录状态  
        	  auto_pw.setChecked(true);  
                //跳转界面  
        	  
                Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);  
                UserLoginActivity.this.startActivity(intent); 
                
                  
          }  
        }  
	}
}
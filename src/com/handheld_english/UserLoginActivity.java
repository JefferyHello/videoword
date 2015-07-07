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
	// �Ƿ񱣴�����
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
				
				/* ��̬����IP��ַ*/
				String ip = ((EditText) findViewById(R.id.ip_edt))
						.getText().toString();
				Config.SERVER_BASE = "http://" + ip +"/vw/";
				
//				boolean flag = false;
//               SQLiteDatabase db = dbHelper.getWritableDatabase();
               //���û�����Ϊȫ�ֱ�����¼������

				UserDAO u_dao = new UserDAO( dbHelper );               
	       		User user = u_dao.getUserByname( name);
	       		
	       		if(user!=null)
	       		{
	       			if (rem_pw.isChecked()) {
						// ��ס�û��������롢
						Editor editor = sp.edit();
						editor.putString("USER_NAME", name);
						editor.putString("PASSWORD", pwd);
						editor.commit();
					}
	       			
					//��ѯ�û��Ƿ�ѡ����γ̣����ѡ������û�ѡ��Ŀγ���Ϊȫ�ֱ���������
	       			AppSession.put(AppSession.USER, user);
	       			SelectCourseDAO dao = new SelectCourseDAO( dbHelper );
					Course course = dao.getSelectedCourse(name);
					AppSession.put(AppSession.COURSE, course);
				
					/*  �����ѡ����ʿ⣬��ֱ����ת��ѧϰģ��*/	
					Intent intent = new Intent(UserLoginActivity.this,
								MainActivity.class);
					startActivity(intent);
	       		}
	       		else {
	       			Toast.makeText(UserLoginActivity.this, "��������˺Ż���������",
							Toast.LENGTH_SHORT).show();
	       		}
			}
		});
		 
		 //������ס�����ѡ��ť�¼�  
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (rem_pw.isChecked()) {  
                      
                    System.out.println("��ס������ѡ��");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("��ס����û��ѡ��");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
        //�����Զ���¼��ѡ���¼�  
        auto_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (auto_pw.isChecked()) {  
                    System.out.println("�Զ���¼��ѡ��");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
  
                } else {  
                    System.out.println("�Զ���¼û��ѡ��");  
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
          //����Ĭ���Ǽ�¼����״̬  
          rem_pw.setChecked(true);  
          ((EditText) findViewById(R.id.name_edt)).setText(sp.getString("USER_NAME", ""));  
          ((EditText) findViewById(R.id.pwd_edt)).setText(sp.getString("PASSWORD", ""));  
          //�ж��Զ���½��ѡ��״̬  
          if(sp.getBoolean("AUTO_ISCHECK", false))  
          {  
                 //����Ĭ�����Զ���¼״̬  
        	  auto_pw.setChecked(true);  
                //��ת����  
        	  
                Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);  
                UserLoginActivity.this.startActivity(intent); 
                
                  
          }  
        }  
	}
}
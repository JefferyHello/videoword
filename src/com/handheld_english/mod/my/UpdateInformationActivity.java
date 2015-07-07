package com.handheld_english.mod.my;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.handheld_english.AboutActivity;
import com.handheld_english.AppSession;
import com.handheld_english.MainActivity;
import com.handheld_english.R;
import com.handheld_english.RegisterActivity;
 
import com.handheld_english.StudyActivity;
import com.handheld_english.UserLoginActivity;
import com.handheld_english.R.drawable;

import com.handheld_english.dao.CourseDAO;
import com.handheld_english.dao.UserDAO;
import com.handheld_english.data.User;
import com.handheld_english.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateInformationActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateinformation);
		Button back1 = (Button) findViewById(R.id.back1);
		Button okupdate = (Button) findViewById(R.id.okupdate);
		Button noupdate = (Button) findViewById(R.id.noupdate);
		final TextView name=(EditText) findViewById(R.id.name);
		final TextView pwd=(EditText) findViewById(R.id.pwd);
		final TextView repwd=(EditText) findViewById(R.id.repwd);
		back1.setOnClickListener(new OnClickListener() {
   	@Override

		public void onClick(View v) {
					Intent intent = new Intent(UpdateInformationActivity.this,
							SetUpActivity.class);
					startActivity(intent);
			}
		});
		okupdate.setOnClickListener(new OnClickListener() {
		   	@Override

				public void onClick(View v) {
		   		if (!"".equals(name) && !"".equals(pwd)) {
					if (!pwd.equals(repwd)) {
						Toast.makeText(UpdateInformationActivity.this,
								"两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
						((EditText) findViewById(R.id.name)).requestFocus();
						((EditText) findViewById(R.id.repwd)).setText("");
						((EditText) findViewById(R.id.pwd)).setText("");
					} else {
						//将修改后的信息更新到数据库
					}
				}
					}
				});
		noupdate.setOnClickListener(new OnClickListener() {
		   	@Override

				public void onClick(View v) {
		   		name.setText(null);
		   		pwd.setText(null);
		   		repwd.setText(null);
					}
				});
	}

}
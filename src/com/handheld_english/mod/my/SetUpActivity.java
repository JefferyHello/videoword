package com.handheld_english.mod.my;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.handheld_english.AboutActivity;
import com.handheld_english.AppSession;
import com.handheld_english.MainActivity;
import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.UserLoginActivity;
import com.handheld_english.R.drawable;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetUpActivity extends Activity {
	/** Called when the activity is first created. */
	private String[] items = new String[] { "选择本地图片", "拍照" };
    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    private ImageView  faceImage;
    private SharedPreferences sp;
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		
		
		User user = (User) AppSession.get(AppSession.USER);
		TextView textview1 = (TextView) findViewById(R.id.text_name);
	    textview1.setText(user.getUName());
		final ImageView person=(ImageView) findViewById(R.id.person);
		
	    
	    /*跳转词库界面*/
		Button buttona = (Button) findViewById(R.id.main_page);
		buttona.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
					Intent intent = new Intent(SetUpActivity.this,
							MainActivity.class);
					if(sp.getString("myphoto", "")==null){
						
					}
					 else{						 
							intent.putExtra("myph",sp.getString("myphoto", ""));
					 }
					intent.putExtra("myimage", faceImage.getDrawingCache());
					startActivity(intent);
				}
		});
		
		/*跳转学习界面*/
		Button buttonb = (Button) findViewById(R.id.study_page);
		buttonb.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
					Intent intent = new Intent(SetUpActivity.this,
							StudyActivity.class);
					startActivity(intent);
			}
		});
		
		Button button1 = (Button) findViewById(R.id.information);
	button1.setOnClickListener(new OnClickListener() {
   	@Override
		public void onClick(View v) {
			
					Intent intent = new Intent(SetUpActivity.this,
							UpdateInformationActivity.class);
					startActivity(intent);
			}
		});
		
		Button button2 = (Button) findViewById(R.id.about);
		button2.setOnClickListener(new OnClickListener() {
  		public void onClick(View v) {			//得到已读文章ID
 		
					Intent intent = new Intent(SetUpActivity.this,
							AboutActivity.class);
					startActivity(intent);
			}
		});
		
	    faceImage = (ImageView) findViewById(R.id.person);
		Button button3 = (Button) findViewById(R.id.mypicture);
		button3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
   		 		showDialog();
			}
		});
		
		Button button4 = (Button) findViewById(R.id.exit);
		button4.setOnClickListener(new OnClickListener() {
   	@Override

		public void onClick(View v) {
			
					Intent intent = new Intent(SetUpActivity.this,
							UserLoginActivity.class);
					startActivity(intent);
			}
		});

		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		 if(sp.getString("myphoto", "")==null)
		 {faceImage.setImageResource(R.drawable.person);}else{
			 try {Bitmap bitmap=null;
				    byte[]bitmapArray;
				    bitmapArray=Base64.decode(sp.getString("myphoto", ""), Base64.DEFAULT);
				bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
				 Drawable drawable = new BitmapDrawable(bitmap);      
					faceImage.setImageDrawable(drawable);
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
	
   }
 private void showDialog() {
         
         new AlertDialog.Builder(this)
                         .setTitle("设置头像")
                         .setItems(items, new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                         switch (which) {
                                         case 0:
                                                 Intent intentFromGallery = new Intent();
                                                 intentFromGallery.setType("image/*"); // 设置文件类型
                                                 intentFromGallery
                                                                 .setAction(Intent.ACTION_GET_CONTENT);
                                                 startActivityForResult(intentFromGallery,
                                                                 IMAGE_REQUEST_CODE);
                                                 break;
                                         case 1:

                                                 Intent intentFromCapture = new Intent(
                                                                 MediaStore.ACTION_IMAGE_CAPTURE);
                                                 // 判断存储卡是否可以用，可用进行存储
                                                 if (Tools.hasSdcard()) {

                                                         intentFromCapture.putExtra(
                                                                         MediaStore.EXTRA_OUTPUT,
                                                                         Uri.fromFile(new File(Environment
                                                                                         .getExternalStorageDirectory(),
                                                                                         IMAGE_FILE_NAME)));
                                                 }

                                                 startActivityForResult(intentFromCapture,
                                                                 CAMERA_REQUEST_CODE);
                                                 break;
                                         }
                                 }
                         })
                         .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                 }
                         }).show();

 }
	 @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode != RESULT_CANCELED) {
             switch (requestCode) {
             case IMAGE_REQUEST_CODE:
                     startPhotoZoom(data.getData());
                     break;
             case CAMERA_REQUEST_CODE:
                     if (Tools.hasSdcard()) {
                             File tempFile = new File(
                                             Environment.getExternalStorageDirectory()
                                                            +  "/" + IMAGE_FILE_NAME);
                             startPhotoZoom(Uri.fromFile(tempFile));
                     } else {
                             Toast.makeText(SetUpActivity.this, "未找到存储卡，无法存储照片！",
                                             Toast.LENGTH_LONG).show();
                     }

                     break;
             case RESULT_REQUEST_CODE:
                     if (data != null) {
                             getImageToView(data);
                     }
                     break;
             }
             super.onActivityResult(requestCode, resultCode, data);
     }}

     /**
      * 裁剪图片方法实现
      * 
      * @param uri
      */
     public void startPhotoZoom(Uri uri) {

             Intent intent = new Intent("com.android.camera.action.CROP");
             intent.setDataAndType(uri, "image/*");
             // 设置裁剪
             intent.putExtra("crop", "true");
             // aspectX aspectY 是宽高的比例
             intent.putExtra("aspectX", 1);
             intent.putExtra("aspectY", 1);
             // outputX outputY 是裁剪图片宽高
             intent.putExtra("outputX", 320);
             intent.putExtra("outputY", 320);
             intent.putExtra("return-data", true);
             startActivityForResult(intent, 2);
     }

     /**
      * 保存裁剪之后的图片数据
      * 
      * @param picdata
      */
     private void getImageToView(Intent data) {
             Bundle extras = data.getExtras();
             if (extras != null) {
                     Bitmap photo = extras.getParcelable("data");
                     Drawable drawable = new BitmapDrawable(photo);      
					faceImage.setImageDrawable(drawable);
					
					String string=null;
				    ByteArrayOutputStream bStream=new ByteArrayOutputStream();
				    photo.compress(CompressFormat.PNG,100,bStream);
				    byte[]bytes=bStream.toByteArray();
				    string=Base64.encodeToString(bytes,Base64.DEFAULT);
					  Editor editor = sp.edit();
					  editor.putString("myphoto",string); 
                      editor.commit(); 
             }
     }

}